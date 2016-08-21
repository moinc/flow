package nl.agiletech.flow.bot.exec;

import java.util.LinkedHashMap;
import java.util.Map;

import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.Catalog;
import nl.agiletech.flow.project.types.Instruction;

public class TaskRunner {
	final Catalog catalog;
	final TaskResultObserver taskResultObserver;

	public TaskRunner(Catalog catalog, TaskResultObserver taskResultObserver) {
		Assertions.notNull(catalog, "catalog");
		Assertions.notNull(taskResultObserver, "taskResultObserver");
		this.catalog = catalog;
		this.taskResultObserver = taskResultObserver;
	}

	public void run() {
		// TODO

		Instruction i = Instruction.createInstance("1", "network", "the hostname of the node", 0, "");
		Map<String, Object> r = new LinkedHashMap<>();
		r.put("hostName", "webserver1");
		r.put("domain", "local");
		r.put("fqdn", "webserver1.local");
		taskResultObserver.observe(i, 200, "OK", r);

	}
}
