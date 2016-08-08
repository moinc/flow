/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class FileUtil {
	private FileUtil() {
		// prevent instantiation
	}

	public static File toFile(File currentDirectory, String file) {
		String f = file == null ? "" : file;
		if (f.startsWith("/")) {
			return new File(f);
		}
		return new File(currentDirectory, f);
	}

	public static void copy(File file, OutputStream output) throws IOException {
		try (FileInputStream input = new FileInputStream(file)) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = input.read(buffer)) > 0) {
				output.write(buffer, 0, bytesRead);
			}
		}
	}
}
