/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.cli;

public interface CommandInfo {
	public String getName();

	public String getDescription();

	public CommandExecutor getCommandExecutor();
}
