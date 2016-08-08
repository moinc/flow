/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.components;

import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Component;

@Flow
public class TomcatComponent extends Component {
	@Override
	public String getPackageName() {
		return "tomcat";
	}

	@Override
	public String getVersion() {
		return "8";
	}

	public Component javaComponent = new Java8Component();
}