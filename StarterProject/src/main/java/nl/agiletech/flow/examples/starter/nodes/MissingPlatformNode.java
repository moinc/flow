/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.nodes;

import nl.agiletech.flow.examples.starter.roles.ApplicationServerRole;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Identity;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.NodeId;
import nl.agiletech.flow.project.types.Role;

/**
 * A node that declares one target machine and assigns a role.
 * 
 * @author moincreemers
 *
 */
@Flow
public class MissingPlatformNode extends Node {
	// public Platform platform = new OsxPlatform();
	public Identity[] identity0 = NodeId.array("webserver3.local");
	public Role applicationServerRole = new ApplicationServerRole();
}
