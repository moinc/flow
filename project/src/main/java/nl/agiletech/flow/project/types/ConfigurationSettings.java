/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.agiletech.flow.common.template.TemplateEngine;
import nl.agiletech.flow.common.template.builtin.SimpleTemplateEngine;

public class ConfigurationSettings implements TakesContext {
	private static final Logger LOG = Logger.getLogger(ConfigurationSettings.class.getName());

	public static final class BuiltIn {
		public static final String FILEROOT = "fileRoot";
		public static final String TEMPLATE_ENGINE = "templateEngine";
	}

	transient Context context;
	List<Pair> settings = new ArrayList<>();
	List<Pair> supportedPlatforms = new ArrayList<>();

	public static ConfigurationSettings createInstance() {
		return new ConfigurationSettings();
	}

	public static ConfigurationSettings loadFrom(File file) throws FileNotFoundException, IOException {
		LOG.info("loading configuration settings from file: " + file.getAbsolutePath());
		try (InputStream src = new FileInputStream(file)) {
			return loadFrom(src);
		}
	}

	public static ConfigurationSettings loadFrom(InputStream src)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ConfigurationSettings cs = objectMapper.readValue(src, ConfigurationSettings.class);
		LOG.info("configuration settings: ");
		for (Pair pair : cs.getSettings()) {
			LOG.info("  " + pair);
		}
		LOG.info("supported platforms: ");
		for (Pair pair : cs.getSupportedPlatforms()) {
			LOG.info("  " + pair);
		}
		return cs;
	}

	public ConfigurationSettings() {
		// do not use
	}

	@Override
	public void setContext(Context context) {
		assert context != null;
		this.context = context;
	}

	public List<Pair> getSupportedPlatforms() {
		return supportedPlatforms;
	}

	public String getSupportedPlatform(String key, String valueIfNull) {
		assert key != null;
		for (Pair pair : supportedPlatforms) {
			if (key.equals(pair.getKey())) {
				return (String) pair.getValue();
			}
		}
		return valueIfNull;
	}

	public List<Pair> getSettings() {
		return settings;
	}

	public List<Object> getSettings(Filter<Pair> filter) {
		return getSettings(filter, new PairTypeConverter());
	}

	public <O> List<O> getSettings(Filter<Pair> filter, TypeConverter<Pair, O> converter) {
		assert filter != null;
		List<O> result = new ArrayList<>();
		for (Pair pair : settings) {
			if (pair != null && filter.include(pair)) {
				result.add(converter.convert(pair));
			}
		}
		return result;
	}

	public Object get(String key) {
		return get(key, null);
	}

	public Object get(String key, Object valueIfNull) {
		List<Object> result = getSettings(new Filter<Pair>() {
			@Override
			public boolean include(Pair value) {
				return value.value != null && value.key.equals(key);
			}
		});
		return result.size() != 0 ? result.get(0) : valueIfNull;
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
}
