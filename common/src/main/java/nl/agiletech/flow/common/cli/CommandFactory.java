package nl.agiletech.flow.common.cli;

import java.util.Collection;

public interface CommandFactory {
	public void registerCommandInfo(CommandInfo commandInfo);

	public void registerImplementationSpecificCommandInfos();

	public Command create(String[] args) throws CliException;

	public CommandInfo getRegisteredCommandInfo(String name);

	public Collection<CommandInfo> getRegisteredCommandInfos();

	public boolean isInteractive(Command command);
}
