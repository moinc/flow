/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.exec.requesthandlers;

import java.util.logging.Logger;

import nl.agiletech.flow.cmp.exec.ProjectExecutor;
import nl.agiletech.flow.cmp.exec.RequestHandler;
import nl.agiletech.flow.cmp.exec.Response;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Task;

/**
 * The resource request is meant to return a file to a node. It is special in
 * that it filters for a task (or maybe tasks) that apply to the requested
 * resource.
 * 
 * @author moincreemers
 *
 */
public class ResourceRequest implements RequestHandler {
	private static final Logger LOG = Logger.getLogger(ResourceRequest.class.getName());

	@Override
	public void handle(Context context, Response response) throws Exception {
		try (ConsoleUtil log = ConsoleUtil.OUT) {
			for (Object obj : context.getDependencies(ProjectExecutor.ENABLED_TASK_FILTER)) {
				Task task = (Task) obj;
				log.normal().append("resource: " + task).print();
				task.resource(context, response.getOutputStream());
			}
		}
	}
}
