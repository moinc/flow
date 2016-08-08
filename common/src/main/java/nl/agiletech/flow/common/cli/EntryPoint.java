/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

import nl.agiletech.flow.common.cli.commands.ExitCommand;
import nl.agiletech.flow.common.cli.commands.HelpCommand;
import nl.agiletech.flow.common.cli.commands.InteractiveCommand;
import nl.agiletech.flow.common.cli.commands.VersionCommand;

public class EntryPoint {
	public static EntryPoint getInstance(AppState appState, CommandFactory commandFactory,
			OptionsFactory optionsFactory) {
		return new EntryPoint(appState, commandFactory, optionsFactory);
	}

	private static final Pattern pat = Pattern.compile("([^\"]\\S*|\".+?\")\\s*");
	private final AppState appState;
	private final CommandFactory commandFactory;
	private final OptionsFactory optionsFactory;

	private EntryPoint(AppState appState, CommandFactory commandFactory, OptionsFactory optionsFactory) {
		this.appState = appState;
		this.commandFactory = commandFactory;
		this.optionsFactory = optionsFactory;

		// register the default commands
		commandFactory.registerCommandInfo(new InteractiveCommand(commandFactory));
		commandFactory.registerCommandInfo(new HelpCommand(commandFactory, optionsFactory));
		commandFactory.registerCommandInfo(new ExitCommand());
		commandFactory.registerCommandInfo(new VersionCommand());
		// allow the implementation to register specific commands
		commandFactory.registerImplementationSpecificCommandInfos();
	}

	public void main(String[] args) {
		try {
			boolean noArgs = (args == null || args.length == 0);
			appState.setProperty(AppStateProperty.noargs, noArgs);
			CommandLineParser parser = new DefaultParser();
			Command command = commandFactory.create(args);
			if (commandFactory.isInteractive(command)) {
				executeCommand(parser, command, appState);
				if (appState.getPropertyAsBool(AppStateProperty.interactive)) {
					try (Scanner scanner = new Scanner(System.in)) {
						do {
							System.out.print("> ");
							String[] nextArgs = lineToArgs(scanner.nextLine());
							command = commandFactory.create(nextArgs);
							executeCommand(parser, command, appState);
						} while (!appState.getPropertyAsBool(AppStateProperty.exit));
					}
				}
			} else {
				executeCommand(parser, command, appState);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("Stack trace:");
			e.printStackTrace(System.err);
		}
	}

	private String[] lineToArgs(String line) {
		List<String> args = new ArrayList<>();
		Matcher m = pat.matcher(line);
		while (m.find()) {
			args.add(m.group(1));
		}
		return args.toArray(new String[0]);
	}

	private void executeCommand(CommandLineParser parser, Command command, AppState appState)
			throws ParseException, CliException {
		optionsFactory.createCommandOptions(command);
		CommandLine commandLine = parser.parse(command.getOptions(), command.getArgs());
		command.getExecutor().execute(command, commandLine, appState);
	}
}
