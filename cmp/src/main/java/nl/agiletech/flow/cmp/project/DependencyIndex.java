package nl.agiletech.flow.cmp.project;

import java.util.LinkedHashMap;
import java.util.Map;

public class DependencyIndex {

	private class DependencyIndexEntry {
		Class<?> declaredIn;
		ProjectClassType projectClassType;
	}

	Map<String, DependencyIndexEntry> index = new LinkedHashMap<>();

	public void put(Class<?> declaredIn, String key, ProjectClassType projectClassType) {
		DependencyIndexEntry entry = new DependencyIndexEntry();
		entry.declaredIn = declaredIn;
		entry.projectClassType = projectClassType;
		index.put(key, entry);
	}

	public boolean contains(String key) {
		return index.containsKey(key);
	}

	public boolean contains(String key, ProjectClassType projectClassType) {
		DependencyIndexEntry entry = index.get(key);
		return (entry != null && entry.projectClassType == projectClassType);
	}
}
