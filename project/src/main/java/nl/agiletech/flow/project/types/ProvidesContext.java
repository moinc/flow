/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

/**
 * Provides a {@link Context} instance to objects that implement the
 * {@link TakesContext} interface.
 * 
 * @author moincreemers
 *
 */
public interface ProvidesContext {
	/**
	 * Provides a {@link Context} instance to objects that implement the
	 * {@link TakesContext} interface.
	 * 
	 * @param objects
	 */
	void applyTo(Object... objects);
}
