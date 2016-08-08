package nl.agiletech.flow.common.cli.commands;

import org.apache.commons.cli.CommandLine;

import nl.agiletech.flow.common.cli.AppState;
import nl.agiletech.flow.common.cli.AppStateProperty;
import nl.agiletech.flow.common.cli.Command;
import nl.agiletech.flow.common.cli.CommandExecutor;
import nl.agiletech.flow.common.cli.CommandFactory;
import nl.agiletech.flow.common.cli.CommandInfo;
import nl.agiletech.flow.common.cli.Table;

public class InteractiveCommand implements CommandInfo, CommandExecutor {
	private final CommandFactory commandFactory;

	public InteractiveCommand(CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	@Override
	public String getName() {
		return "interactive";
	}

	@Override
	public String getDescription() {
		return "Interactive mode";
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
		if (appState.getPropertyAsBool(AppStateProperty.noargs)) {
			System.out
					.println("usage: " + appState.getPropertyAsString(AppStateProperty.title) + " <command> <options>");
			System.out.println("available commands:");
			Table table = Table.getInstance(2);
			for (CommandInfo commandInfo : commandFactory.getRegisteredCommandInfos()) {
				table.addRow(commandInfo.getName(), commandInfo.getDescription());
			}
			table.print(System.out);
		} else {
			// interactive mode
			System.out.println("selected interactive mode");
			appState.setProperty(AppStateProperty.interactive, true);
		}
	}

}
