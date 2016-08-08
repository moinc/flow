package nl.agiletech.flow.bot;

import org.apache.commons.cli.CommandLine;

import nl.agiletech.flow.common.cli.AppState;
import nl.agiletech.flow.common.cli.Command;
import nl.agiletech.flow.common.cli.CommandExecutor;
import nl.agiletech.flow.common.cli.CommandInfo;
import nl.agiletech.flow.common.shell.Shell;
import nl.agiletech.flow.common.shell.ShellResult;

public class InspectCommand implements CommandInfo, CommandExecutor {

	public enum InspectKey {
		hostname
	}

	@Override
	public String getName() {
		return CommandName.inspect.name();
	}

	@Override
	public String getDescription() {
		return "Inspects the local system";
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
		inspect("hostname", "-c");
		inspect("pwd");
		inspect("huh");
	}

	private void inspect(String... commands) {
		System.out.println("- inspecting: \"" + String.join(" ", commands) + "\"");

		ShellResult result = Shell.execute(null, commands);

		System.out.println("  exit code: " + result.getExitCode());
		System.out.println("  time: " + result.getTime() + "ms");
		if (result.isSuccess()) {
			System.out.println("  output:");
			for (String output : result.getOutput()) {
				System.out.println("    " + output);
			}
		} else {
			System.out.println("  errors:");
			for (String err : result.getErrors()) {
				System.out.println("    " + err);
			}
		}
	}

}
