package nl.agiletech.flow.project.types;

import nl.agiletech.flow.common.util.Assertions;

/**
 * Interface for objects that can be 'switched on or off'.
 * 
 * @author moincreemers
 *
 */
public interface Switchable {
	public static boolean isEnabled(Object obj) {
		Assertions.notNull(obj, "obj");
		if (obj instanceof Switchable) {
			Switchable switchable = (Switchable) obj;
			return switchable.isEnabled();
		}
		return true;
	}

	boolean isEnabled();
}
