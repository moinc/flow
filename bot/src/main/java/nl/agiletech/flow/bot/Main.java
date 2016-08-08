package nl.agiletech.flow.bot;

import nl.agiletech.flow.common.cli.AppState;
import nl.agiletech.flow.common.cli.AppStateProperty;
import nl.agiletech.flow.common.cli.EntryPoint;

public class Main {
	public static void main(String[] args) throws Exception {
		AppState appState = new AppState();
		appState.setProperty(AppStateProperty.title, "bot");
		appState.setProperty(AppStateProperty.version, "1.0");
		EntryPoint.getInstance(appState, new CommandFactoryImpl(), new OptionsFactoryImpl()).main(args);
	}
}
