package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

//Linux: use the getifaddrs function to retrieve the network interfaces.
// Mac OSX: use the getifaddrs function to retrieve the network interfaces.
//	dhcp (ip) — The address of the DHCP server for the default interface.
//	domain (string) — The domain name of the system.
//	fqdn (string) — The fully-qualified domain name of the system.
//	hostname (string) — The host name of the system.
//	interfaces (map) — The network interfaces of the system.
//	<interface> (map) — Represents a network interface.
//	bindings (array) — The array of IPv4 address bindings for the interface.
//	bindings6 (array) — The array of IPv6 address bindings for the interface.
//	dhcp (ip) — The DHCP server for the network interface.
//	ip (ip) — The IPv4 address for the network interface.
//	ip6 (ip6) — The IPv6 address for the network interface.
//	mac (mac) — The MAC address for the network interface.
//	mtu (integer) — The Maximum Transmission Unit (MTU) for the network interface.
//	netmask (ip) — The IPv4 netmask for the network interface.
//	netmask6 (ip6) — The IPv6 netmask for the network interface.
//	network (ip) — The IPv4 network for the network interface.
//	network6 (ip6) — The IPv6 network for the network interface.
//	primary (string) — The name of the primary interface.
public class NetworkInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}
