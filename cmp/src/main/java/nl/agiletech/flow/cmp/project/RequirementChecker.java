package nl.agiletech.flow.cmp.project;

import java.util.Set;
import java.util.logging.Logger;

import nl.agiletech.flow.common.cli.logging.Color;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.reflect.ClassUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.ContextError;
import nl.agiletech.flow.project.types.Requirement;

public class RequirementChecker {
	private static final Logger LOG = Logger.getLogger(RequirementChecker.class.getName());

	public static RequirementChecker createInstance(Context context, ProjectConfiguration projectConfiguration) {
		return new RequirementChecker(context, projectConfiguration);
	}

	final Context context;
	final ProjectConfiguration projectConfiguration;

	public RequirementChecker(Context context, ProjectConfiguration projectConfiguration) {
		Assertions.notNull(context, "context");
		Assertions.notNull(projectConfiguration, "projectConfiguration");
		this.context = context;
		this.projectConfiguration = projectConfiguration;
	}

	/**
	 * Checks that all requirements are satisfiable.
	 */
	public void satisfy() {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			log.normal().append("checking requirements:").print();
			for (Object obj : projectConfiguration.getDependencyIndex().keySet()) {
				assertSatisfiable(obj);
			}
		}
	}

	private void assertSatisfiable(Object obj) {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			if (!ClassUtil.isSubclassOf(obj.getClass(), Requirement.class)) {
				return;
			}
			// search for an object in the dependency index that can satisfy the
			// dependency
			String dependencyName = DependencyUtil.getDependencyName(obj);
			boolean satisfied = false;
			for (Object real : projectConfiguration.getDependencyIndex().keySet()) {
				ProjectClassType projectClassType = ProjectClassUtil.getProjectClassType(real.getClass());
				if (projectClassType.isTaskSubclass()) {
					String objName = DependencyUtil.getDependencyName(real);
					if (objName.equals(dependencyName)) {
						satisfied = true;
						log.normal().foreground(Color.GREEN)
								.append("  +requirement: " + dependencyName + " is satisfied by: " + objName).print();
						logDeclaredInList(log, obj);
						logSatisfiedByList(log, real);
						break;
					}
				}
			}
			if (!satisfied) {
				context.addError(ContextError.createInstance("requirement: " + dependencyName + " not satisfiable"));
				log.error().append("  -requirement: " + dependencyName + " not satisfiable").print();
				logDeclaredInList(log, obj);
			}
		}
	}

	private void logDeclaredInList(ConsoleUtil log, Object obj) {
		Set<Class<?>> declaredInClasses = projectConfiguration.getDependencyIndex().get(obj);
		for (Class<?> declaredIn : declaredInClasses) {
			log.normal().append("    declared-in: " + declaredIn).print();
		}
	}

	private void logSatisfiedByList(ConsoleUtil log, Object obj) {
		Set<Class<?>> declaredInClasses = projectConfiguration.getDependencyIndex().get(obj);
		for (Class<?> declaredIn : declaredInClasses) {
			log.normal().append("    satisfied-in: " + declaredIn).print();
		}
	}
}
