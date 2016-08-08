/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5CheckSum {
	private MD5CheckSum() {
		// prevent instantiation
	}

	public static byte[] createChecksum(File file) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		return createChecksum(file, new byte[0]);
	}

	public static byte[] createChecksum(File file, byte[] additionalBytes)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		assert file != null && file.exists();
		try (InputStream fis = new FileInputStream(file)) {
			byte[] buffer = new byte[1024];
			MessageDigest md = MessageDigest.getInstance("MD5");
			int bytesRead;
			do {
				bytesRead = fis.read(buffer);
				if (bytesRead > 0) {
					md.update(buffer, 0, bytesRead);
				}
			} while (bytesRead != -1);
			if (additionalBytes != null && additionalBytes.length != 0) {
				md.update(additionalBytes);
			}
			return md.digest();
		}
	}

	public static String createChecksumHex(File file)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		return createChecksumHex(file, new byte[0]);
	}

	public static String createChecksumHex(File file, byte[] additionalBytes)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		byte[] b = createChecksum(file, additionalBytes);
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

}
