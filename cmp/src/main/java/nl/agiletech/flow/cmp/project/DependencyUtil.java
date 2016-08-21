package nl.agiletech.flow.cmp.project;

import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.Component;
import nl.agiletech.flow.project.types.CustomComponent;
import nl.agiletech.flow.project.types.CustomFile;
import nl.agiletech.flow.project.types.File;
import nl.agiletech.flow.project.types.Inspector;
import nl.agiletech.flow.project.types.Template;

public final class DependencyUtil {
	private DependencyUtil() {
		// prevent instantiation
	}

	public static String getDependencyName(Object obj) {
		Assertions.notNull(obj, "obj");
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
				dependencyName = component.asRequirement().toString();
			}
			break;
		case CUSTOM_COMPONENT:
			CustomComponent customComponent = (CustomComponent) obj;
			if (customComponent.isRepeatable()) {
				// instance name
				dependencyName = obj.toString();
			} else {
				// dependency name
				dependencyName = customComponent.asRequirement().toString();
			}
			break;
		case FILE:
			File file = (File) obj;
			if (file.isRepeatable()) {
				// instance name
				dependencyName = obj.toString();
			} else {
				// dependency name
				dependencyName = file.asRequirement().toString();
			}
			break;
		case CUSTOM_FILE:
			CustomFile customFile = (CustomFile) obj;
			if (customFile.isRepeatable()) {
				// instance name
				dependencyName = obj.toString();
			} else {
				// dependency name
				dependencyName = customFile.asRequirement().toString();
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
		case ASPECT:
			break;
		case REQUIREMENT:
			dependencyName = obj.toString();
			break;
		default:
			break;
		}
		return dependencyName;
	}
}
