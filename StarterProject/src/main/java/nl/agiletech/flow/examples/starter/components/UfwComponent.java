package nl.agiletech.flow.examples.starter.components;

import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Component;

@Flow
public class UfwComponent extends Component {
	@Override
	public String getPackageName() {
		return "ufw";
	}

	@Override
	public String getVersion() {
		return "1";
	}
}