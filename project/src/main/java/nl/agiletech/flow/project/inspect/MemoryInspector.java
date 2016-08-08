package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

//Linux: parse the contents of /proc/meminfo to retrieve the system memory information.
// Mac OSX: use the sysctl function to retrieve the system memory information.
//  swap (map) — Represents information about swap memory.
//	available (string) — The display size of the available amount of swap memory (e.g. “1 GiB”).
//	available_bytes (integer) — The size of the available amount of swap memory, in bytes.
//	capacity (string) — The capacity percentage (0% is empty, 100% is full).
//	encrypted (boolean) — True if the swap is encrypted or false if not.
//	total (string) — The display size of the total amount of swap memory (e.g. “1 GiB”).
//	total_bytes (integer) — The size of the total amount of swap memory, in bytes.
//	used (string) — The display size of the used amount of swap memory (e.g. “1 GiB”).
//	used_bytes (integer) — The size of the used amount of swap memory, in bytes.
//	system (map) — Represents information about system memory.
//	available (string) — The display size of the available amount of system memory (e.g. “1 GiB”).
//	available_bytes (integer) — The size of the available amount of system memory, in bytes.
//	capacity (string) — The capacity percentage (0% is empty, 100% is full).
//	total (string) — The display size of the total amount of system memory (e.g. “1 GiB”).
//	total_bytes (integer) — The size of the total amount of system memory, in bytes.
//	used (string) — The display size of the used amount of system memory (e.g. “1 GiB”).
//	used_bytes (integer) — The size of the used amount of system memory, in bytes.
public class MemoryInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}
