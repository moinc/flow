/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp;

import nl.agiletech.flow.common.cli.AppState;
import nl.agiletech.flow.common.cli.AppStateProperty;
import nl.agiletech.flow.common.cli.EntryPoint;
import nl.agiletech.flow.common.util.LogUtil;
import nl.agiletech.flow.common.util.StringUtil;

public class Main {
	public static void main(String[] args) throws Exception {
		// setup logging
		String allArgs = StringUtil.join(args, " ");
		LogUtil.configure(Main.class, (allArgs.contains("-v") || allArgs.contains("--verbose"))
				? "verboseLogging.properties" : "logging.properties");

		AppState appState = new AppState();
		appState.setProperty(AppStateProperty.title, "cmp");
		appState.setProperty(AppStateProperty.version, "1.0");
		EntryPoint.getInstance(appState, new CommandFactoryImpl(), new OptionsFactoryImpl()).main(args);
	}
}
