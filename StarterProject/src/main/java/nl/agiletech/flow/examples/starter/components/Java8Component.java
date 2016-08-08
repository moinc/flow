package nl.agiletech.flow.examples.starter.components;

import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Component;

@Flow
public class Java8Component extends Component {
	@Override
	public String getPackageName() {
		return "java";
	}

	@Override
	public String getVersion() {
		return "8";
	}
}