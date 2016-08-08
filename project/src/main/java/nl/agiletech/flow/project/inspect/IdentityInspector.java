/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

//POSIX platforms: use the getegid, getpwuid_r, geteuid, and getgrgid_r functions to retrieve the identity information.
//	gid (integer) — The group identifier of the user running facter.
//	group (string) — The group name of the user running facter.
//	uid (integer) — The user identifier of the user running facter.
//	user (string) — The user name of the user running facter.
public class IdentityInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}
