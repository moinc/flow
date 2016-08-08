/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.cli.commands;

import org.apache.commons.cli.CommandLine;

import nl.agiletech.flow.common.cli.AppState;
import nl.agiletech.flow.common.cli.AppStateProperty;
import nl.agiletech.flow.common.cli.Command;
import nl.agiletech.flow.common.cli.CommandExecutor;
import nl.agiletech.flow.common.cli.CommandInfo;

public class VersionCommand implements CommandInfo, CommandExecutor {

	@Override
	public String getName() {
		return "version";
	}

	@Override
	public String getDescription() {
		return "Version number";
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
	public void execute(Command command, CommandLine commandLine, AppState appState) {
		System.out.println(appState.getPropertyAsString(AppStateProperty.version));
	}

}
