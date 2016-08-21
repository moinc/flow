package nl.agiletech.flow.common.util;

import java.io.File;
import java.lang.reflect.Array;

public final class Assertions {
	private Assertions() {
		// prevent instantiation
	}

	public static void notNull(Object object, String argumentName) {
		if (object == null) {
			throw new IllegalArgumentException("unexpected null argument: " + argumentName);
		}
	}

	public static void notEmpty(String string, String argumentName) {
		if (string == null) {
			throw new IllegalArgumentException("unexpected null argument: " + argumentName);
		}
		if (string.isEmpty()) {
			throw new IllegalArgumentException("unexpected empty argument: " + argumentName);
		}
	}

	public static void notEmpty(Object array, String argumentName) {
		if (array == null) {
			throw new IllegalArgumentException("unexpected null argument: " + argumentName);
		}
		if (array.getClass().isArray() && Array.getLength(array) == 0) {
			throw new IllegalArgumentException("empty array argument: " + argumentName);
		}
	}

	public static void exists(File file, String argumentName) {
		if (file == null) {
			throw new IllegalArgumentException("unexpected null argument: " + argumentName);
		}
		if (!file.exists()) {
			throw new IllegalArgumentException("file not found argument: " + argumentName);
		}
	}

	public static void notNull(Object... objects) {
		if (objects == null) {
			return;
		}
		int i = 0;
		for (Object obj : objects) {
			notNull(obj, "" + i);
			i++;
		}
	}

	public static void notEmpty(String... strings) {
		if (strings == null) {
			return;
		}
		int i = 0;
		for (String string : strings) {
			notEmpty(string, "" + i);
			i++;
		}
	}
}
