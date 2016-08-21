/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.exec.responsetypes;

import java.io.IOException;
import java.io.OutputStream;

import nl.agiletech.flow.cmp.exec.Response;
import nl.agiletech.flow.common.util.Assertions;

public abstract class AbstractResponse implements Response {
	private final OutputStream outputStream;

	public AbstractResponse(OutputStream outputStream) {
		Assertions.notNull(outputStream, "outputStream");
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
