package nl.agiletech.flow.project.types;

/**
 * Identity is a general interface for any object that represents an
 * identifyable thing.
 * 
 * @author moincreemers
 *
 */
public interface Identity {
	boolean matches(NodeId nodeId);
}
