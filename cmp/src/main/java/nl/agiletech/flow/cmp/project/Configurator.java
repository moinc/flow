/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.cmp.jarinspector.ObjectDiscoveryOptions;
import nl.agiletech.flow.common.cli.logging.Color;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.collections.CollectionUtil;
import nl.agiletech.flow.project.types.ConfigurationProvider;
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

	public Map<String, Object> configure() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, NoSuchAlgorithmException, IOException {
		try (ConsoleUtil log = ConsoleUtil.OUT) {
			Map<String, Object> currentConfiguration = new LinkedHashMap<>();
			currentConfiguration.putAll(context.getConfiguration());

			Map<String, Object> configuration = new LinkedHashMap<>();
			CollectionUtil.flatten(context.getNodeData().getValues(), configuration);

			// update context now to allow other configuration objects to use
			// that
			// information
			context.setConfiguration(configuration);

			for (ConfigurationProvider configurationProvider : context.getConfigurationProviders()) {
				interrogateConfigurationProvider(configurationProvider, configuration);
			}

			// update context again to include the additional data
			context.setConfiguration(configuration);

			Map<String, Object> diff = CollectionUtil.diff(currentConfiguration, configuration);
			if (diff.size() == 0) {
				log.normal().append("configuring data dictionary: no changes").print();
			} else {
				log.normal().append("configuring data dictionary:").print();
				printToLog(diff);
			}

			return configuration;
		}
	}

	private void interrogateConfigurationProvider(ConfigurationProvider configurationProvider,
			Map<String, Object> configuration) throws IllegalAccessException, InvocationTargetException {
		Object obj = configurationProvider.provideConfiguration();
		Map<String, Object> objects = ClassUtil.discoverObjects(obj, context, options);
		CollectionUtil.flatten(objects, configuration);
	}

	private void printToLog(Map<String, Object> configuration) {
		for (String key : configuration.keySet()) {
			printToLog(key, configuration.get(key));
		}
	}

	@SuppressWarnings("rawtypes")
	private void printToLog(String key, Object value) {
		try (ConsoleUtil log = ConsoleUtil.OUT) {
			Object v = value;
			if (v == null) {
				log.normal().foreground(Color.GREEN).append("  +" + key + " = <null>]").print();
				return;
			} else if (v.getClass().isArray()) {
				v = Arrays.asList((Object[]) v);
			}
			if (v instanceof Collection) {
				int i = 0;
				for (Object w : (Collection) v) {
					String key2 = key + "[" + i + "]";
					printToLog(key2, w);
					i++;
				}
				return;
			}
			if (v instanceof Map) {
				Map map = (Map) v;
				for (Object k : map.keySet()) {
					String key2 = key + "." + k;
					printToLog(key2, map.get(k));
				}
				return;
			}
			if (value instanceof String && ((String) value).isEmpty()) {
				v = "<empty>";
			}
			log.normal().foreground(Color.GREEN).append("  +" + key + " = " + v.toString()).print();
		}
	}
}
