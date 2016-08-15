package nl.agiletech.flow.project.types;

@SuppressWarnings("serial")
public class ConditionEvaluationException extends RuntimeException {
	public ConditionEvaluationException() {
		super();
	}

	public ConditionEvaluationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConditionEvaluationException(String message) {
		super(message);
	}

	public ConditionEvaluationException(Throwable cause) {
		super(cause);
	}
}
