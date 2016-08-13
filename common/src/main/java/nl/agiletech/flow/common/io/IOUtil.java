package nl.agiletech.flow.common.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {
	public static byte[] copy(InputStream src) throws IOException {
		assert src != null;
		try (ByteArrayOutputStream dest = new ByteArrayOutputStream()) {
			copy(src, dest);
			return dest.toByteArray();
		}
	}

	public static void copy(InputStream src, OutputStream dest) throws IOException {
		byte[] buffer = new byte[1024];
		while (true) {
			int r = src.read(buffer);
			if (r == -1) {
				break;
			}
			dest.write(buffer, 0, r);
		}
	}

	public static void copy(InputStream src, File dest) throws FileNotFoundException, IOException {
		try (FileOutputStream outputStream = new FileOutputStream(dest)) {
			copy(src, outputStream);
		}
	}

	public static void copy(File src, InputStream dest) throws FileNotFoundException, IOException {
		try (FileInputStream inputStream = new FileInputStream(src)) {
			copy(src, dest);
		}
	}

	public static byte[] copy(File src) throws FileNotFoundException, IOException {
		try (FileInputStream inputStream = new FileInputStream(src)) {
			return copy(inputStream);
		}
	}
}
