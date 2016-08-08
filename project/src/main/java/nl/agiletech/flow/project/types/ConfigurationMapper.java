/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

/**
 * Interface for configuration mappers. The responsibility of implementers is to
 * resolve one or more configuration objects to a node by examining the
 * specified context. Context.nodeData would be of particular interest here.
 * 
 * @author moincreemers
 *
 */
public interface ConfigurationMapper {
	/**
	 * Resolve node configuration objects to a node by examining the specified
	 * {@link Context}. The method is expected to add configuration objects
	 * using the Context.addConfiguration method.
	 * 
	 * @param context
	 */
	public void mapConfigurations(Context context);
}
