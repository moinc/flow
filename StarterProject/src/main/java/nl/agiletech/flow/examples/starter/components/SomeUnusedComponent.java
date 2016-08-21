/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.components;

import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Component;

@Flow
public class SomeUnusedComponent extends Component {
	@Override
	public String getPackageName() {
		return "unused";
	}

	@Override
	public String getVersion() {
		return "1";
	}
}