/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.exec;

import java.io.OutputStream;
import java.util.logging.Logger;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Filter;
import nl.agiletech.flow.project.types.Task;

public class ProjectExecutor {
	private static final Logger LOG = Logger.getLogger(ProjectExecutor.class.getName());

	final Filter<Object> taskFilter = new Filter<Object>() {
		@Override
		public boolean include(Object value) {
			return value instanceof Task;
		}
	};

	final OutputStream outputStream;
	final Context context;
	final RequestHandlerFactory requestHandlerFactory;
	final ResponseFactory responseFactory;

	public static ProjectExecutor createInstance(OutputStream outputStream, Context context) {
		return new ProjectExecutor(outputStream, context);
	}

	private ProjectExecutor(OutputStream outputStream, Context context) {
		this.outputStream = outputStream;
		this.context = context;
		this.requestHandlerFactory = new DefaultRequestHandlerFactory(context);
		this.responseFactory = new DefaultResponseFactory(outputStream, context);
	}

	public void run() throws Exception {
		// project goes through a number of steps:
		// 1. Initialization
		// 2. Request
		// 3. Termination

		Response response = responseFactory.create();

		initialize(context);
		executeRequest(context, requestHandlerFactory, response);
		terminate(context);
	}

	private void initialize(Context context) {
		LOG.info("--- initialize ---");
		for (Object obj : context.getDependencies(taskFilter)) {
			initialize((Task) obj, context);
		}
	}

	private void initialize(Task task, Context context) {
		LOG.info("initialize task: " + task);
		try {
			task.initialize(context);
		} catch (Exception e) {
			LOG.severe("task " + task + " threw an error while initializing: " + e.getMessage());
		}
	}

	private void executeRequest(Context context, RequestHandlerFactory requestHandlerFactory, Response response)
			throws Exception {
		LOG.info("--- execute ---");
		RequestHandler requestHandler = requestHandlerFactory.create();
		requestHandler.handle(context, response);
	}

	private void terminate(Context context) {
		LOG.info("--- terminate ---");
		for (Object obj : context.getDependencies(taskFilter)) {
			terminate((Task) obj, context);
		}
	}

	private void terminate(Task task, Context context) {
		LOG.info("terminate task: " + task);
		try {
			task.terminate(context);
		} catch (Exception e) {
			LOG.severe("task " + task + " threw an error while terminating: " + e.getMessage());
		}
	}
}
