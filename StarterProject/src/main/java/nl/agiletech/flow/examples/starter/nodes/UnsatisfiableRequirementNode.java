/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.nodes;

import nl.agiletech.flow.examples.starter.components.SomeUnusedComponent;
import nl.agiletech.flow.examples.starter.roles.ApplicationServerRole;
import nl.agiletech.flow.osx.OsxPlatform;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.Identity;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.NodeId;
import nl.agiletech.flow.project.types.Platform;
import nl.agiletech.flow.project.types.Requirement;
import nl.agiletech.flow.project.types.Role;

/**
 * A node that declares one target machine and assigns a role.
 * 
 * @author moincreemers
 *
 */
@Flow
public class UnsatisfiableRequirementNode extends Node {
	public Platform platform = new OsxPlatform();
	public Identity[] identity0 = NodeId.array("webserver4.local");
	public Role applicationServerRole = new ApplicationServerRole();
	public Requirement requireUnusedComponent = Requirement.require(new SomeUnusedComponent());

}
