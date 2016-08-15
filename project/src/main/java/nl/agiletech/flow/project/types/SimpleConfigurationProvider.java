/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

public class SimpleConfigurationProvider implements ConfigurationProvider {
	final Object obj;

	public SimpleConfigurationProvider(Object obj) {
		super();
		this.obj = obj;
	}

	@Override
	public Object provideConfiguration() {
		return obj;
	}

	@Override
	public String toString() {
		String objName = obj != null ? obj.toString() : "<null>";
		return super.toString() + "--->" + objName;
	}
}
