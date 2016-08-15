/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.common.template.TemplateRenderException;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.NodeId;
import nl.agiletech.flow.project.types.NodeIdentifier;

public class RootNodeIdentifier implements NodeIdentifier {
	private static final Logger LOG = Logger.getLogger(RootNodeIdentifier.class.getName());

	final ProjectConfiguration projectConfiguration;

	public static NodeIdentifier createInstance(ProjectConfiguration projectConfiguration) {
		return new RootNodeIdentifier(projectConfiguration);
	}

	private RootNodeIdentifier(ProjectConfiguration fpc) {
		this.projectConfiguration = fpc;
	}

	@Override
	public NodeId identify(Context context) throws Exception {
		assert context != null;
		LOG.info("identifying node:");
		List<NodeId> identities = new ArrayList<>();
		if (!attemptToIdentifyUsingCustomIdentifier(context, identities)) {
			performHostNameIdentification(context, identities);
		}
		NodeId found = identities.size() != 0 ? identities.get(0) : NodeId.UNKNOWN;
		context.setNodeId(found);
		LOG.info("  +" + found);
		return found;
	}

	private boolean attemptToIdentifyUsingCustomIdentifier(Context context, List<NodeId> identities) throws Exception {
		for (Class<? extends NodeIdentifier> clazz : projectConfiguration.getNodeIdentifierClasses()) {
			LOG.fine("attempt to identify node using: " + clazz.getName());
			NodeIdentifier nodeIdentifier = ClassUtil.createInstance(clazz, context);
			NodeId nodeId = nodeIdentifier.identify(context);
			if (nodeId == null) {
				throw new NodeIdentificationException(
						"illegal value returned by custom node identifier: " + nodeIdentifier);
			}
			if (!nodeId.isUnknown()) {
				identities.add(0, nodeId);
			}
		}
		return false;
	}

	private void performHostNameIdentification(Context context, List<NodeId> identities)
			throws IOException, TemplateRenderException, NodeIdentificationException {
		LOG.fine("attempt to identify node using default node identifier");
		String domain = (String) context.getNodeData().get("network.domain", "");
		String fqdn = (String) context.getNodeData().get("network.fqdn", "");
		NodeId nodeId = NodeId.get(domain, fqdn);
		if (nodeId == null || nodeId.isUnknown()) {
			throw new NodeIdentificationException("failed to identify node using NodeData");
		}
		identities.add(0, nodeId);
	}
}