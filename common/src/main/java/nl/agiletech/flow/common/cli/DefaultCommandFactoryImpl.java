/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.cli;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class DefaultCommandFactoryImpl implements CommandFactory {
	private Map<String, CommandInfo> registeredCommandInfos = new HashMap<>();

	@Override
	public void registerCommandInfo(CommandInfo commandInfo) {
		registeredCommandInfos.put(commandInfo.getName(), commandInfo);
	}

	@Override
	public CommandInfo getRegisteredCommandInfo(String name) {
		return registeredCommandInfos.get(name);
	}

	@Override
	public Collection<CommandInfo> getRegisteredCommandInfos() {
		return registeredCommandInfos.values();
	}

	public Command create(String[] args) throws CliException {
		String name;
		String[] otherArgs;
		if (args == null || args.length == 0) {
			name = DefaultCommandName.interactive.name();
			otherArgs = new String[0];
		} else {
			name = args[0];
			otherArgs = new String[args.length - 1];
			for (int i = 1; i < args.length; i++) {
				otherArgs[i - 1] = args[i];
			}
		}
		Command command = new Command(name, otherArgs);
		CommandInfo commandInfo = getRegisteredCommandInfo(name);
		if (commandInfo == null) {
			throw new CliException("command '" + name + "' not implemented");
		}
		command.setExecutor(commandInfo.getCommandExecutor());
		return command;
	}

	@Override
	public boolean isInteractive(Command command) {
		return command.getName().equals(DefaultCommandName.interactive.name());
	}

}
