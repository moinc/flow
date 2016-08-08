package nl.agiletech.flow.common.cli;

import java.util.HashMap;
import java.util.Map;

public class AppState {
	private final Map<AppStateProperty, Object> properties = new HashMap<>();

	public void setProperty(AppStateProperty key, Object value) {
		this.properties.put(key, value);
	}

	public Object getProperty(AppStateProperty key) {
		return this.properties.get(key);
	}

	public String getPropertyAsString(AppStateProperty key) {
		return (String) this.properties.get(key);
	}

	public boolean getPropertyAsBool(AppStateProperty key) {
		Object o = this.properties.get(key);
		if (o instanceof Boolean) {
			return (Boolean) o;
		}
		return false;
	}

}
