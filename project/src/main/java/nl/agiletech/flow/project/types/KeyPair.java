/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

public class KeyPair extends Pair {
	String id;

	public KeyPair() {
		// do not use
	}

	public KeyPair(String id, String key, String value) {
		super(key, value);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "[" + id + " : " + super.toString() + "]";
	}
}
