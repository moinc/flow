/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Logger;

import nl.agiletech.flow.common.template.TemplateEngine;
import nl.agiletech.flow.common.template.builtin.SimpleTemplateEngine;

public class ConfigurationSettings extends ConfigurationFile {
	private static final Logger LOG = Logger.getLogger(ConfigurationSettings.class.getName());

	public static final class BuiltIn {
		public static final String RESOURCES_FILEROOT = "resources.fileRoot";
		public static final String PROJECTS_PROJECTROOT = "projects.projectRoot";
		public static final String TEMPLATES_ENGINE = "templates.engine";
		public static final String SERVICE_PORT = "service.port";
		public static final String BOT_MAXCATALOGS = "bot.maxCatalogs";
		public static final String BOT_SERVICEURI = "bot.serviceURI";
	}

	public static final ConfigurationSettings EMPTY = new ConfigurationSettings();

	public static ConfigurationSettings loadFrom(File file) throws FileNotFoundException, IOException {
		return _loadFrom(file, new ConfigurationSettings());
	}

	public static ConfigurationSettings loadFrom(InputStream src) throws IOException {
		return _loadFrom(src, new ConfigurationSettings());
	}

	private ConfigurationSettings() {
	}

	public File getFileRoot() {
		String value = (String) get(BuiltIn.RESOURCES_FILEROOT, "");
		File file = new File(value);
		if (file.isFile()) {
			file = file.getParentFile();
		}
		return file;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends TemplateEngine> getTemplateEngineClass() {
		String value = (String) get(BuiltIn.TEMPLATES_ENGINE);
		if (value != null) {
			try {
				return (Class<? extends TemplateEngine>) Class.forName(value.toString(), true,
						getClass().getClassLoader());
			} catch (ClassNotFoundException e) {
				LOG.severe("failed to initialize class: " + value);
			}
		}
		return SimpleTemplateEngine.class;
	}

	public File getProjectRoot() {
		String value = (String) get(BuiltIn.PROJECTS_PROJECTROOT, "");
		if (value == null || value.isEmpty()) {
			value = ".";
		}
		File file = new File(value);
		if (file.isFile()) {
			file = file.getParentFile();
		}
		return file;
	}

	public int getServicePort() {
		return (int) get(BuiltIn.SERVICE_PORT, 0);
	}

	public int getMaxCatalogs() {
		int maxCatalogs = (int) get(BuiltIn.BOT_MAXCATALOGS, 1);
		return Math.max(1, maxCatalogs);
	}

	public URI getServiceURI() throws ConfigurationException {
		String value = (String) get(BuiltIn.BOT_SERVICEURI, "");
		if (value == null || value.isEmpty()) {
			throw new ConfigurationException("Missing configuration setting: " + BuiltIn.BOT_SERVICEURI);
		}
		return URI.create(value);
	}
}
