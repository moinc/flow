/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.cmp.jarinspector.ObjectDiscoveryOptions;
import nl.agiletech.flow.common.util.StringUtil;
import nl.agiletech.flow.project.types.ConfigurationProvider;
import nl.agiletech.flow.project.types.KeyPair;
import nl.agiletech.flow.project.types.Context;

public class Configurator {
	private static final Logger LOG = Logger.getLogger(Configurator.class.getName());

	public static Configurator createInstance(Context context, ProjectConfiguration projectConfiguration) {
		return new Configurator(context, projectConfiguration);
	}

	final Context context;
	final ProjectConfiguration projectConfiguration;
	final ObjectDiscoveryOptions options = ObjectDiscoveryOptions.createInstanceForConfigurationDiscovery();

	private Configurator(Context context, ProjectConfiguration projectConfiguration) {
		this.context = context;
		this.projectConfiguration = projectConfiguration;
	}

	public Map<String, Object> configure()
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		LOG.info("configuring dataset");
		Map<String, Object> configuration = new HashMap<>();

		LOG.info("  NodeData:");
		for (KeyPair keyPair : context.getNodeData().getEntries()) {
			String concatenatedName = StringUtil.join(new Object[] { "nodedata", keyPair.getKey() }, ".");
			configuration.put(concatenatedName, keyPair.getValue());
			LOG.info("    [" + concatenatedName + " = " + keyPair.getValue() + "]");
		}

		for (ConfigurationProvider configurationProvider : context.getConfigurationProviders()) {
			interrogateConfigurationProvider(configurationProvider, configuration);
		}

		context.setConfiguration(configuration);

		return configuration;
	}

	private void interrogateConfigurationProvider(ConfigurationProvider configurationProvider,
			Map<String, Object> configuration) throws IllegalAccessException, InvocationTargetException {
		Object obj = configurationProvider.provideConfiguration();
		LOG.info("  " + obj + ":");
		Map<String, Object> objects = ClassUtil.discoverObjects(obj, context, options);
		for (String key : objects.keySet()) {
			Object val = objects.get(key);
			LOG.info("    [" + key + " = " + objects.get(key) + "]");
			configuration.put(key, val);
		}
	}
}
