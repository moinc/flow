package nl.agiletech.flow.project.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Attributes {
	static final Filter<Class<? extends AttributeSource>> DEFAULT_FILTER = new Filter<Class<? extends AttributeSource>>() {
		@Override
		public boolean include(Class<? extends AttributeSource> attributeSourceType) {
			return true;
		}
	};
	private final Map<String, Attribute> attributes = new LinkedHashMap<>();

	public void add(Attribute attribute) {
		attributes.put(attribute.key, attribute);
	}

	public boolean contains(String key) {
		return attributes.containsKey(key);
	}

	public void remove(String key) {
		attributes.remove(key);
	}

	public void clear() {
		attributes.clear();
	}

	public List<Attribute> getAttributes() {
		return getAttributes(DEFAULT_FILTER);
	}

	public List<Attribute> getAttributes(Filter<Class<? extends AttributeSource>> filter) {
		List<Attribute> result = new ArrayList<>();
		for (Attribute attribute : attributes.values()) {
			if (filter.include(attribute.source)) {
				result.add(attribute);
			}
		}
		return result;
	}
}
