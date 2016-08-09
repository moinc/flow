/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

public enum ProjectClassType {
	UNDEFINED,

	/**
	 * Project class annotated with {@link FlowConfig}
	 */
	CONFIG,

	TAG, ROLE, NODE_IDENTIFIER, CONFIGURATION_MAPPER, NODE, PLATFORM, COMPONENT, CUSTOM_COMPONENT, FILE, CUSTOM_FILE, TEMPLATE, INSPECTOR
}
