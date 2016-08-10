/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.jarinspector;

import java.util.ArrayList;
import java.util.List;

import nl.agiletech.flow.project.types.Filter;

public class ObjectDiscoveryOptions {
	static Filter<Class<?>> DEFAULT_TYPEFILTER = new Filter<Class<?>>() {
		@Override
		public boolean include(Class<?> value) {
			return true;
		}
	};

	private final List<String> excludedPackages = new ArrayList<>();
	private boolean expandCollections = false;
	private Filter<Class<?>> typeFilter = DEFAULT_TYPEFILTER;

	public static ObjectDiscoveryOptions createInstanceForDependencyDiscovery() {
		ObjectDiscoveryOptions c = new ObjectDiscoveryOptions();
		c.excludedPackages.add("java");
		c.expandCollections = true;
		return c;
	}

	public static ObjectDiscoveryOptions createInstanceForConfigurationDiscovery() {
		return new ObjectDiscoveryOptions();
	}

	public List<String> getExcludedPackages() {
		return excludedPackages;
	}

	public boolean isExpandCollections() {
		return expandCollections;
	}

	public void setExpandCollections(boolean expandCollections) {
		this.expandCollections = expandCollections;
	}

	public Filter<Class<?>> getTypeFilter() {
		return typeFilter;
	}

	public ObjectDiscoveryOptions withTypeFilter(Filter<Class<?>> typeFilter) {
		this.typeFilter = typeFilter != null ? typeFilter : DEFAULT_TYPEFILTER;
		return this;
	}
}
