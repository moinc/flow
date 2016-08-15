package nl.agiletech.flow.project.types;

import java.util.List;

public interface ContextValidator {
	public static final ContextValidator NULL_VALIDATOR = new ContextValidator() {
		@Override
		public void validate(Context context, List<String> errors) {
			// the null validator does nothing
		}
	};

	void validate(Context context, List<String> errors);
}
