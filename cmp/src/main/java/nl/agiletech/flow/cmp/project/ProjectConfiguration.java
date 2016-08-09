/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import nl.agiletech.flow.cmp.jarinspector.InspectionObserver;
import nl.agiletech.flow.project.types.ConfigurationMapper;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.NodeIdentifier;
import nl.agiletech.flow.project.types.Role;
import nl.agiletech.flow.project.types.Task;

public class ProjectConfiguration implements InspectionObserver, DependencyObserver {
	private static final Logger LOG = Logger.getLogger(ProjectConfiguration.class.getName());

	final List<Class<?>> configurationClasses = new ArrayList<>();
	final List<Class<? extends NodeIdentifier>> nodeIdentifierClasses = new ArrayList<>();
	final List<Class<? extends ConfigurationMapper>> configurationMapperClasses = new ArrayList<>();
	final List<Class<? extends Role>> roleClasses = new ArrayList<>();
	final List<Class<? extends Node>> nodeClasses = new ArrayList<>();
	final List<Class<? extends Task>> taskClasses = new ArrayList<>();
	final List<Class<?>> tagClasses = new ArrayList<>();

	// indices
	final Map<Object, Set<Class<?>>> dependencyIndex = new HashMap<>();
	final Map<Class<?>, Set<Object>> invertedDependencyIndex = new HashMap<>();

	public void addConfigurationClass(Class<?> clazz) {
		LOG.fine("adding configuration class: " + clazz.getName());
		configurationClasses.add(clazz);
	}

	public void addNodeIdentifierClass(Class<? extends NodeIdentifier> clazz) {
		LOG.fine("adding custom node identifier class: " + clazz.getName());
		nodeIdentifierClasses.add(clazz);
	}

	public void addConfigurationMapperClass(Class<? extends ConfigurationMapper> clazz) {
		LOG.fine("adding custom configuration mapper class: " + clazz.getName());
		configurationMapperClasses.add(clazz);
	}

	public void addRoleClass(Class<? extends Role> clazz) {
		LOG.fine("adding role class: " + clazz.getName());
		roleClasses.add(clazz);
	}

	public void addNodeClass(Class<? extends Node> clazz) {
		LOG.fine("adding node class: " + clazz.getName());
		nodeClasses.add(clazz);
	}

	public void addTaskClass(Class<? extends Task> clazz) {
		LOG.fine("adding task class: " + clazz.getName());
		taskClasses.add(clazz);
	}

	public void addTagClass(Class<?> clazz) {
		LOG.fine("adding tag class: " + clazz.getName());
		tagClasses.add(clazz);
	}

	public void addDependencyIndexEntry(Object obj, Class<?> declaredIn) {
		LOG.fine("adding dependency to index; object: " + obj + " was declared in: " + declaredIn.getName());

		Set<Class<?>> list = dependencyIndex.get(obj);
		if (list == null) {
			list = new LinkedHashSet<>();
			dependencyIndex.put(obj, list);
		}
		list.add(declaredIn);

		Set<Object> list2 = invertedDependencyIndex.get(declaredIn);
		if (list2 == null) {
			list2 = new LinkedHashSet<>();
			invertedDependencyIndex.put(declaredIn, list2);
		}
		list2.add(obj);
	}

	//

	public List<Class<?>> getConfigurationClasses() {
		return configurationClasses;
	}

	public List<Class<? extends NodeIdentifier>> getNodeIdentifierClasses() {
		return nodeIdentifierClasses;
	}

	public List<Class<? extends ConfigurationMapper>> getConfigurationMapperClasses() {
		return configurationMapperClasses;
	}

	public List<Class<? extends Role>> getRoleClasses() {
		return roleClasses;
	}

	public List<Class<? extends Node>> getNodeClasses() {
		return nodeClasses;
	}

	public List<Class<? extends Task>> getTaskClasses() {
		return taskClasses;
	}

	public List<Class<?>> getTagClasses() {
		return tagClasses;
	}

	public Map<Object, Set<Class<?>>> getDependencyIndex() {
		return dependencyIndex;
	}

	public Map<Class<?>, Set<Object>> getInvertedDependencyIndex() {
		return invertedDependencyIndex;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void observe(Class<?> clazz) {
		ProjectClassType projectClassType = ProjectClassUtil.getProjectClassType(clazz);
		if (projectClassType != ProjectClassType.UNDEFINED) {
			LOG.fine(clazz.getName());
		}
		switch (projectClassType) {
		case COMPONENT:
		case CUSTOM_COMPONENT:
		case FILE:
		case CUSTOM_FILE:
			addTaskClass((Class<? extends Task>) clazz);
			break;
		case CONFIG:
			addConfigurationClass(clazz);
			break;
		case NODE:
			addNodeClass((Class<? extends Node>) clazz);
			break;
		case PLATFORM:
			break;
		case NODE_IDENTIFIER:
			addNodeIdentifierClass((Class<? extends NodeIdentifier>) clazz);
			break;
		case CONFIGURATION_MAPPER:
			addConfigurationMapperClass((Class<? extends ConfigurationMapper>) clazz);
			break;
		case ROLE:
			addRoleClass((Class<? extends Role>) clazz);
			break;
		case TAG:
			addTagClass(clazz);
			break;
		case TEMPLATE:
			break;
		case INSPECTOR:
			break;
		case UNDEFINED:
			break;

		default:
			break;
		}
	}

	@Override
	public void observe(Object obj, Class<?> declaredIn) {
		addDependencyIndexEntry(obj, declaredIn);
	}
}
