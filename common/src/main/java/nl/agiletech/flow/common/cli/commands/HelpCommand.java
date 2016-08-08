package nl.agiletech.flow.common.cli.commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import nl.agiletech.flow.common.cli.AppState;
import nl.agiletech.flow.common.cli.AppStateProperty;
import nl.agiletech.flow.common.cli.CliException;
import nl.agiletech.flow.common.cli.Command;
import nl.agiletech.flow.common.cli.CommandExecutor;
import nl.agiletech.flow.common.cli.CommandFactory;
import nl.agiletech.flow.common.cli.CommandInfo;
import nl.agiletech.flow.common.cli.OptionsFactory;
import nl.agiletech.flow.common.cli.Table;

public class HelpCommand implements CommandInfo, CommandExecutor {
	private final CommandFactory commandFactory;
	private final OptionsFactory optionsFactory;

	public HelpCommand(CommandFactory commandFactory, OptionsFactory optionsFactory) {
		this.commandFactory = commandFactory;
		this.optionsFactory = optionsFactory;
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Usage information";
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
		Command requestedCommand = commandFactory.create(commandLine.getArgs());
		if (commandFactory.isInteractive(requestedCommand)) {
			System.out.println("usage: " + appState.getPropertyAsString(AppStateProperty.title) + " "
					+ command.getExecutor().getCommandInfo().getName() + " <command>");
		} else {
			System.out.println("usage: " + appState.getPropertyAsString(AppStateProperty.title) + " "
					+ command.getExecutor().getCommandInfo().getName() + " "
					+ requestedCommand.getExecutor().getCommandInfo().getName());
			System.out.println("description: " + requestedCommand.getExecutor().getCommandInfo().getDescription());
			optionsFactory.createCommandOptions(requestedCommand);
			if (requestedCommand.getOptions().getOptions().isEmpty()) {
				System.out.println("available options: none");
			} else {
				System.out.println("available options:");
				Table table = Table.getInstance(4);
				table.setColumnSeparators(", ", ": ", "; ");
				for (Option option : requestedCommand.getOptions().getOptions()) {
					table.addRow("-" + option.getOpt(), "--" + option.getLongOpt(), option.getDescription(),
							option.isRequired() ? "" : "required");
				}
				table.print(System.out);
			}
		}
	}

}
