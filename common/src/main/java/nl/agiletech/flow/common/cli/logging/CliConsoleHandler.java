package nl.agiletech.flow.common.cli.logging;

import java.util.logging.ConsoleHandler;

public class CliConsoleHandler extends ConsoleHandler {
	public CliConsoleHandler() {
		super();
		setOutputStream(System.out);
	}
}
