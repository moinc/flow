/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.util.logging.Logger;

import nl.agiletech.flow.common.cli.logging.Color;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.ConfigurationMapper;
import nl.agiletech.flow.project.types.ConfigurationProvider;
import nl.agiletech.flow.project.types.Context;

public class GlobalConfigurationMapper implements ConfigurationMapper {
	private static final Logger LOG = Logger.getLogger(GlobalConfigurationMapper.class.getName());

	final ProjectConfiguration projectConfiguration;

	public static GlobalConfigurationMapper createInstance(ProjectConfiguration projectConfiguration) {
		return new GlobalConfigurationMapper(projectConfiguration);
	}

	private GlobalConfigurationMapper(ProjectConfiguration projectConfiguration) {
		Assertions.notNull(projectConfiguration, "projectConfiguration");
		this.projectConfiguration = projectConfiguration;
	}

	@Override
	public void mapConfigurations(Context context) {
		Assertions.notNull(context, "context");
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			log.normal().append("global configuration classes:").print();
			for (Class<?> clazz : projectConfiguration.getConfigurationClasses()) {
				ConfigurationProvider configurationProvider = new DefaultConfigurationProvider(clazz);
				context.addConfigurationProvider(configurationProvider);
				log.normal().foreground(Color.GREEN).append("  +" + clazz).print();
			}
		}
	}
}
