/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

// Linux: parse the contents of /sys/class/dmi/id/board_asset_tag to retrieve the system board asset tag.
// Linux: parse the contents of /sys/class/dmi/id/board_vendor to retrieve the system board manufacturer.
// Linux: parse the contents of /sys/class/dmi/id/board_name to retrieve the system board product name.
// Linux: parse the contents of /sys/class/dmi/id/board_serial to retrieve the system board serial number.
// Linux: parse the contents of /sys/class/dmi/id/chassis_asset_tag to retrieve the system chassis asset tag.
// Linux: parse the contents of /sys/class/dmi/id/chassis_type to retrieve the system chassis type.
public class AssetInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}

}
