package nl.agiletech.flow.project.types;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.Map;

import org.junit.Test;

import nl.agiletech.flow.common.io.IOUtil;

public class ConfigurationSettingsTest {
	private static final String FLOWCONFIG_FILE = "flowconfig.json";

	@Test
	public void testConfigurationSettingsShouldLoadFromFile() throws Exception {
		try (InputStream inputStream = getClass().getResourceAsStream(FLOWCONFIG_FILE)) {
			java.io.File file = java.io.File.createTempFile("flowconfig", null);
			IOUtil.copy(inputStream, file);
			ConfigurationSettings cs = ConfigurationSettings.loadFrom(file);
			assertConfigurationKeys(cs, "resources", "templates", "projects");
			file.delete();
		}
	}

	@Test
	public void testConfigurationSettingsShouldLoadFromInputStream() throws Exception {
		try (InputStream inputStream = getClass().getResourceAsStream(FLOWCONFIG_FILE)) {
			ConfigurationSettings cs = ConfigurationSettings.loadFrom(inputStream);
			assertConfigurationKeys(cs, "resources", "templates", "projects");
		}
	}

	private void assertConfigurationKeys(ConfigurationSettings cs, String... keys) {
		for (String key : keys) {
			Object o = cs.get(key);
			assertNotNull("expected flowconfig file to contain root field: " + key, o);
			assertTrue("expected config value to be a map", o instanceof Map);
		}
	}
}
