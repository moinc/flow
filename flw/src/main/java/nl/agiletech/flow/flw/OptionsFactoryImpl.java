/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.flw;

import org.apache.commons.cli.Option;

import nl.agiletech.flow.common.cli.Command;
import nl.agiletech.flow.common.cli.OptionsFactory;

public final class OptionsFactoryImpl implements OptionsFactory {
	public void createCommandOptions(Command command) {
		CommandName commandName = CommandName.valueOf(command.getName());
		switch (commandName) {
		case listen:
			Option flowConfigOption = new Option("c", "config", true, "Path to flow configuration");
			flowConfigOption.setType(String.class);
			flowConfigOption.setRequired(true);
			command.addOption(flowConfigOption);
			break;
		}

		Option verboseOption = new Option("v", "verbose", false, "Verbose logging");
		verboseOption.setRequired(false);
		command.addOption(verboseOption);
	}
}
