/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.template;

import java.io.IOException;

@SuppressWarnings("serial")
public class TransformException extends IOException {
	public TransformException() {
		super();
	}

	public TransformException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransformException(String message) {
		super(message);
	}

	public TransformException(Throwable cause) {
		super(cause);
	}
}
