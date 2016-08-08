/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

//Linux: use the sysinfo function to retrieve the system uptime.
// POSIX platforms: use the uptime utility to retrieve the system uptime.
//	days (integer) — The number of complete days the system has been up.
//	hours (integer) — The number of complete hours the system has been up.
//	seconds (integer) — The number of total seconds the system has been up.
//	uptime (string) — The full uptime string.
public class SystemUptimeInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}
