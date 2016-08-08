/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.exec.requesthandlers;

import java.util.logging.Logger;

import nl.agiletech.flow.cmp.exec.RequestHandler;
import nl.agiletech.flow.cmp.exec.Response;
import nl.agiletech.flow.project.types.Catalog;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Filter;
import nl.agiletech.flow.project.types.Task;

/**
 * The update stage (request) is meant to collect information from each task to
 * configure the node. This is accomplished by adding statements to a
 * {@link Catalog}. The catalog is returned to the node which executes each
 * statement.
 * 
 * @author moincreemers
 *
 */
public class UpdateRequest implements RequestHandler {
	private static final Logger LOG = Logger.getLogger(UpdateRequest.class.getName());

	final Filter<Object> taskFilter = new Filter<Object>() {
		@Override
		public boolean include(Object value) {
			return value instanceof Task;
		}
	};

	@Override
	public void handle(Context context, Response response) throws Exception {
		Catalog catalog = new Catalog();
		for (Object obj : context.getDependencies(taskFilter)) {
			Task task = (Task) obj;
			LOG.info("update: " + task);
			task.update(context, catalog);
			response.write(catalog);
		}
	}
}
