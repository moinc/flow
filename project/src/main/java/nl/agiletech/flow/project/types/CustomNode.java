package nl.agiletech.flow.project.types;

public abstract class CustomNode extends AbstractNode {
	public CustomNode(String platform, String hostName) {
		super(platform, hostName);
	}

	public CustomNode(String platform, String[] hostNames) {
		super(platform, hostNames);
	}
}
