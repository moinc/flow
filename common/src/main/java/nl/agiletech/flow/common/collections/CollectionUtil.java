package nl.agiletech.flow.common.collections;

import java.util.Collection;

public final class CollectionUtil {
	public static boolean containsAny(Collection<?> a, Collection<?> b) {
		for (Object elem : b) {
			if (a.contains(elem)) {
				return true;
			}
		}
		return false;
	}
}
