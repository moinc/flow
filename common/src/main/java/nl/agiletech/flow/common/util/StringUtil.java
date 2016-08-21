/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringUtil {
	public static String notNull(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	public static String or(String value, String alt) {
		return notNull(value).isEmpty() ? notNull(alt) : notNull(value);
	}

	public static List<String> factors(String... values) {
		Assertions.notNull(values, "values");
		List<String> result = new ArrayList<>();
		StringBuffer x = new StringBuffer();
		for (String value : values) {
			if (x.length() != 0) {
				x.append(".");
			}
			x.append(value);
			result.add(x.toString());
		}
		return result;
	}

	public static String join(Object values, String separator) {
		Assertions.notNull(values, "values");
		Assertions.notNull(separator, "separator");
		StringBuffer sb = new StringBuffer();
		Object[] x = new Object[0];
		if (values instanceof Collection) {
			x = ((Collection<?>) values).toArray();
		} else if (values.getClass().isArray()) {
			x = (Object[]) values;
		}
		for (int i = 0; i < x.length; i++) {
			if (i != 0 && separator.length() != 0) {
				sb.append(separator);
			}
			sb.append(x[i]);
		}
		return sb.toString();
	}
}
