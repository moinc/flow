/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

public class NodeId {
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

	public static final NodeId get(String networkName, String hostName) {
		if (networkName == null || networkName.isEmpty()) {
			networkName = ANY_NETWORK_NAME_MASK;
		}
		return new NodeId(false, networkName, hostName);
	}

	private NodeId(boolean unknown, String networkName, String hostName) {
		assert networkName != null && !networkName.isEmpty();
		assert hostName != null;
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
