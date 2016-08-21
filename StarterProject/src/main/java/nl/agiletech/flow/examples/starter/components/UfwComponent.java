/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.components;

import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.aspects.AbstractAspect;
import nl.agiletech.flow.project.types.Component;

@Flow
public class UfwComponent extends Component {
	public AbstractAspect rulesAspect = new AbstractAspect(false) {
		@Override
		public boolean isValid() {
			return false;
		}

		public void addRule() {
			//
		}
	};

	@Override
	public String getPackageName() {
		return "ufw";
	}

	@Override
	public String getVersion() {
		return "1";
	}
}