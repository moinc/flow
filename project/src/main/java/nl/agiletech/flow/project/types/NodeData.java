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

import nl.agiletech.flow.common.template.TemplateRenderException;

public class NodeData implements TakesContext {
	private static final Logger LOG = Logger.getLogger(NodeData.class.getName());

	transient Context context;
	List<KeyPair> entries = new ArrayList<>();

	public static NodeData createInstance() {
		return new NodeData();
	}

	public static NodeData loadFrom(File file) throws FileNotFoundException, IOException {
		assert file != null;
		LOG.fine("load nodedata from file: " + file.getAbsolutePath());
		try (InputStream src = new FileInputStream(file)) {
			return loadFrom(src);
		}
	}

	public static NodeData loadFrom(InputStream src) throws JsonParseException, JsonMappingException, IOException {
		assert src != null;
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(src, NodeData.class);
	}

	public NodeData() {
		// do not use
	}

	@Override
	public void setContext(Context context) {
		assert context != null;
		this.context = context;
	}

	public List<KeyPair> getEntries() {
		return entries;
	}

	public List<Object> getEntries(Filter<KeyPair> filter) {
		return getEntries(filter, new KeyPairTypeConverter());
	}

	public <O> List<O> getEntries(Filter<KeyPair> filter, TypeConverter<KeyPair, O> converter) {
		assert filter != null;
		List<O> result = new ArrayList<>();
		for (KeyPair keyPair : entries) {
			if (keyPair != null && filter.include(keyPair)) {
				result.add(converter.convert(keyPair));
			}
		}
		return result;
	}

	public Object get(final String key, Object valueIfNull) throws IOException, TemplateRenderException {
		List<Object> result = getEntries(new Filter<KeyPair>() {
			@Override
			public boolean include(KeyPair value) {
				return value.value != null && value.key.equals(key);
			}
		});
		return result.size() != 0 ? result.get(0) : valueIfNull;
	}
}
