package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

//Linux: use procfs or utilities such as vmware and virt-what to retrieve virtual machine status.
// Mac OSX: use the system profiler to retrieve virtual machine status.
//Linux: use procfs or utilities such as vmware and virt-what to retrieve virtual machine name.
// Mac OSX: use the system profiler to retrieve virtual machine name.
public class VirtualMachineInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}
