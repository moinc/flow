package nl.agiletech.flow.bot;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.http.HttpException;

import nl.agiletech.flow.bot.exec.TaskRunner;
import nl.agiletech.flow.bot.requests.UpdateRequest;
import nl.agiletech.flow.common.cli.AppState;
import nl.agiletech.flow.common.cli.CliException;
import nl.agiletech.flow.common.cli.Command;
import nl.agiletech.flow.common.cli.CommandExecutor;
import nl.agiletech.flow.common.cli.CommandInfo;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.Catalog;
import nl.agiletech.flow.project.types.CatalogStatus;
import nl.agiletech.flow.project.types.ConfigurationException;
import nl.agiletech.flow.project.types.ConfigurationSettings;

public class UpdateCommand implements CommandInfo, CommandExecutor {
	private static final Logger LOG = Logger.getLogger(UpdateCommand.class.getName());

	@Override
	public String getName() {
		return CommandName.update.name();
	}

	@Override
	public String getDescription() {
		return "Updates the local system";
	}

	@Override
	public CommandExecutor getCommandExecutor() {
		return this;
	}

	@Override
	public CommandInfo getCommandInfo() {
		return this;
	}

	@Override
	public void execute(Command command, CommandLine commandLine, AppState appState) throws CliException {
		// the update process goes through several steps:
		//
		// 1. Collect basic information from this node.
		//
		// 2. Send an inspect request to the server.
		//
		// 3. Receive the response and follow its instructions (i.e. A catalog.
		// Executing a catalog yields information).
		//
		// 5. Depending on the instructions from the server, send a new request
		// to the server and include the information collected. Go to step 3
		// until the response explicitly tells the node that the update is
		// complete.

		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			// process arguments

			File flowConfigFile = null;
			if (commandLine.hasOption("c")) {
				String flowConfigPath = commandLine.getOptionValue("c");
				flowConfigFile = new File(flowConfigPath);
			}
			Assertions.exists(flowConfigFile, "flowConfigFile");

			String projectName = commandLine.getOptionValue("p");
			Assertions.notEmpty(projectName, "projectName");

			String overrideServiceURIString = commandLine.getOptionValue("u");
			URI overrideServiceURI = null;
			if (overrideServiceURIString != null && !overrideServiceURIString.isEmpty()) {
				overrideServiceURI = URI.create(overrideServiceURIString);
			}

			// load files

			ConfigurationSettings configurationSettings = null;
			try {
				configurationSettings = ConfigurationSettings.loadFrom(flowConfigFile);
			} catch (IOException e) {
				throw new CliException("failed to read flow configuration file", e);
			}

			try {
				log.faint().append("--- update ---").print();

				URI serviceURI = overrideServiceURI == null ? configurationSettings.getServiceURI()
						: overrideServiceURI;

				log.normal().append("service uri: " + serviceURI).print();

				Catalog catalog = Catalog.EMPTY;
				boolean done = false;
				int count = 0;
				int maxCatalogs = configurationSettings.getMaxCatalogs();
				while (!done && count < maxCatalogs) {
					DefaultTaskResultObserver taskResultObserver = new DefaultTaskResultObserver();
					TaskRunner taskRunner = new TaskRunner(catalog, taskResultObserver);
					taskRunner.run();
					if (catalog.getCatalogStatus() == CatalogStatus.DONE) {
						done = true;
					} else {
						UpdateRequest request = new UpdateRequest(serviceURI, catalog.getSessionId(), projectName);
						catalog = request.send(taskResultObserver.getResult());
						log.normal().append("received catalog: " + count).print();
						log.normal().append(catalog.toString()).print();
					}
					count++;
				}
			} catch (ConfigurationException e) {
				throw new CliException(e);
			} catch (URISyntaxException e) {
				throw new CliException(e);
			} catch (IOException e) {
				throw new CliException(e);
			} catch (HttpException e) {
				throw new CliException(e);
			} finally {
				log.faint().append("\n--- done ---").print();
			}
		}
	}
}
