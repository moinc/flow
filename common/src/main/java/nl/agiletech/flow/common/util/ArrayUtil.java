package nl.agiletech.flow.common.util;

public final class ArrayUtil {
	public static <T> T[] copy(T[] src, T[] dst) {
		assert src != null;
		if (src.length != 0) {
			System.arraycopy(src, 0, dst, 0, dst.length);
		}
		return dst;
	}
}
