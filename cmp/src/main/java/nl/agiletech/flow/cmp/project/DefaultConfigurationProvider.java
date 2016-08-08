package nl.agiletech.flow.cmp.project;

import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.project.types.ConfigurationProvider;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.TakesContext;

public class DefaultConfigurationProvider implements ConfigurationProvider, TakesContext {

	final Class<?> configurationClass;
	protected Context context;

	public DefaultConfigurationProvider(Class<?> configurationClass) {
		this.configurationClass = configurationClass;
	}

	@Override
	public Object provideConfiguration() {
		assert configurationClass != null && context != null;
		return ClassUtil.createInstance(configurationClass, context);
	}

	@Override
	public void setContext(Context context) {
		this.context = context;
	}
}
