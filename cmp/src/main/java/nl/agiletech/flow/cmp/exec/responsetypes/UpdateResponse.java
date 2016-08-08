/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.exec.responsetypes;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.agiletech.flow.project.types.Catalog;

public class UpdateResponse extends AbstractResponse {
	final ObjectMapper objectMapper = new ObjectMapper();

	public UpdateResponse(OutputStream outputStream) {
		super(outputStream);
	}

	@Override
	public void write(Object data) throws IOException {
		if (data instanceof Catalog) {
			write((Catalog) data);
		}
	}

	public void write(Catalog catalog) throws JsonGenerationException, JsonMappingException, IOException {
		objectMapper.writeValue(getOutputStream(), catalog);
	}
}
