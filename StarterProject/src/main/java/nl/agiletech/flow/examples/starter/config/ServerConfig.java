/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.config;

import nl.agiletech.flow.project.types.Template;

public class ServerConfig {
	public Template publicHostName = Template.inline("${*.hostName}.${*.domainName}");
}
