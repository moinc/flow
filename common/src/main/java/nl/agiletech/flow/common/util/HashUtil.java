package nl.agiletech.flow.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtil {
	public static String digest(Serializable object) throws IOException, NoSuchAlgorithmException {
		assert object != null;
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
				objectOutputStream.writeObject(object);
				return createChecksumHex(outputStream.toByteArray());
			}
		}
	}

	public static byte[] createChecksum(byte[] bytes, byte[] additionalBytes)
			throws IOException, NoSuchAlgorithmException {
		assert bytes != null;
		try (InputStream fis = new ByteArrayInputStream(bytes)) {
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

	public static String createChecksumHex(byte[] bytes) throws NoSuchAlgorithmException, IOException {
		return createChecksumHex(bytes, new byte[0]);
	}

	public static String createChecksumHex(byte[] bytes, byte[] additionalBytes)
			throws NoSuchAlgorithmException, IOException {
		byte[] b = createChecksum(bytes, additionalBytes);
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	private HashUtil() {
		// prevent instantiation
	}
}
