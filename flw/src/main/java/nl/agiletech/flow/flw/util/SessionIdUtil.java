package nl.agiletech.flow.flw.util;

import java.util.UUID;

public final class SessionIdUtil {

	public static String createSessionId() {
		return UUID.randomUUID().toString();
	}

	private SessionIdUtil() {
		// prevent instantiation
	}
}
