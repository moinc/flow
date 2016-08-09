/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.lang.annotation.Annotation;
import java.util.logging.Logger;

import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.annotation.FlowConfig;
import nl.agiletech.flow.project.annotation.FlowTag;
import nl.agiletech.flow.project.types.Component;
import nl.agiletech.flow.project.types.ConfigurationMapper;
import nl.agiletech.flow.project.types.CustomComponent;
import nl.agiletech.flow.project.types.CustomFile;
import nl.agiletech.flow.project.types.File;
import nl.agiletech.flow.project.types.Inspector;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.NodeIdentifier;
import nl.agiletech.flow.project.types.Platform;
import nl.agiletech.flow.project.types.Role;
import nl.agiletech.flow.project.types.Template;

public class ProjectClassUtil {
	private static final Logger LOG = Logger.getLogger(ProjectClassUtil.class.getName());

	public static ProjectClassType getProjectClassType(Class<?> clazz) {
		LOG.fine("classify class: " + clazz.getName());
		Annotation[] annotations = clazz.getAnnotations();
		if (hasAnnotation(annotations, Flow.class)) {
			if (hasAnnotation(annotations, FlowConfig.class)) {
				return ProjectClassType.CONFIG;
			}
			if (hasAnnotation(annotations, FlowTag.class)) {
				return ProjectClassType.TAG;
			}
			if (isSubclassOf(clazz, NodeIdentifier.class)) {
				return ProjectClassType.NODE_IDENTIFIER;
			}
			if (isSubclassOf(clazz, ConfigurationMapper.class)) {
				return ProjectClassType.CONFIGURATION_MAPPER;
			}
			if (isSubclassOf(clazz, Node.class)) {
				return ProjectClassType.NODE;
			}
			if (isSubclassOf(clazz, Platform.class)) {
				return ProjectClassType.PLATFORM;
			}
			if (isSubclassOf(clazz, Role.class)) {
				return ProjectClassType.ROLE;
			}
			if (isSubclassOf(clazz, Component.class)) {
				return ProjectClassType.COMPONENT;
			}
			if (isSubclassOf(clazz, CustomComponent.class)) {
				return ProjectClassType.CUSTOM_COMPONENT;
			}
			if (isSubclassOf(clazz, File.class)) {
				return ProjectClassType.FILE;
			}
			if (isSubclassOf(clazz, CustomFile.class)) {
				return ProjectClassType.CUSTOM_FILE;
			}
		}
		if (isSubclassOf(clazz, Template.class)) {
			return ProjectClassType.TEMPLATE;
		}
		if (isSubclassOf(clazz, Inspector.class)) {
			return ProjectClassType.INSPECTOR;
		}
		LOG.fine("failed to classify class: " + clazz.getName());
		return ProjectClassType.UNDEFINED;
	}

	private static boolean hasAnnotation(Annotation[] annotations, Class<?> annotationClass) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == annotationClass) {
				return true;
			}
		}
		return false;
	}

	private static boolean isSubclassOf(Class<?> clazz, Class<?> superClass) {
		return (clazz.equals(superClass) || superClass.isAssignableFrom(clazz));
	}
}
