package nl.agiletech.flow.common.cli;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Command {
	private final String name;
	private final List<Option> options = new ArrayList<>();
	private CommandExecutor executor;
	private final String[] args;

	public Command(String name, String[] args) {
		this.name = name;
		this.args = args;
	}

	public void addOption(Option option) {
		this.options.add(option);
	}

	public void setExecutor(CommandExecutor executor) {
		this.executor = executor;
	}

	public String getName() {
		return name;
	}

	public Options getOptions() {
		Options options = new Options();
		for (Option option : this.options) {
			options.addOption(option);
		}
		return options;
	}

	public String[] getArgs() {
		return args;
	}

	public CommandExecutor getExecutor() {
		return executor;
	}

}
