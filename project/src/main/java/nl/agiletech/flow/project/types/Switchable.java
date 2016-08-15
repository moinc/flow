package nl.agiletech.flow.project.types;

/**
 * Interface for objects that can be 'switched on or off'.
 * 
 * @author moincreemers
 *
 */
public interface Switchable {
	public static boolean isEnabled(Object obj) {
		assert obj != null;
		if (obj instanceof Switchable) {
			Switchable switchable = (Switchable) obj;
			return switchable.isEnabled();
		}
		return true;
	}

	boolean isEnabled();
}
