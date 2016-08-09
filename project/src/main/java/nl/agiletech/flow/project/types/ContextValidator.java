package nl.agiletech.flow.project.types;

import java.util.List;

public interface ContextValidator {
	void validate(Context context, List<String> errors);
}
