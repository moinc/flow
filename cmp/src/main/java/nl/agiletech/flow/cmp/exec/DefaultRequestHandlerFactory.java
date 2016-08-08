package nl.agiletech.flow.cmp.exec;

import nl.agiletech.flow.cmp.exec.requesthandlers.InspectRequest;
import nl.agiletech.flow.cmp.exec.requesthandlers.NullHandler;
import nl.agiletech.flow.cmp.exec.requesthandlers.ReportRequest;
import nl.agiletech.flow.cmp.exec.requesthandlers.ResourceRequest;
import nl.agiletech.flow.cmp.exec.requesthandlers.UpdateRequest;
import nl.agiletech.flow.project.types.Context;

public class DefaultRequestHandlerFactory implements RequestHandlerFactory {
	final Context context;

	public DefaultRequestHandlerFactory(Context context) {
		this.context = context;
	}

	@Override
	public RequestHandler create() {
		switch (context.getRequestType()) {
		case INSPECT:
			return new InspectRequest();
		case UPDATE:
			return new UpdateRequest();
		case RESOURCE:
			return new ResourceRequest();
		case REPORT:
			return new ReportRequest();
		case UNDEFINED:
			break;
		}
		return new NullHandler();
	}
}
