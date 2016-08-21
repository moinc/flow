package nl.agiletech.flow.cmp.compiler.builtin;

import java.util.List;

import nl.agiletech.flow.cmp.project.ProjectConfiguration;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.ContextError;
import nl.agiletech.flow.project.types.ContextValidator;
import nl.agiletech.flow.project.types.Filter;
import nl.agiletech.flow.project.types.Platform;

public class DefaultContextValidator implements ContextValidator {
	private static final String ERR_NODE_NOT_FOUND = "node not found";
	private static final String ERR_NO_DEPENDENCIES_FOUND = "no dependencies were found in the current project";
	private static final String ERR_NO_PLATFORM_FOUND = "expected platform in dependency tree";

	final ProjectConfiguration projectConfiguration;

	public DefaultContextValidator(ProjectConfiguration projectConfiguration) {
		this.projectConfiguration = projectConfiguration;
	}

	private final Filter<Object> dependencyFilter = new Filter<Object>() {
		@Override
		public boolean include(Object value) {
			return true;
		}
	};
	private final Filter<Object> platformDependencyFilter = new Filter<Object>() {
		@Override
		public boolean include(Object value) {
			return (value instanceof Platform);
		}
	};

	@Override
	public void validate(Context context, List<String> errors) {
		Assertions.notNull(context, "context");
		Assertions.notNull(errors, "errors");
		if (context.getNode() == null) {
			errors.add(ERR_NODE_NOT_FOUND);
		}
		if (context.getDependencies(dependencyFilter).isEmpty()) {
			errors.add(ERR_NO_DEPENDENCIES_FOUND);
		}
		if (context.getDependencies(platformDependencyFilter).isEmpty()) {
			errors.add(ERR_NO_PLATFORM_FOUND);
		}
		if (context.getErrors().size() != 0) {
			for (ContextError contextError : context.getErrors()) {
				errors.add(contextError.getMessage());
			}
		}
	}
}
