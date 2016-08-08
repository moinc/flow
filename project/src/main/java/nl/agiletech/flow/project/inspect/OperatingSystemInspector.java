package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

//Linux: use the lsb_release utility and parse the contents of release files in /etc to retrieve the OS information.
//	OSX: use the sw_vers utility to retrieve the OS information.
//	architecture (string) — The operating system’s hardware architecture.
//	distro (map) — Represents information about a Linux distribution.
//	codename (string) — The code name of the Linux distribution.
//	description (string) — The description of the Linux distribution.
//	id (string) — The identifier of the Linux distribution.
//	release (map) — Represents information about a Linux distribution release.
//	full (string) — The full release of the Linux distribution.
//	major (string) — The major release of the Linux distribution.
//	minor (string) — The minor release of the Linux distribution.
//	specification (string) — The Linux Standard Base (LSB) release specification.
//	family (string) — The operating system family.
//	hardware (string) — The operating system’s hardware model.
//	macosx (map) — Represents information about Mac OSX.
//	build (string) — The Mac OSX build version.
//	product (string) — The Mac OSX product name.
//	version (map) — Represents information about the Mac OSX version.
//	full (string) — The full Mac OSX version number.
//	major (string) — The major Mac OSX version number.
//	minor (string) — The minor Mac OSX version number.
//	name (string) — The operating system’s name.
//	release (map) — Represents the operating system’s release.
//	full (string) — The full operating system release.
//	major (string) — The major release of the operating system.
//	minor (string) — The minor release of the operating system.
//	selinux (map) — Represents information about Security-Enhanced Linux (SELinux).
//	config_mode (string) — The configured SELinux mode.
//	config_policy (string) — The configured SELinux policy.
//	current_mode (string) — The current SELinux mode.
//	enabled (boolean) — True if SELinux is enabled or false if not.
//	enforced (boolean) — True if SELinux policy is enforced or false if not.
//	policy_version (string) — The version of the SELinux policy.
//	windows (map) — Represents information about Windows.
//	system32 (string) — The path to the System32 directory.
public class OperatingSystemInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}
