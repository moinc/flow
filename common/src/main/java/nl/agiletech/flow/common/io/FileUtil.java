/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

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

	public static String readAsString(InputStream inputStream, Charset charset) throws IOException {
		assert inputStream != null;
		try (StringWriter writer = new StringWriter()) {
			IOUtils.copy(inputStream, writer, charset.name());
			return writer.toString();
		}
	}
}
