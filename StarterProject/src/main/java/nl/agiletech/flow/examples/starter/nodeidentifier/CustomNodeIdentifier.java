/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.examples.starter.nodeidentifier;

import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.NodeId;
import nl.agiletech.flow.project.types.NodeIdentifier;
import nl.agiletech.flow.project.types.Context;

/**
 * A custom node identifier.
 * 
 * @author moincreemers
 *
 */
@Flow
public class CustomNodeIdentifier implements NodeIdentifier {
	@Override
	public NodeId identify(Context context) {
		return NodeId.UNKNOWN;
	}
}
