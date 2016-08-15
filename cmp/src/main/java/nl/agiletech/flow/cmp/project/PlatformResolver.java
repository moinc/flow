/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.util.Set;
import java.util.logging.Logger;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.Platform;

public class PlatformResolver {
	private static final Logger LOG = Logger.getLogger(PlatformResolver.class.getName());

	public static PlatformResolver createInstance(Context context, ProjectConfiguration projectConfiguration) {
		return new PlatformResolver(context, projectConfiguration);
	}

	final Context context;
	final ProjectConfiguration projectConfiguration;

	private PlatformResolver(Context context, ProjectConfiguration projectConfiguration) {
		assert context != null && projectConfiguration != null;
		this.context = context;
		this.projectConfiguration = projectConfiguration;
	}

	public void resolve() {
		LOG.info("resolving platform:");
		Node node = context.getNode();
		if (node == null) {
			LOG.warning("failed to resolve platform: node not set");
			return;
		}
		Class<? extends Node> nodeClass = node.getClass();
		Set<Object> objects = projectConfiguration.getInvertedDependencyIndex().get(nodeClass);
		if (objects == null) {
			LOG.warning("failed to resolve platform: empty dependency index for node: " + node);
		}
		context.setPlatform(null);
		for (Object obj : objects) {
			if (obj instanceof Platform) {
				Platform platform = (Platform) obj;
				context.setPlatform(platform);
				LOG.info("  +" + platform);
				break;
			}
		}
		if (context.getPlatform() == null) {
			LOG.warning("failed to resolve platform: not found");
		}
	}
}
