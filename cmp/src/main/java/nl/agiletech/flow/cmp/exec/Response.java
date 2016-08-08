package nl.agiletech.flow.cmp.exec;

import java.io.IOException;
import java.io.OutputStream;

public interface Response {
	void write(Object data) throws IOException;

	void write(byte[] byteArray) throws IOException;

	OutputStream getOutputStream();
}
