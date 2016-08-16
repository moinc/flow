/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CollectionUtil {
	public static boolean containsAny(Collection<?> a, Collection<?> b) {
		for (Object elem : b) {
			if (a.contains(elem)) {
				return true;
			}
		}
		return false;
	}

	public static void flatten(Map<String, Object> map, Map<String, Object> dest) {
		assert map != null;
		if (map.size() == 0) {
			return;
		}
		flatten("", map, dest);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void flatten(String parentKey, Map src, Map dest) {
		for (Object key : src.keySet()) {
			String k = parentKey + (key == null ? "?" : key.toString());
			Object v = src.get(key);
			if (v instanceof Map) {
				flatten(k + ".", (Map) v, dest);
			} else {
				dest.put(k, v);
			}
		}
	}

	public static Map<String, Object> sort(Map<String, Object> src) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<String> keys = new ArrayList<>(src.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			result.put(key, src.get(key));
		}
		return result;
	}

	public static Map<String, Object> diff(Map<String, Object> a, Map<String, Object> b) {
		assert a != null && b != null;
		Map<String, Object> result = new LinkedHashMap<>();
		if (a.size() == 0 && b.size() == 0) {
			return result;
		}
		List<String> keysA = new ArrayList<>(a.keySet());
		keysA.removeAll(b.keySet());
		List<String> keysB = new ArrayList<>(b.keySet());
		keysB.removeAll(a.keySet());
		for (String key : keysA) {
			result.put(key, a.get(key));
		}
		for (String key : keysB) {
			result.put(key, b.get(key));
		}
		return result;
	}
}
