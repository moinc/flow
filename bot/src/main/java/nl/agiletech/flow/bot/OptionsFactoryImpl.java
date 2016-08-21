package nl.agiletech.flow.bot;

import org.apache.commons.cli.Option;

import nl.agiletech.flow.common.cli.Command;
import nl.agiletech.flow.common.cli.OptionsFactory;

public final class OptionsFactoryImpl implements OptionsFactory {
	public void createCommandOptions(Command command) {
		switch (command.getName()) {
		case "update":
			Option flowConfigOption = new Option("c", "config", true, "Path to flow configuration");
			flowConfigOption.setType(String.class);
			flowConfigOption.setRequired(true);
			command.addOption(flowConfigOption);

			Option projectOption = new Option("p", "project", true, "Project name");
			projectOption.setType(String.class);
			projectOption.setRequired(true);
			command.addOption(projectOption);

			Option serviceURIOption = new Option("u", "serviceuri", true, "Override configured service URI");
			serviceURIOption.setType(String.class);
			serviceURIOption.setRequired(false);
			command.addOption(serviceURIOption);
			break;
		}

		Option verboseOption = new Option("v", "verbose", false, "Verbose logging");
		verboseOption.setRequired(false);
		command.addOption(verboseOption);
	}
}
