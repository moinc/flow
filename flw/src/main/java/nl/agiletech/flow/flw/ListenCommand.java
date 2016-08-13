/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.flw;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.apache.commons.cli.CommandLine;

import nl.agiletech.flow.common.cli.AppState;
import nl.agiletech.flow.common.cli.CliException;
import nl.agiletech.flow.common.cli.Command;
import nl.agiletech.flow.common.cli.CommandExecutor;
import nl.agiletech.flow.common.cli.CommandInfo;
import nl.agiletech.flow.flw.http.ResourceRouterHttpHandler;
import nl.agiletech.flow.flw.http.SimpleHttpServer;
import nl.agiletech.flow.flw.listeners.ServletContextListenerTest;
import nl.agiletech.flow.flw.matchers.AllMatchers;
import nl.agiletech.flow.flw.servlets.InspectServlet;
import nl.agiletech.flow.flw.servlets.ResourceServlet;
import nl.agiletech.flow.flw.servlets.UpdateServlet;
import nl.agiletech.flow.project.types.ConfigurationSettings;

public class ListenCommand implements CommandInfo, CommandExecutor {
	private static final Logger LOG = Logger.getLogger(ListenCommand.class.getName());

	@Override
	public String getName() {
		return CommandName.listen.name();
	}

	@Override
	public String getDescription() {
		return "Starts the HTTP server";
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
		// process options and arguments
		File flowConfigFile = null;
		if (commandLine.hasOption("c")) {
			String flowConfigPath = commandLine.getOptionValue("c");
			flowConfigFile = new File(flowConfigPath);
		}
		assert flowConfigFile != null && flowConfigFile.exists();

		// load files
		ConfigurationSettings configurationSettings = null;
		try {
			configurationSettings = ConfigurationSettings.loadFrom(flowConfigFile);
		} catch (IOException e) {
			throw new CliException("failed to read flow configuration file", e);
		}

		ResourceRouterHttpHandler resourceRouter = new ResourceRouterHttpHandler(configurationSettings);
		try {
			resourceRouter.addServlet(AllMatchers.createInspectMatcher(), new InspectServlet());
			resourceRouter.addServlet(AllMatchers.createUpdateMatcher(), new UpdateServlet());
			resourceRouter.addServlet(AllMatchers.createResourceMatcher(), new ResourceServlet());
		} catch (ServletException e) {
			throw new CliException(e);
		}

		SimpleHttpServer server = new SimpleHttpServer(configurationSettings, resourceRouter);
		server.addListener(new ServletContextListenerTest());

		try {
			server.start(8080);
		} catch (IOException e) {
			throw new CliException(e);
		} catch (ServletException e) {
			throw new CliException(e);
		}
	}

}
