/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

/**
 * A Component represents an installable software package.
 * 
 * @author moincreemers
 *
 */
abstract class AbstractComponent extends Task {
	protected AbstractComponent() {
		super(false);
	}

	public abstract String getPackageName();

	/**
	 * Returns a dependency to this component.
	 * 
	 * @return a dependency
	 */
	@Override
	public Requirement asRequirement() {
		return Requirement.get(getPackageName(), getVersion());
	}
}
