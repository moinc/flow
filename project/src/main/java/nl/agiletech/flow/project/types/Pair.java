/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

public class Pair {
	String key;
	Object value;

	public Pair() {
		// do not use
	}

	public Pair(String key, Object value) {
		assert this.key != null && !this.key.isEmpty();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "[" + key + " = " + (value == null ? "<null>" : value.toString()) + "]";
	}
}
