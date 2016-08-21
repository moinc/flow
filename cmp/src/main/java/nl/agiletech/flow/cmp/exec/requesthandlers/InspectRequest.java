/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.exec.requesthandlers;

import java.util.logging.Logger;

import nl.agiletech.flow.cmp.exec.ProjectExecutor;
import nl.agiletech.flow.cmp.exec.RequestHandler;
import nl.agiletech.flow.cmp.exec.Response;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.Catalog;
import nl.agiletech.flow.project.types.CatalogStatus;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Task;

/**
 * The inspect stage (request) is meant to collect information from each task to
 * interrogate the node. This is accomplished by adding statements to a
 * {@link Catalog}. The catalog is returned to the node which executes each
 * statement. Statements return information that is held in a node data document
 * which is used by the node to perform an update request, the update request is
 * expected to contain the node data document.
 * 
 * @author moincreemers
 *
 */
public class InspectRequest implements RequestHandler {
	private static final Logger LOG = Logger.getLogger(InspectRequest.class.getName());

	@Override
	public void handle(Context context, Response response) throws Exception {
		Assertions.notNull(context, "context");
		Assertions.notNull(response, "response");
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			Catalog catalog = new Catalog("", CatalogStatus.MUST_EXECUTE);
			for (Object obj : context.getDependencies(ProjectExecutor.ENABLED_TASK_FILTER)) {
				Task task = (Task) obj;
				log.normal().append("inspect: " + task).print();
				task.inspect(context, catalog);
			}
			response.write(catalog);
		}
	}
}
