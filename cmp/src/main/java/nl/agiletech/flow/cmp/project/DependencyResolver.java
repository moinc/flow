/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.cmp.jarinspector.ObjectDiscoveryOptions;
import nl.agiletech.flow.project.types.Component;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.CustomComponent;
import nl.agiletech.flow.project.types.CustomFile;
import nl.agiletech.flow.project.types.File;
import nl.agiletech.flow.project.types.Inspector;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.Switchable;
import nl.agiletech.flow.project.types.Template;

public class DependencyResolver {
	private static final Logger LOG = Logger.getLogger(DependencyResolver.class.getName());

	public static DependencyResolver createInstance(Context context, DependencyObserver dependencyObserver) {
		return new DependencyResolver(context, dependencyObserver);
	}

	final Context context;
	final DependencyObserver dependencyObserver;
	final ObjectDiscoveryOptions options = ObjectDiscoveryOptions.createInstanceForDependencyDiscovery();

	private DependencyResolver(Context context, DependencyObserver dependencyObserver) {
		assert context != null && dependencyObserver != null;
		this.context = context;
		this.dependencyObserver = dependencyObserver;
	}

	public List<Object> resolve() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		LOG.info("resolving dependencies:");
		Node node = context.getNode();
		List<Object> resolvedObjects = new ArrayList<>();
		Map<String, Class<?>> index = new HashMap<>();
		resolve(context.getClass(), node, resolvedObjects, index);
		context.setDependencies(resolvedObjects);
		return resolvedObjects;
	}

	private void resolve(Class<?> declaredIn, Object obj, List<Object> resolvedObjects, Map<String, Class<?>> index)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		assert declaredIn != null && obj != null && resolvedObjects != null && index != null;
		ProjectClassType projectClassType = ProjectClassUtil.getProjectClassType(obj.getClass());
		if (projectClassType == ProjectClassType.UNDEFINED) {
			return;
		}
		Map<String, Object> dependencies = ClassUtil.discoverObjects(obj, context, options);
		String objName = getDependencyName(obj);
		index.put(objName, declaredIn);
		for (Object dependency : dependencies.values()) {
			// determine the name of the dependency
			String dependencyName = getDependencyName(dependency);
			if (!Switchable.isEnabled(dependency)) {
				ProjectClassType dependencyProjectClassType = ProjectClassUtil
						.getProjectClassType(dependency.getClass());
				LOG.info("  -dependency: " + dependencyName + " (" + dependencyProjectClassType + ") <-- disabled ");
				continue;
			}
			if (!index.containsKey(dependencyName)) {
				resolve(obj.getClass(), dependency, resolvedObjects, index);
			} else {
				// circular dependency found
				Class<?> alreadyDefinedInClass = index.get(dependencyName);
				LOG.warning("encountered circular dependency " + dependencyName + " in " + obj.getClass()
						+ " but was already declared in " + alreadyDefinedInClass);
			}
		}
		resolvedObjects.add(obj);
		dependencyObserver.observe(obj, declaredIn);
		LOG.info("  +dependency: " + objName + " (" + projectClassType + ")");
	}

	private String getDependencyName(Object obj) {
		assert obj != null;
		Class<?> clazz = obj.getClass();
		ProjectClassType projectClassType = ProjectClassUtil.getProjectClassType(clazz);
		String dependencyName = clazz.getName();
		switch (projectClassType) {
		case COMPONENT:
			Component component = (Component) obj;
			if (component.isRepeatable()) {
				// instance name
				dependencyName = obj.toString();
			} else {
				// dependency name
				dependencyName = component.asDependency().toString();
			}
			break;
		case CUSTOM_COMPONENT:
			CustomComponent customComponent = (CustomComponent) obj;
			if (customComponent.isRepeatable()) {
				// instance name
				dependencyName = obj.toString();
			} else {
				// dependency name
				dependencyName = customComponent.asDependency().toString();
			}
			break;
		case FILE:
			File file = (File) obj;
			if (file.isRepeatable()) {
				// instance name
				dependencyName = obj.toString();
			} else {
				// dependency name
				dependencyName = file.asDependency().toString();
			}
			break;
		case CUSTOM_FILE:
			CustomFile customFile = (CustomFile) obj;
			if (customFile.isRepeatable()) {
				// instance name
				dependencyName = obj.toString();
			} else {
				// dependency name
				dependencyName = customFile.asDependency().toString();
			}
			break;
		case CONFIG:
			break;
		case NODE:
			break;
		case PLATFORM:
			break;
		case NODE_IDENTIFIER:
			break;
		case CONFIGURATION_MAPPER:
			break;
		case ROLE:
			break;
		case TAG:
			break;
		case TEMPLATE:
			Template template = (Template) obj;
			dependencyName = template.toString();
			break;
		case INSPECTOR:
			Inspector<?> inspector = (Inspector<?>) obj;
			dependencyName = inspector.toString();
			break;
		case UNDEFINED:
			break;
		default:
			break;
		}
		return dependencyName;
	}
}
