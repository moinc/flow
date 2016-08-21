/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.util;

public final class ArrayUtil {
	public static <T> T[] copy(T[] src, T[] dst) {
		if (src == null) {
			throw new IllegalArgumentException("unexpected null argument: src");
		}
		if (src.length != 0) {
			System.arraycopy(src, 0, dst, 0, dst.length);
		}
		return dst;
	}
}
