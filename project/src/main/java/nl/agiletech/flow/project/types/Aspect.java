package nl.agiletech.flow.project.types;

/**
 * Interface for aspect classes. An aspect is a partial view of an object such
 * as a {@link Task} and provides a visit method that, when invoked, configures
 * the specified object to conform to the aspect instance. The specified object
 * must implement the HasAttributes class in order to receive these settings.
 * 
 * @author moincreemers
 *
 */
// TODO: make Aspect extend a Task because an aspect does need to
// generate instructions to be executed on the target platform
public interface Aspect extends AttributeSource {
	/**
	 * Returns true if this aspect is required for the object it applies to.
	 * 
	 * @return
	 */
	boolean isRequired();

	/**
	 * Returns true if the aspect is configured correctly.
	 * 
	 * @return
	 */
	boolean isValid();

	/**
	 * Returns true if the aspect is enabled. A required aspect is always
	 * enabled.
	 * 
	 * @return
	 */
	boolean isEnabled();

	/**
	 * Visit the specified object instance to configure it for this apect.
	 * 
	 * @param hasAttributes
	 */
	void visit(HasAttributes hasAttributes);
}
