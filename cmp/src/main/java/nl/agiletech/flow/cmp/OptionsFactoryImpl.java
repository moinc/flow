/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp;

import org.apache.commons.cli.Option;

import nl.agiletech.flow.common.cli.Command;
import nl.agiletech.flow.common.cli.OptionsFactory;

public final class OptionsFactoryImpl implements OptionsFactory {
	public void createCommandOptions(Command command) {
		switch (command.getName()) {
		case "compile":
			Option flowConfigOption = new Option("c", "config", true, "Path to flow configuration");
			flowConfigOption.setType(String.class);
			flowConfigOption.setRequired(true);
			command.addOption(flowConfigOption);

			Option projectOption = new Option("p", "project", true, "Path to flow project");
			projectOption.setType(String.class);
			projectOption.setRequired(true);
			command.addOption(projectOption);

			Option requesttypeOption = new Option("r", "requesttype", true,
					"The request type: INFORMATION_RETRIEVAL | UPDATE | RESOURCE | REPORT");
			requesttypeOption.setType(String.class);
			requesttypeOption.setRequired(true);
			command.addOption(requesttypeOption);

			Option nodedataOption = new Option("n", "nodedata", true, "Node data file");
			nodedataOption.setType(String.class);
			nodedataOption.setRequired(false);
			command.addOption(nodedataOption);

			break;
		}

		Option verboseOption = new Option("v", "verbose", false, "Verbose logging");
		verboseOption.setRequired(false);
		command.addOption(verboseOption);
	}
}
