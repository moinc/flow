/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.flw;

import nl.agiletech.flow.common.cli.DefaultCommandFactoryImpl;

public class CommandFactoryImpl extends DefaultCommandFactoryImpl {
	@Override
	public void registerImplementationSpecificCommandInfos() {
		registerCommandInfo(new ListenCommand());
	}
}
