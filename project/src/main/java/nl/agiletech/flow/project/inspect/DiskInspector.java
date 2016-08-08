package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

//Linux: parse the contents of /sys/block/<device>/.
//Linux: parse the contents of /sys/block/<device>/device/model to retrieve the model name/number for a device.
//Linux: parse the contents of /sys/block/<device>/size to receive the size (multiplying by 512 to correct for blocks-to-bytes).
//Linux: parse the contents of /sys/block/<device>/device/vendor to retrieve the vendor for a device.
// Mac OSX: parse the contents of diskutil list
public class DiskInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}
