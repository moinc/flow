/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.cli;

import org.apache.commons.cli.CommandLine;

public interface CommandExecutor {
	public CommandInfo getCommandInfo();

	public void execute(Command command, CommandLine commandLine, AppState appState) throws CliException;
}
