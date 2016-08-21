/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.agiletech.flow.common.util.Assertions;

public class ConfigurationFile implements TakesConfiguration, TakesContext {
	private static final Logger LOG = Logger.getLogger(ConfigurationFile.class.getName());
	static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

	@JsonIgnoreProperties(ignoreUnknown = true)
	static class IntermediateRepresentation extends HashMap<String, Object> {
		private static final long serialVersionUID = 7138821218806778567L;

		public IntermediateRepresentation() {
			super();
		}
	}

	transient Context context;
	final Map<String, Object> values = new HashMap<>();

	public static ConfigurationFile createInstance() {
		return new ConfigurationFile();
	}

	protected static <T extends TakesConfiguration> T _loadFrom(File file, T instance)
			throws FileNotFoundException, IOException {
		LOG.info("loading configuration file: " + file.getAbsolutePath());
		try (InputStream src = new FileInputStream(file)) {
			return _loadFrom(src, instance);
		}
	}

	protected static <T extends TakesConfiguration> T _loadFrom(InputStream src, T instance)
			throws JsonParseException, JsonMappingException, IOException {
		instance.load(OBJECTMAPPER.readValue(src, IntermediateRepresentation.class));
		return instance;
	}

	protected ConfigurationFile() {
	}

	@Override
	public void load(Map<String, Object> values) {
		this.values.clear();
		this.values.putAll(values);
	}

	@Override
	public void setContext(Context context) {
		Assertions.notNull(context, "context");
		this.context = context;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public List<Object> getValues(Filter<Entry<String, Object>> filter) {
		return getValues(filter, new MapEntryTypeConverter());
	}

	public <O> List<O> getValues(Filter<Entry<String, Object>> filter,
			TypeConverter<Entry<String, Object>, O> converter) {
		Assertions.notNull(filter, "filter");
		List<O> result = new ArrayList<>();
		for (Entry<String, Object> entry : values.entrySet()) {
			if (entry != null && filter.include(entry)) {
				result.add(converter.convert(entry));
			}
		}
		return result;
	}

	public Object get(String key) {
		return get(key, null);
	}

	public Object get(String key, Object nullValue) {
		Assertions.notEmpty(key, "key");
		Object value = dereference(values, key);
		return value == null ? nullValue : value;
	}

	@SuppressWarnings("rawtypes")
	private Object dereference(Map map, String key) {
		Assertions.notNull(map, "map");
		Assertions.notEmpty(key, "key");
		Queue<String> keys = new ConcurrentLinkedQueue<>();
		for (String value : key.split("\\.")) {
			keys.add(value);
		}
		return dereference(map, keys);
	}

	@SuppressWarnings("rawtypes")
	private Object dereference(Map map, Queue<String> keys) {
		Assertions.notNull(map, "map");
		Assertions.notNull(keys, "keys");
		String key = keys.poll();
		Object value = map.get(key);
		if (!keys.isEmpty() && value instanceof Map) {
			Map map2 = (Map) value;
			value = dereference(map2, keys);
		}
		return value;
	}

	protected void printSettings() {
		printSettings("configuration settings", values, 1);
	}

	@SuppressWarnings("rawtypes")
	private void printSettings(Object key, Object value, int indent) {
		final String nullValue = "<null>";
		final String indentValue = "  ";
		String k = key != null ? key.toString() : "<null>";
		String s = "";
		for (int j = 0; j < indent; j++) {
			s += indentValue;
		}
		if (value instanceof Map) {
			LOG.info(s + k + ": ");
			Map map = (Map) value;
			for (Object key2 : map.keySet()) {
				Object value2 = map.get(key2);
				printSettings(key2, value2, indent + 1);
			}
		} else if (value instanceof Collection) {
			LOG.info(s + k + ": ");
			Collection col = (Collection) value;
			int i = 0;
			for (Object value2 : col) {
				String key2 = "item_" + i++;
				printSettings(key2, value2, indent + 1);
			}
		} else {
			String v = value != null ? value.toString() : nullValue;
			LOG.info(s + k + ": " + v);
		}
	}
}
