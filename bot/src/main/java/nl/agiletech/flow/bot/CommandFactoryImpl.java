package nl.agiletech.flow.bot;

import nl.agiletech.flow.common.cli.DefaultCommandFactoryImpl;

public class CommandFactoryImpl extends DefaultCommandFactoryImpl {
	@Override
	public void registerImplementationSpecificCommandInfos() {
		registerCommandInfo(new UpdateCommand());
	}
}
