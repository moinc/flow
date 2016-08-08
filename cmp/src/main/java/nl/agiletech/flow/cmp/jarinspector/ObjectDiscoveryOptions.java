/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.jarinspector;

import java.util.ArrayList;
import java.util.List;

public class ObjectDiscoveryOptions {
	private final List<String> excludedPackages = new ArrayList<>();
	private boolean expandCollections = false;

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

}
