/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.config;

import nl.agiletech.flow.examples.starter.nodes.DatabaseNode;
import nl.agiletech.flow.examples.starter.nodes.WebserverNode;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.ConfigurationMapper;
import nl.agiletech.flow.project.types.Context;

/**
 * A custom configuration mapper.
 * 
 * @author moincreemers
 *
 */
@Flow
public class CustomConfigurationMapper implements ConfigurationMapper {
	@Override
	public void mapConfigurations(Context context) {
		if (context.getNode() instanceof WebserverNode) {
			context.createConfigurationProvider(new ServerConfig());
		}
		if (context.getNode() instanceof DatabaseNode) {
			context.createConfigurationProvider(new ServerConfig());
		}
	}
}
