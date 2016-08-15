/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

/**
 * Class may implement this interface when it requires a Context instance in
 * order to perform certain work.
 * 
 * @author moincreemers
 *
 */
public interface TakesContext {
	/**
	 * Should be invoked by the Context class only.
	 * 
	 * @param context
	 */
	public void setContext(Context context);
}
