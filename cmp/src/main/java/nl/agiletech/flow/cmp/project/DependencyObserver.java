package nl.agiletech.flow.cmp.project;

/**
 * Allows an object to observe the discovery of other objects in a dependency
 * tree.
 * 
 * @author moincreemers
 *
 */
public interface DependencyObserver {
	void observe(Object obj, Class<?> declaredIn);
}
