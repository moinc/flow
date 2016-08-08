package nl.agiletech.flow.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class LogUtil {
	/**
	 * Loads a resource with the name "logging.properties" in context with the
	 * specified class into the LogManager.
	 * 
	 * @param clazz
	 * @throws SecurityException
	 * @throws IOException
	 */
	public static void configure(Class<?> clazz) throws SecurityException, IOException {
		configure(clazz, "logging.properties");
	}

	public static void configure(Class<?> clazz, String resource) throws SecurityException, IOException {
		try (InputStream inputStream = clazz.getResourceAsStream(resource)) {
			LogManager logManager = LogManager.getLogManager();
			logManager.readConfiguration(inputStream);
		}
	}
}
