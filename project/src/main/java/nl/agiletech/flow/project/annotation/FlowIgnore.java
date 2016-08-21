/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares that the declaration should be ignored.
 * 
 * @author moincreemers
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowIgnore {
}
