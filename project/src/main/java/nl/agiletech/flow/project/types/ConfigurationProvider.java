package nl.agiletech.flow.project.types;

/**
 * Interface for ConfigurationProvider classes. Instances of objects that
 * implement this interface are used through {@link ConfigurationMapper} objects
 * to provide the configuration settings for a node.
 * 
 * @author moincreemers
 *
 */
public interface ConfigurationProvider {
	/**
	 * Returns a configuration object.
	 * 
	 * @return
	 */
	Object provideConfiguration();
}
