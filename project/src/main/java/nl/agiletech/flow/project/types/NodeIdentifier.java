/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

/**
 * Interface for node identifiers. The responsibility of implementers is to
 * resolve the identity of the node by examining the specified context.
 * Context.nodeData would be of particular interest here. If the implementor is
 * unable to resolve the identity of the node, then it must return
 * {@link NodeId.UNKNOWN}.
 * 
 * @author moincreemers
 *
 */
public interface NodeIdentifier {
	/**
	 * Resolves the identity of the node by examining the specified context and
	 * return either a valid {@link NodeId} or {@link NodeId.UNKNOWN} in case
	 * the node could not be identified.
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	NodeId identify(Context context) throws Exception;
}
