/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import nl.agiletech.flow.common.util.Assertions;

/**
 * Models a requirement which is basically a name and version.
 * 
 * @author moincreemers
 *
 */
public class Requirement {
	public static Requirement require(Task task) {
		Assertions.notNull(task, "task");
		return task.asRequirement();
	}

	final String name;
	final Version version;

	public static Requirement getForAnyVersion(String name) {
		return new Requirement(name, Version.ANY);
	}

	public static Requirement get(String name, String version) {
		return new Requirement(name, Version.get(version));
	}

	private Requirement(String name, Version version) {
		Assertions.notEmpty(name, "name");
		Assertions.notNull(version, "version");
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public Version getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return name + " " + version;
	}
}
