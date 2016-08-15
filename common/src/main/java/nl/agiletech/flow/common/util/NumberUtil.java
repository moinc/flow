package nl.agiletech.flow.common.util;

import java.util.Date;

public class NumberUtil {
	public static double toDouble(Object value) {
		return toDouble(value, Double.NaN, Double.NaN);
	}

	public static double toDouble(Object value, double nullValue, double emptyValue) {
		Object v = value;
		if (v == null) {
			return nullValue;
		}
		double result = Double.NaN;
		if (v instanceof Boolean) {
			result = ((Boolean) v) ? 1d : 0d;
		} else if (v instanceof Byte) {
			result = ((Byte) v).doubleValue();
		} else if (v instanceof Short) {
			result = ((Short) v).doubleValue();
		} else if (v instanceof Integer) {
			result = ((Integer) v).doubleValue();
		} else if (v instanceof Long) {
			result = ((Long) v).doubleValue();
		} else if (v instanceof Double) {
			result = (Double) v;
		} else if (v instanceof Date) {
			result = ((Long) ((Date) v).getTime()).doubleValue();
		} else if (v instanceof String) {
			// handled later
		} else {
			// convert object to string
			v = v.toString();
		}
		if (v instanceof String) {
			if (((String) value).isEmpty()) {
				result = emptyValue;
			} else {
				result = Double.parseDouble((String) value);
			}
		}
		return result;
	}
}
