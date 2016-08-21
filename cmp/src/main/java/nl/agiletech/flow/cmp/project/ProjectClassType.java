/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

public enum ProjectClassType {
	UNDEFINED(false),

	/**
	 * Project class annotated with {@link FlowConfig}
	 */
	CONFIG(false),

	TAG(false),

	ROLE(false),

	NODE_IDENTIFIER(false),

	CONFIGURATION_MAPPER(false),

	NODE(true),

	PLATFORM(false),

	COMPONENT(true),

	CUSTOM_COMPONENT(true),

	FILE(true),

	CUSTOM_FILE(true),

	TEMPLATE(true),

	INSPECTOR(true),

	ASPECT(false),

	REQUIREMENT(false);

	private final boolean taskSubclass;

	ProjectClassType(boolean taskSubclass) {
		this.taskSubclass = taskSubclass;
	}

	public boolean isTaskSubclass() {
		return taskSubclass;
	}
}
