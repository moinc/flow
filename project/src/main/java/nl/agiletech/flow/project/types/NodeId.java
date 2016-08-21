/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.util.ArrayList;
import java.util.List;

import nl.agiletech.flow.common.util.Assertions;

public class NodeId implements Identity {
	private static final String SEPARATOR = ":";
	private static final String ANY_NETWORK_NAME_MASK = "*";
	private static final String UNKNOWN_HOSTNAME = "";
	final boolean unknown;
	final String networkName;
	final String hostName;

	public static final NodeId UNKNOWN = new NodeId(true, ANY_NETWORK_NAME_MASK, UNKNOWN_HOSTNAME);

	public static NodeId parse(String value) {
		if (value == null || value.isEmpty()) {
			return UNKNOWN;
		}
		if (value.indexOf(SEPARATOR) == -1) {
			return new NodeId(false, ANY_NETWORK_NAME_MASK, value);
		}
		String[] values = value.split(SEPARATOR);
		String networkName = values[0];
		if (networkName.isEmpty()) {
			networkName = ANY_NETWORK_NAME_MASK;
		}
		String hostName = values[1];
		return new NodeId(false, networkName, hostName);
	}

	public static final NodeId get(String hostName) {
		return get(null, hostName);
	}

	public static final NodeId[] array(String... hostNames) {
		Assertions.notEmpty(hostNames, "hostNames");
		List<NodeId> nodeIds = new ArrayList<>();
		for (String hostName : hostNames) {
			nodeIds.add(get(null, hostName));
		}
		return nodeIds.toArray(new NodeId[0]);
	}

	public static final List<NodeId> list(String... hostNames) {
		Assertions.notEmpty(hostNames, "hostNames");
		List<NodeId> nodeIds = new ArrayList<>();
		for (String hostName : hostNames) {
			nodeIds.add(get(null, hostName));
		}
		return nodeIds;
	}

	public static final NodeId get(String networkName, String hostName) {
		if (networkName == null || networkName.isEmpty()) {
			networkName = ANY_NETWORK_NAME_MASK;
		}
		return new NodeId(false, networkName, hostName);
	}

	private NodeId(boolean unknown, String networkName, String hostName) {
		Assertions.notEmpty(networkName, "networkName");
		Assertions.notNull(hostName, "hostName");
		this.unknown = unknown;
		this.networkName = networkName;
		this.hostName = hostName;
	}

	public boolean isUnknown() {
		return unknown;
	}

	public String getNetworkName() {
		return networkName;
	}

	public String getHostName() {
		return hostName;
	}

	public boolean matches(NodeId nodeId) {
		return equals(nodeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NodeId) {
			NodeId other = (NodeId) obj;
			if (hostName.equals(other.hostName)) {
				if (ANY_NETWORK_NAME_MASK.equals(networkName)) {
					return true;
				} else if (networkName.equals(other.networkName)) {
					return true;
				}
			}
			return false;
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return networkName + SEPARATOR + hostName;
	}

}
