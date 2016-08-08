/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.cli;

@SuppressWarnings("serial")
public class CliException extends Exception {

	public CliException() {
		super();
	}

	public CliException(String message, Throwable cause) {
		super(message, cause);
	}

	public CliException(String message) {
		super(message);
	}

	public CliException(Throwable cause) {
		super(cause);
	}

}
