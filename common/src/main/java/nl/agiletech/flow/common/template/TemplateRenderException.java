/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.template;

@SuppressWarnings("serial")
public class TemplateRenderException extends TransformException {
	public TemplateRenderException() {
		super();
	}

	public TemplateRenderException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateRenderException(String message) {
		super(message);
	}

	public TemplateRenderException(Throwable cause) {
		super(cause);
	}
}
