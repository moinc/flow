package nl.agiletech.flow.project.types;

/**
 * Common interface for all inspector implementations. An inspector is a piece
 * of code that inspects the environment it is running in and returns a value in
 * a common format. An inspector should deal with just one aspect of the
 * environment (i.e. operating system name, architecture, network interfaces,
 * etc.). Also, an inspector implementation targets a specific platform.
 * 
 * @author moincreemers
 *
 */
public interface CanInspect<O> extends ValueTransform<O>, Script {
}
