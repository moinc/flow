/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import nl.agiletech.flow.common.template.TemplateEngine;
import nl.agiletech.flow.common.template.TemplateRenderException;
import nl.agiletech.flow.common.template.builtin.SimpleTemplateEngine;

public class Context implements ProvidesContext {
	private static final Logger LOG = Logger.getLogger(Context.class.getName());

	final ContextValidator contextValidator;
	final List<String> validationErrors = new ArrayList<>();
	final ConfigurationSettings configurationSettings;
	final UUID sessionId;
	final RequestType requestType;
	final NodeData nodeData;
	final List<ConfigurationProvider> configurationProviders = new ArrayList<>();

	NodeId nodeId = NodeId.UNKNOWN;
	Node node;
	Platform platform;
	TemplateEngine templateEngine;
	final Map<String, Object> configuration = new LinkedHashMap<>();
	final List<Object> dependencies = new ArrayList<>();

	public static Context createInstance(ContextValidator contextValidator, ConfigurationSettings configurationSettings,
			RequestType requestType) throws Exception {
		return new Context(contextValidator, configurationSettings, UUID.randomUUID(), requestType, NodeData.EMPTY);
	}

	public static Context createInstance(ContextValidator contextValidator, ConfigurationSettings configurationSettings,
			RequestType requestType, NodeData nodeData) throws Exception {
		return new Context(contextValidator, configurationSettings, UUID.randomUUID(), requestType, nodeData);
	}

	public static Context reviveInstance(ContextValidator contextValidator, ConfigurationSettings configurationSettings,
			UUID sessionId, RequestType requestType) throws Exception {
		return new Context(contextValidator, configurationSettings, sessionId, requestType, NodeData.EMPTY);
	}

	private Context(ContextValidator contextValidator, ConfigurationSettings configurationSettings, UUID sessionId,
			RequestType requestType, NodeData nodeData) throws Exception {
		assert contextValidator != null && configurationSettings != null && sessionId != null && requestType != null
				&& nodeData != null;
		this.contextValidator = contextValidator;
		this.configurationSettings = configurationSettings;
		this.sessionId = sessionId;
		this.requestType = requestType;
		this.nodeData = nodeData;

		initTemplateEngine();

		applyTo(this.configurationSettings, this.nodeData);
	}

	private void initTemplateEngine() throws InstantiationException, IllegalAccessException {
		Class<?> templateEngineClass = configurationSettings.getTemplateEngineClass();
		if (templateEngineClass != SimpleTemplateEngine.class) {
			templateEngine = (TemplateEngine) templateEngineClass.newInstance();
		} else {
			templateEngine = new SimpleTemplateEngine();
		}
	}

	@Override
	public void applyTo(Object... objects) {
		assert objects != null;
		for (Object obj : objects) {
			if (obj == null) {
				continue;
			}
			if (obj instanceof TakesContext) {
				LOG.fine("applying context to: " + obj);
				((TakesContext) obj).setContext(this);
			} else {
				LOG.fine("NOT applying context to: " + obj);
			}
		}
	}

	/**
	 * A configuration settings object. Note that this has nothing to do with
	 * node configuration (see: {@link ConfigurationMapper}.
	 * 
	 * @return
	 */
	public ConfigurationSettings getConfigurationSettings() {
		return configurationSettings;
	}

	public UUID getSessionId() {
		return sessionId;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public NodeData getNodeData() {
		return nodeData;
	}

	public void setNodeId(NodeId nodeId) {
		this.nodeId = nodeId;
	}

	public NodeId getNodeId() {
		return nodeId;
	}

	public void setNode(Node node) {
		applyTo(node);
		this.node = node;
	}

	public Node getNode() {
		return node;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public String renderTemplate(Reader src) throws IOException, TemplateRenderException {
		try (Writer dest = new StringWriter()) {
			templateEngine.render(src, dest, configuration);
			return dest.toString();
		}
	}

	public void createConfigurationProvider(Object... objects) {
		assert objects != null;
		for (Object obj : objects) {
			addConfigurationProvider(new SimpleConfigurationProvider(obj));
		}
	}

	public void addConfigurationProvider(ConfigurationProvider... providers) {
		assert providers != null;
		for (ConfigurationProvider provider : providers) {
			applyTo(provider);
			configurationProviders.add(provider);
		}
	}

	public List<ConfigurationProvider> getConfigurationProviders() {
		return configurationProviders;
	}

	public void setConfiguration(Map<String, Object> configuration) {
		assert configuration != null;
		this.configuration.putAll(configuration);
	}

	public Map<String, Object> getConfiguration() {
		return configuration;
	}

	public void setDependencies(List<Object> dependencies) {
		this.dependencies.clear();
		this.dependencies.addAll(dependencies);
	}

	public List<Object> getDependencies(Filter<Object> filter) {
		assert filter != null;
		List<Object> result = new ArrayList<>();
		for (Object dependency : dependencies) {
			if (dependency != null && filter.include(dependency)) {
				result.add(dependency);
			}
		}
		return result;
	}

	public void validate() {
		validationErrors.clear();
		contextValidator.validate(this, validationErrors);
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}
}
