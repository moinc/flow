/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.util.logging.Logger;

import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.project.types.Context;

public class NodeResolver {
	private static final Logger LOG = Logger.getLogger(NodeResolver.class.getName());

	public static NodeResolver createInstance(ProjectConfiguration projectConfiguration) {
		return new NodeResolver(projectConfiguration);
	}

	final ProjectConfiguration projectConfiguration;

	private NodeResolver(ProjectConfiguration projectConfiguration) {
		assert projectConfiguration != null;
		this.projectConfiguration = projectConfiguration;
	}

	public boolean resolveAndAssert(Context context) {
		return resolve(context) != null;
	}

	public Node resolve(Context context) {
		assert context.getNodeId() != null;
		LOG.fine("resolving node class");
		for (Class<? extends Node> clazz : projectConfiguration.getNodeClasses()) {
			Node node = ClassUtil.createInstance(clazz, context);
			if (node.matches(context.getNodeId())) {
				LOG.info("+node: " + node);
				context.setNode(node);
				return node;
			}
		}
		return null;
	}
}
