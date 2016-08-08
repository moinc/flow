package nl.agiletech.flow.project.types;

public class Dependency {
	final String name;
	final Version version;

	public static Dependency getForAnyVersion(String name) {
		return new Dependency(name, Version.ANY);
	}

	public static Dependency get(String name, String version) {
		return new Dependency(name, Version.get(version));
	}

	private Dependency(String name, Version version) {
		assert name != null && name.length() != 0;
		assert version != null;
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
