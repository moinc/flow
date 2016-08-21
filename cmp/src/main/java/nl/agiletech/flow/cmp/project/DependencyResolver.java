/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.cmp.jarinspector.ObjectDiscoveryOptions;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.Switchable;

public class DependencyResolver {
	private static final Logger LOG = Logger.getLogger(DependencyResolver.class.getName());

	public static DependencyResolver createInstance(Context context, DependencyObserver dependencyObserver) {
		return new DependencyResolver(context, dependencyObserver);
	}

	final Context context;
	final DependencyObserver dependencyObserver;
	final ObjectDiscoveryOptions options = ObjectDiscoveryOptions.createInstanceForDependencyDiscovery();

	private DependencyResolver(Context context, DependencyObserver dependencyObserver) {
		Assertions.notNull(context, "context");
		Assertions.notNull(dependencyObserver, "dependencyObserver");
		this.context = context;
		this.dependencyObserver = dependencyObserver;
	}

	public List<Object> resolve() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			log.normal().append("resolving dependencies:").print();
			Node node = context.getNode();
			List<Object> resolvedObjects = new ArrayList<>();
			DependencyIndex index = new DependencyIndex();
			resolve(context.getClass(), node, resolvedObjects, index);
			context.setDependencies(resolvedObjects);
			return resolvedObjects;
		}
	}

	private void resolve(Class<?> declaredIn, Object obj, List<Object> resolvedObjects, DependencyIndex index)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assertions.notNull(declaredIn, "declaredIn");
		Assertions.notNull(obj, "obj");
		Assertions.notNull(resolvedObjects, "resolvedObjects");
		Assertions.notNull(index, "index");
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			ProjectClassType projectClassType = ProjectClassUtil.getProjectClassType(obj.getClass());
			if (isProjectClassTypeIgnored(projectClassType)) {
				return;
			}
			Map<String, Object> dependencies = ClassUtil.discoverObjects(obj, context, options);
			String objName = DependencyUtil.getDependencyName(obj);
			setResolved(index, declaredIn, objName, projectClassType);
			Class<?> dependencyDeclaredIn = obj.getClass();
			for (Object dependency : dependencies.values()) {
				ProjectClassType dependencyProjectClassType = ProjectClassUtil
						.getProjectClassType(dependency.getClass());
				// determine the name of the dependency
				String dependencyName = DependencyUtil.getDependencyName(dependency);

				if (!Switchable.isEnabled(dependency)) {
					logDisabledObj(log, dependencyDeclaredIn, dependencyName, dependencyProjectClassType);
					continue;
				}

				if (isAlreadyResolved(index, dependencyName, dependencyProjectClassType)) {
					logDuplicateObj(log, dependencyDeclaredIn, dependencyName, dependencyProjectClassType);
					continue;
				}

				resolve(obj.getClass(), dependency, resolvedObjects, index);
			}
			resolvedObjects.add(obj);
			dependencyObserver.observe(obj, declaredIn);
			logResolvedObj(log, declaredIn, objName, projectClassType);
		}
	}

	private boolean isProjectClassTypeIgnored(ProjectClassType projectClassType) {
		return projectClassType == ProjectClassType.UNDEFINED;
	}

	private void setResolved(DependencyIndex index, Class<?> declaredIn, String objName,
			ProjectClassType projectClassType) {
		Assertions.notNull(index, "index");
		Assertions.notNull(declaredIn, "declaredIn");
		Assertions.notEmpty(objName, "objName");
		Assertions.notEmpty(projectClassType, "projectClassType");
		index.put(declaredIn, objName, projectClassType);
	}

	private boolean isAlreadyResolved(DependencyIndex index, String objName, ProjectClassType projectClassType) {
		Assertions.notNull(index, "index");
		Assertions.notEmpty(objName, "objName");
		return index.contains(objName, projectClassType);
	}

	private void logResolvedObj(ConsoleUtil log, Class<?> declaredIn, String objName,
			ProjectClassType projectClassType) {
		Assertions.notNull(log, "log");
		Assertions.notNull(declaredIn, "declaredIn");
		Assertions.notEmpty(objName, "objName");
		Assertions.notNull(projectClassType, "projectClassType");
		log.normal().append(
				"  +dependency: " + objName + " (" + projectClassType + ") declared in: " + declaredIn.getSimpleName())
				.print();
	}

	private void logDuplicateObj(ConsoleUtil log, Class<?> declaredIn, String objName,
			ProjectClassType projectClassType) {
		Assertions.notNull(log, "log");
		Assertions.notNull(declaredIn, "declaredIn");
		Assertions.notEmpty(objName, "objName");
		Assertions.notEmpty(projectClassType, "projectClassType");
		log.faint().append("  ~dependency: " + objName + " (" + projectClassType + ") declared in: "
				+ declaredIn.getSimpleName() + " <-- duplicate").print();
	}

	private void logDisabledObj(ConsoleUtil log, Class<?> declaredIn, String objName,
			ProjectClassType projectClassType) {
		Assertions.notNull(log, "log");
		Assertions.notNull(declaredIn, "declaredIn");
		Assertions.notEmpty(objName, "objName");
		Assertions.notEmpty(projectClassType, "projectClassType");
		log.faint().append("  -dependency: " + objName + " (" + projectClassType + ") declared in: "
				+ declaredIn.getSimpleName() + " <-- disabled ").print();
	}
}
