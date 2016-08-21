/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import java.util.logging.Logger;

import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.common.cli.logging.Color;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.ConfigurationMapper;
import nl.agiletech.flow.project.types.Context;

public class ConfigurationResolver implements ConfigurationMapper {
	private static final Logger LOG = Logger.getLogger(ConfigurationResolver.class.getName());

	public static ConfigurationResolver createInstance(ProjectConfiguration projectConfiguration) {
		return new ConfigurationResolver(projectConfiguration);
	}

	final ProjectConfiguration projectConfiguration;

	private ConfigurationResolver(ProjectConfiguration projectConfiguration) {
		Assertions.notNull(projectConfiguration, "projectConfiguration");
		this.projectConfiguration = projectConfiguration;
	}

	@Override
	public void mapConfigurations(Context context) {
		Assertions.notNull(context, "context");
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			log.normal().append("custom configuration mappers:").print();
			for (Class<? extends ConfigurationMapper> clazz : projectConfiguration.getConfigurationMapperClasses()) {
				log.normal().foreground(Color.GREEN).append("  +" + clazz.getName()).print();
				ConfigurationMapper configurationMapper = ClassUtil.createInstance(clazz, context);
				configurationMapper.mapConfigurations(context);
			}
		}
	}
}
