/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

// Linux: parse the contents /sys/devices/system/cpu/ and /proc/cpuinfo to retrieve the processor information.
//	Mac OSX: use the sysctl function to retrieve the processor information.
//	count (integer) — The count of logical processors.
//	isa (string) — The processor instruction set architecture.
//	models (array) — The processor model strings (one for each logical processor).
//	physicalcount (integer) — The count of physical processors.
//	speed (string) — The speed of the processors (e.g. “2.0 GHz”).
public class ProcessorInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}
