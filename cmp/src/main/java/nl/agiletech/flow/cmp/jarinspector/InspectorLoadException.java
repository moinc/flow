/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.jarinspector;

@SuppressWarnings("serial")
public class InspectorLoadException extends Exception {
	public InspectorLoadException() {
		super();
	}

	public InspectorLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	public InspectorLoadException(String message) {
		super(message);
	}

	public InspectorLoadException(Throwable cause) {
		super(cause);
	}
}
