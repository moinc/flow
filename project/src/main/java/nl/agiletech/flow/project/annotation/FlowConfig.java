/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares that the type is a configuration provider. A configuration provider
 * object represents meta data. Flow reads configuration data from the
 * configuration provider by examining its fields. This includes inherited
 * fields. This annotation should be used on classes that represent global
 * configuration settings that should be are applied regardless of the node that
 * is being provisioned.
 * 
 * @author moincreemers
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowConfig {
}
