/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

//Linux: parse the contents of /sys/class/dmi/id/bios_date to retrieve the system BIOS release date.
//Linux: parse the contents of /sys/class/dmi/id/bios_vendor to retrieve the system BIOS vendor.
//Linux: parse the contents of /sys/class/dmi/id/bios_version to retrieve the system BIOS version.
public class BiosInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}

}
