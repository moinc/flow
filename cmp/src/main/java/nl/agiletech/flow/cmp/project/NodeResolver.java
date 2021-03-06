/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Logger;

import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.cmp.jarinspector.ObjectDiscoveryOptions;
import nl.agiletech.flow.common.cli.logging.Color;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Filter;
import nl.agiletech.flow.project.types.Identity;
import nl.agiletech.flow.project.types.Node;

public class NodeResolver {
	private static final Logger LOG = Logger.getLogger(NodeResolver.class.getName());

	static final Filter<Class<?>> IDENTITY_TYPEFILTER = new Filter<Class<?>>() {
		@Override
		public boolean include(Class<?> value) {
			return Identity.class.isAssignableFrom(value);
		}
	};

	public static NodeResolver createInstance(ProjectConfiguration projectConfiguration) {
		return new NodeResolver(projectConfiguration);
	}

	final ProjectConfiguration projectConfiguration;

	private NodeResolver(ProjectConfiguration projectConfiguration) {
		Assertions.notNull(projectConfiguration, "projectConfiguration");
		this.projectConfiguration = projectConfiguration;
	}

	public boolean resolveAndAssert(Context context)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assertions.notNull(context, "context");
		return resolve(context) != null;
	}

	public Node resolve(Context context)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assertions.notNull(context, "context");
		Assertions.notNull(context.getNodeId(), "context.getNodeId");
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			log.normal().append("resolving node class:").print();
			for (Class<? extends Node> clazz : projectConfiguration.getNodeClasses()) {
				Node node = ClassUtil.createInstance(clazz, context);
				ObjectDiscoveryOptions options = ObjectDiscoveryOptions.createInstanceForDependencyDiscovery()
						.withTypeFilter(IDENTITY_TYPEFILTER);
				Map<String, Object> objects = ClassUtil.discoverObjects(node, context, options);
				for (Object obj : objects.values()) {
					if (obj instanceof Identity) {
						Identity identity = (Identity) obj;
						if (identity.matches(context.getNodeId())) {
							log.normal().foreground(Color.GREEN).append("  +node: " + node).print();
							context.setNode(node);
							return node;
						}
					}
				}
			}
			return null;
		}
	}
}
