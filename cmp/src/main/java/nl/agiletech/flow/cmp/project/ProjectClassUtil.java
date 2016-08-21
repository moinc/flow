/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.util.logging.Logger;

import nl.agiletech.flow.common.reflect.ClassUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.annotation.FlowConfig;
import nl.agiletech.flow.project.annotation.FlowIgnore;
import nl.agiletech.flow.project.annotation.FlowTag;
import nl.agiletech.flow.project.types.Aspect;
import nl.agiletech.flow.project.types.Component;
import nl.agiletech.flow.project.types.ConfigurationMapper;
import nl.agiletech.flow.project.types.CustomComponent;
import nl.agiletech.flow.project.types.CustomFile;
import nl.agiletech.flow.project.types.File;
import nl.agiletech.flow.project.types.Inspector;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.NodeIdentifier;
import nl.agiletech.flow.project.types.Platform;
import nl.agiletech.flow.project.types.Requirement;
import nl.agiletech.flow.project.types.Role;
import nl.agiletech.flow.project.types.Template;

public class ProjectClassUtil {
	private static final Logger LOG = Logger.getLogger(ProjectClassUtil.class.getName());

	public static ProjectClassType getProjectClassType(Class<?> clazz) {
		Assertions.notNull(clazz, "clazz");
		LOG.fine("classify class: " + clazz.getName());
		if (!ClassUtil.isAnnotationPresent(clazz, FlowIgnore.class)) {
			if (ClassUtil.isAnnotationPresent(clazz, Flow.class)) {
				if (ClassUtil.isAnnotationPresent(clazz, FlowConfig.class)) {
					return ProjectClassType.CONFIG;
				}
				if (ClassUtil.isAnnotationPresent(clazz, FlowTag.class)) {
					return ProjectClassType.TAG;
				}
				if (ClassUtil.isSubclassOf(clazz, NodeIdentifier.class)) {
					return ProjectClassType.NODE_IDENTIFIER;
				}
				if (ClassUtil.isSubclassOf(clazz, ConfigurationMapper.class)) {
					return ProjectClassType.CONFIGURATION_MAPPER;
				}
				if (ClassUtil.isSubclassOf(clazz, Node.class)) {
					return ProjectClassType.NODE;
				}
				if (ClassUtil.isSubclassOf(clazz, Platform.class)) {
					return ProjectClassType.PLATFORM;
				}
				if (ClassUtil.isSubclassOf(clazz, Role.class)) {
					return ProjectClassType.ROLE;
				}
				if (ClassUtil.isSubclassOf(clazz, Component.class)) {
					return ProjectClassType.COMPONENT;
				}
				if (ClassUtil.isSubclassOf(clazz, CustomComponent.class)) {
					return ProjectClassType.CUSTOM_COMPONENT;
				}
				if (ClassUtil.isSubclassOf(clazz, File.class)) {
					return ProjectClassType.FILE;
				}
				if (ClassUtil.isSubclassOf(clazz, CustomFile.class)) {
					return ProjectClassType.CUSTOM_FILE;
				}
			}
			if (ClassUtil.isSubclassOf(clazz, Template.class)) {
				return ProjectClassType.TEMPLATE;
			}
			if (ClassUtil.isSubclassOf(clazz, Inspector.class)) {
				return ProjectClassType.INSPECTOR;
			}
			if (ClassUtil.isSubclassOf(clazz, Aspect.class)) {
				return ProjectClassType.ASPECT;
			}
			if (ClassUtil.isSubclassOf(clazz, Requirement.class)) {
				return ProjectClassType.REQUIREMENT;
			}
		}
		LOG.fine("failed to classify class: " + clazz.getName());
		return ProjectClassType.UNDEFINED;
	}
}
