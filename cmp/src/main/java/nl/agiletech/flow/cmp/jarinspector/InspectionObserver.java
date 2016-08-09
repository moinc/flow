/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.jarinspector;

/**
 * Allows an object to observe the discovery of objects in a Jar.
 * 
 * @author moincreemers
 *
 */
public interface InspectionObserver {
	void observe(Class<?> clazz);
}
