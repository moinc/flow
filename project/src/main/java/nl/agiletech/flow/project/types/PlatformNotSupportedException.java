package nl.agiletech.flow.project.types;

import java.io.IOException;

@SuppressWarnings("serial")
public class PlatformNotSupportedException extends IOException {
	public PlatformNotSupportedException() {
		super();
	}

	public PlatformNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlatformNotSupportedException(String message) {
		super(message);
	}

	public PlatformNotSupportedException(Throwable cause) {
		super(cause);
	}
}
