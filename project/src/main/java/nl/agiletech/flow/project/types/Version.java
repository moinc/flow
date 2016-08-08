/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

public class Version {
	final String match;

	public static Version get(String match) {
		return new Version(match);
	}

	public static final Version ANY = new Version(".*");

	private Version(String match) {
		this.match = match;
	}

	public String getMatch() {
		return match;
	}

	@Override
	public String toString() {
		return "[Version: " + match + "]";
	}
}
