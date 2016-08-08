/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.roles;

import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Role;

/**
 * Example of a role that includes other roles.
 * 
 * @author moincreemers
 *
 */
@Flow
public class FullStackServerRole implements Role {
	public Role applicationServerRole = new ApplicationServerRole();
	public Role databaseServerRole = new DatabaseServerRole();
}
