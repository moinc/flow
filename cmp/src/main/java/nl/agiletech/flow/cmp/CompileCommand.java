/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;

import nl.agiletech.flow.cmp.compiler.CompileException;
import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompiler;
import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompilerOptions;
import nl.agiletech.flow.cmp.exec.ProjectExecutor;
import nl.agiletech.flow.common.cli.AppState;
import nl.agiletech.flow.common.cli.CliException;
import nl.agiletech.flow.common.cli.Command;
import nl.agiletech.flow.common.cli.CommandExecutor;
import nl.agiletech.flow.common.cli.CommandInfo;
import nl.agiletech.flow.common.cli.logging.Color;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.io.FileUtil;
import nl.agiletech.flow.common.io.TempFile;
import nl.agiletech.flow.project.types.ConfigurationSettings;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.NodeData;
import nl.agiletech.flow.project.types.RequestType;

public class CompileCommand implements CommandInfo, CommandExecutor {
	private static final Logger LOG = Logger.getLogger(CompileCommand.class.getName());

	@Override
	public String getName() {
		return CommandName.compile.name();
	}

	@Override
	public String getDescription() {
		return "Compiles a project";
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
		try (ConsoleUtil log = ConsoleUtil.OUT) {
			// process options and arguments

			File flowConfigFile = null;
			if (commandLine.hasOption("c")) {
				String flowConfigPath = commandLine.getOptionValue("c");
				flowConfigFile = new File(flowConfigPath);
			}
			assert flowConfigFile != null && flowConfigFile.exists();

			File projectFile = null;
			if (commandLine.hasOption("p")) {
				String projectPath = commandLine.getOptionValue("p");
				projectFile = new File(projectPath);
			}
			assert projectFile != null && projectFile.exists();

			RequestType requestType = RequestType.UNDEFINED;
			if (commandLine.hasOption("r")) {
				String requestTypeString = commandLine.getOptionValue("r");
				requestType = RequestType.valueOf(requestTypeString);
			}

			File nodeDataFile = null;
			if (commandLine.hasOption("n")) {
				String nodeDataPath = commandLine.getOptionValue("n");
				nodeDataFile = new File(nodeDataPath);
				assert nodeDataFile != null && nodeDataFile.exists();
			}

			// load files

			ConfigurationSettings configurationSettings = null;
			try {
				configurationSettings = ConfigurationSettings.loadFrom(flowConfigFile);
			} catch (IOException e) {
				throw new CliException("failed to read flow configuration file", e);
			}

			NodeData nodeData = null;
			if (nodeDataFile != null) {
				try {
					nodeData = NodeData.loadFrom(nodeDataFile);
				} catch (IOException e) {
					throw new CliException("failed to read node data", e);
				}
			}

			try {
				// compile project
				DefaultCompilerOptions compileOptions = DefaultCompilerOptions.createInstance(configurationSettings,
						projectFile, requestType, nodeData);
				Context context = DefaultCompiler.createInstance(compileOptions).compile();

				// execute the project

				File temp = TempFile.create();
				try (OutputStream output = new FileOutputStream(temp)) {
					ProjectExecutor.createInstance(output, context).run();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// print output to console
				log.faint().append("--- output ---").print();
				// LOG.info("--- output ---");
				FileUtil.copy(temp, System.out);
				log.faint().append("\n--- done ---").print();
				// LOG.info("\n--- done ---");

				// delete the temp file
				temp.delete();

			} catch (CompileException e) {
				throw new CliException(e);
			} catch (IOException e) {
				throw new CliException(e);
			} catch (Exception e) {
				throw new CliException(e);
			}
		}
	}

}
