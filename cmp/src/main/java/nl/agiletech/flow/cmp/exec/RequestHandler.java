/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.exec;

import nl.agiletech.flow.project.types.Context;

public interface RequestHandler {
	/**
	 * Performs a specific operation on a task.
	 * 
	 * @param context
	 * @param response
	 * @throws Exception
	 */
	void handle(Context context, Response response) throws Exception;
}
