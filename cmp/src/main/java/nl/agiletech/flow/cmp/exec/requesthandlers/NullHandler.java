package nl.agiletech.flow.cmp.exec.requesthandlers;

import nl.agiletech.flow.cmp.exec.RequestHandler;
import nl.agiletech.flow.cmp.exec.Response;
import nl.agiletech.flow.project.types.Context;

/**
 * Calls to this NullHandler will go nowhere.
 * 
 * @author moincreemers
 *
 */
public class NullHandler implements RequestHandler {
	@Override
	public void handle(Context context, Response response) throws Exception {
	}
}
