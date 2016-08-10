/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.nodes;

import nl.agiletech.flow.debian.DebianPlatform;
import nl.agiletech.flow.examples.starter.roles.DatabaseServerRole;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Identity;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.NodeId;
import nl.agiletech.flow.project.types.Platform;
import nl.agiletech.flow.project.types.Role;

/**
 * A node that declares one target machine and assigns a role.
 * 
 * @author moincreemers
 *
 */
@Flow
public class DatabaseNode extends Node {
	public Platform platform = new DebianPlatform();
	public Identity identity1 = NodeId.get("database1.local");
	public Role databaseServerRole = new DatabaseServerRole();
}
