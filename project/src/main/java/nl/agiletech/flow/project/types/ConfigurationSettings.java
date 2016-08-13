/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import nl.agiletech.flow.common.template.TemplateEngine;
import nl.agiletech.flow.common.template.builtin.SimpleTemplateEngine;

public class ConfigurationSettings extends ConfigurationFile {
	private static final Logger LOG = Logger.getLogger(ConfigurationSettings.class.getName());

	public static final class BuiltIn {
		public static final String FILEROOT = "resources.fileRoot";
		public static final String PROJECTROOT = "projects.projectRoot";
		public static final String TEMPLATE_ENGINE = "templates.engine";
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
		String value = (String) get(BuiltIn.FILEROOT, "");
		File file = new File(value);
		if (file.isFile()) {
			file = file.getParentFile();
		}
		return file;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends TemplateEngine> getTemplateEngineClass() {
		String value = (String) get(BuiltIn.TEMPLATE_ENGINE);
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
		String value = (String) get(BuiltIn.PROJECTROOT, "");
		if (value == null || value.isEmpty()) {
			value = ".";
		}
		File file = new File(value);
		if (file.isFile()) {
			file = file.getParentFile();
		}
		return file;
	}
}
