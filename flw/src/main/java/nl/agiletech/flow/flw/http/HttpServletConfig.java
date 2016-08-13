package nl.agiletech.flow.flw.http;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import nl.agiletech.flow.project.types.ConfigurationSettings;

public class HttpServletConfig implements ServletConfig {
	final HttpServlet servlet;
	final ConfigurationSettings configurationSettings;

	public HttpServletConfig(HttpServlet servlet, ConfigurationSettings configurationSettings) {
		super();
		this.servlet = servlet;
		this.configurationSettings = configurationSettings;
	}

	@Override
	public String getServletName() {
		return servlet.getServletName();
	}

	@Override
	public ServletContext getServletContext() {
		return servlet.getServletContext();
	}

	@Override
	public String getInitParameter(String name) {
		return (String) configurationSettings.get(name, "");
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return null;
	}

	public ConfigurationSettings getConfigurationSettings() {
		return configurationSettings;
	}
}
