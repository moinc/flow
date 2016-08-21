/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import nl.agiletech.flow.common.util.Assertions;

public class Pair {
	String key;
	Object value;

	public Pair() {
		// do not use
	}

	public Pair(String key, Object value) {
		Assertions.notEmpty(key, "key");
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
