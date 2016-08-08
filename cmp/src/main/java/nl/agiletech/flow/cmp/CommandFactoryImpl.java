package nl.agiletech.flow.cmp;

import nl.agiletech.flow.common.cli.DefaultCommandFactoryImpl;

public class CommandFactoryImpl extends DefaultCommandFactoryImpl {
	@Override
	public void registerImplementationSpecificCommandInfos() {
		registerCommandInfo(new CompileCommand());
	}
}
