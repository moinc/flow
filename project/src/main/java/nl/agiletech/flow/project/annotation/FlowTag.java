/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Assigns a tag to a type. Tags are used to...
 * 
 * @author moincreemers
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowTag {
	public String name();
}
