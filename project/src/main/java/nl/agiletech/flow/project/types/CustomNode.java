/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

public abstract class CustomNode extends AbstractNode {
	public CustomNode(String hostName) {
		super(hostName);
	}

	public CustomNode(String platform, String[] hostNames) {
		super(hostNames);
	}
}
