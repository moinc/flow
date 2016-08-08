package nl.agiletech.flow.examples.starter.roles;

import nl.agiletech.flow.examples.starter.components.PostgreSqlComponent;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Component;
import nl.agiletech.flow.project.types.Role;

/**
 * Role that includes a component.
 * 
 * @author moincreemers
 *
 */
@Flow
public class DatabaseServerRole implements Role {
	public Component databaseComponent = new PostgreSqlComponent();
	public Role test = new ApplicationServerRole();
}
