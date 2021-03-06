/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.roles;

import nl.agiletech.flow.examples.starter.components.TomcatComponent;
import nl.agiletech.flow.examples.starter.components.UfwComponent;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Component;
import nl.agiletech.flow.project.types.Role;

/**
 * A role that includes a component.
 * 
 * @author moincreemers
 *
 */
@Flow
public class ApplicationServerRole implements Role {
	public Component ufwComponent = new UfwComponent();
	public Component tomcatComponent = new TomcatComponent();
	public Role software = new DeploySoftwareRole();
}
