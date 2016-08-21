/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.exec.requesthandlers;

import java.io.PrintWriter;
import java.util.logging.Logger;

import nl.agiletech.flow.cmp.exec.ProjectExecutor;
import nl.agiletech.flow.cmp.exec.RequestHandler;
import nl.agiletech.flow.cmp.exec.Response;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Task;

/**
 * The report request allows each task to process information returned from the
 * node and report about what happened.
 * 
 * @author moincreemers
 *
 */
public class ReportRequest implements RequestHandler {
	private static final Logger LOG = Logger.getLogger(ReportRequest.class.getName());

	@Override
	public void handle(Context context, Response response) throws Exception {
		Assertions.notNull(context, "context");
		Assertions.notNull(response, "response");
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			PrintWriter pw = new PrintWriter(response.getOutputStream());
			for (Object obj : context.getDependencies(ProjectExecutor.ENABLED_TASK_FILTER)) {
				Task task = (Task) obj;
				log.normal().append("report: " + task).print();
				task.report(context, pw);
			}
		}
	}
}
