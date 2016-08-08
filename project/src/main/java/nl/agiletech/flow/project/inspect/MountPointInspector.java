/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

//Linux: use the setmntent function to retrieve the mount points.
// Mac OSX: use the getfsstat function to retrieve the mount points.
// <mountpoint> (map) — Represents a mount point.
//	available (string) — The display size of the available space (e.g. “1 GiB”).
//	available_bytes (integer) — The size of the available space, in bytes.
//	capacity (string) — The capacity percentage (0% is empty, 100% is full).
//	device (string) — The name of the mounted device.
//	filesystem (string) — The file system of the mounted device.
//	options (array) — The mount options.
//	size (string) — The display size of the total space (e.g. “1 GiB”).
//	size_bytes (integer) — The size of the total space, in bytes.
//	used (string) — The display size of the used space (e.g. “1 GiB”).
//	used_bytes (integer) — The size of the used space, in bytes.
public class MountPointInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}
