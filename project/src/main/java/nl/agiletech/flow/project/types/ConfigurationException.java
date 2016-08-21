package nl.agiletech.flow.project.types;

@SuppressWarnings("serial")
public class ConfigurationException extends Exception {
	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(Throwable cause) {
		super(cause);
	}
}
