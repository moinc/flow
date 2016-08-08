package nl.agiletech.flow.cmp.exec;

import java.io.OutputStream;

import nl.agiletech.flow.cmp.exec.responsetypes.InspectResponse;
import nl.agiletech.flow.cmp.exec.responsetypes.NullResponse;
import nl.agiletech.flow.cmp.exec.responsetypes.ReportResponse;
import nl.agiletech.flow.cmp.exec.responsetypes.ResourceResponse;
import nl.agiletech.flow.cmp.exec.responsetypes.UpdateResponse;
import nl.agiletech.flow.project.types.Context;

public class DefaultResponseFactory implements ResponseFactory {
	final OutputStream outputStream;
	final Context context;

	public DefaultResponseFactory(OutputStream outputStream, Context context) {
		this.outputStream = outputStream;
		this.context = context;
	}

	@Override
	public Response create() {
		switch (context.getRequestType()) {
		case INSPECT:
			return new InspectResponse(outputStream);
		case UPDATE:
			return new UpdateResponse(outputStream);
		case RESOURCE:
			return new ResourceResponse(outputStream);
		case REPORT:
			return new ReportResponse(outputStream);
		case UNDEFINED:
			break;
		}
		return new NullResponse(outputStream);
	}
}
