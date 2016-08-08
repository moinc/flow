package nl.agiletech.flow.examples.starter.nodes;

import nl.agiletech.flow.examples.starter.roles.ApplicationServerRole;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.BuiltIn;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.Role;

/**
 * A node that declares multiple target machines and assigns a role.
 * 
 * @author moincreemers
 *
 */
@Flow
public class WebserverNode extends Node {
	public WebserverNode() {
		super(BuiltIn.PLATFORM_DARWIN, new String[] { "webserver1.local", "webserver2.local" });
	}

	public Role applicationServerRole = new ApplicationServerRole();
}
