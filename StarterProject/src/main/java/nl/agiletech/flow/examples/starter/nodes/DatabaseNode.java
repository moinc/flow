package nl.agiletech.flow.examples.starter.nodes;

import nl.agiletech.flow.examples.starter.roles.DatabaseServerRole;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.BuiltIn;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.Role;

/**
 * A node that declares one target machine and assigns a role.
 * 
 * @author moincreemers
 *
 */
@Flow
public class DatabaseNode extends Node {
	public DatabaseNode() {
		super(BuiltIn.PLATFORM_DARWIN, "database1.local");
	}

	public Role databaseServerRole = new DatabaseServerRole();
}
