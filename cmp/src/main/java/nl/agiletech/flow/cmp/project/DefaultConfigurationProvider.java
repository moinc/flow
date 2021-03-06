/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.project;

import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.ConfigurationProvider;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.TakesContext;

public class DefaultConfigurationProvider implements ConfigurationProvider, TakesContext {
	final Class<?> configurationClass;
	protected Context context;

	public DefaultConfigurationProvider(Class<?> configurationClass) {
		Assertions.notNull(configurationClass, "configurationClass");
		this.configurationClass = configurationClass;
	}

	@Override
	public Object provideConfiguration() {
		Assertions.notNull(context, "context");
		return ClassUtil.createInstance(configurationClass, context);
	}

	@Override
	public void setContext(Context context) {
		Assertions.notNull(context, "context");
		this.context = context;
	}

	@Override
	public String toString() {
		return super.toString() + "-->" + configurationClass.getSimpleName();
	}

}
