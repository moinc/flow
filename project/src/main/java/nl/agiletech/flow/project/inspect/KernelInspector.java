/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

// POSIX platforms: use the uname function to retrieve the kernel name.
// POSIX platforms: use the uname function to retrieve the kernel’s major version.
// POSIX platforms: use the uname function to retrieve the kernel’s release.
// POSIX platforms: use the uname function to retrieve the kernel’s version.
public class KernelInspector  extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}

