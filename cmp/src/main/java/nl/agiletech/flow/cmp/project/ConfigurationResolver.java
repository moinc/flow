/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.util.logging.Logger;

import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.project.types.ConfigurationMapper;
import nl.agiletech.flow.project.types.Context;

public class ConfigurationResolver implements ConfigurationMapper {
	private static final Logger LOG = Logger.getLogger(ConfigurationResolver.class.getName());

	public static ConfigurationResolver createInstance(ProjectConfiguration projectConfiguration) {
		return new ConfigurationResolver(projectConfiguration);
	}

	final ProjectConfiguration projectConfiguration;

	private ConfigurationResolver(ProjectConfiguration projectConfiguration) {
		assert projectConfiguration != null;
		this.projectConfiguration = projectConfiguration;
	}

	@Override
	public void mapConfigurations(Context context) {
		LOG.info("custom configuration mappers:");
		for (Class<? extends ConfigurationMapper> clazz : projectConfiguration.getConfigurationMapperClasses()) {
			LOG.info("  +" + clazz.getName());
			ConfigurationMapper configurationMapper = ClassUtil.createInstance(clazz, context);
			configurationMapper.mapConfigurations(context);
		}
	}
}
