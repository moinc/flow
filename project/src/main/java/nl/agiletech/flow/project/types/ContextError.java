package nl.agiletech.flow.project.types;

import nl.agiletech.flow.common.util.Assertions;

public class ContextError {
	String message;

	public static ContextError createInstance(String message) {
		return new ContextError(message);
	}

	public ContextError(String message) {
		Assertions.notEmpty(message, "message");
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
