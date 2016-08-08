/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

public abstract class Service extends Task {
	public Service() {
		super(true);
	}

	public abstract String getName();
}