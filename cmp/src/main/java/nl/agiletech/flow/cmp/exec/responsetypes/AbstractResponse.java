package nl.agiletech.flow.cmp.exec.responsetypes;

import java.io.IOException;
import java.io.OutputStream;

import nl.agiletech.flow.cmp.exec.Response;

public abstract class AbstractResponse implements Response {
	private final OutputStream outputStream;

	public AbstractResponse(OutputStream outputStream) {
		assert outputStream != null;
		this.outputStream = outputStream;
	}

	@Override
	public void write(Object data) throws IOException {
	}

	@Override
	public void write(byte[] byteArray) throws IOException {
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}
}
