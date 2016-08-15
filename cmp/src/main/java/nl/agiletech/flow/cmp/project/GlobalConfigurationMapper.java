/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.util.logging.Logger;

import nl.agiletech.flow.project.types.ConfigurationMapper;
import nl.agiletech.flow.project.types.ConfigurationProvider;
import nl.agiletech.flow.project.types.Context;

public class GlobalConfigurationMapper implements ConfigurationMapper {
	private static final Logger LOG = Logger.getLogger(GlobalConfigurationMapper.class.getName());

	final ProjectConfiguration projectConfiguration;

	public static GlobalConfigurationMapper createInstance(ProjectConfiguration projectConfiguration) {
		return new GlobalConfigurationMapper(projectConfiguration);
	}

	private GlobalConfigurationMapper(ProjectConfiguration fpc) {
		this.projectConfiguration = fpc;
	}

	@Override
	public void mapConfigurations(Context context) {
		LOG.info("global configuration classes:");
		for (Class<?> clazz : projectConfiguration.getConfigurationClasses()) {
			ConfigurationProvider configurationProvider = new DefaultConfigurationProvider(clazz);
			context.addConfigurationProvider(configurationProvider);
			LOG.info("  +" + clazz);
		}
	}
}
