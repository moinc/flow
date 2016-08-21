/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import nl.agiletech.flow.common.reflect.ClassUtil;
import nl.agiletech.flow.common.util.Assertions;

/**
 * Task is the base class for all classes that express something that 'needs to
 * be done'.
 * 
 * @author moincreemers
 *
 */
public abstract class Task implements TakesContext, TakesCondition, HasAttributes {
	protected Context context;
	private final boolean repeatable;
	private final List<Requirement> dependencies = new ArrayList<>();
	private Condition condition = Condition.TRUE;
	private final Attributes attributes = new Attributes();

	public Task(boolean repeatable) {
		this.repeatable = repeatable;
	}

	@Override
	public void setContext(Context context) {
		Assertions.notNull(context, "context");
		this.context = context;
		this.condition.setContext(context);
	}

	@Override
	public void setCondition(Condition condition) {
		Assertions.notNull(condition, "condition");
		this.condition = condition;
		if (context != null) {
			this.condition.setContext(context);
		}
	}

	@Override
	public boolean isEnabled() {
		return condition.eval();
	}

	@Override
	public Attributes getAttributes() {
		return attributes;
	}

	public String getClassName() {
		return ClassUtil.getClassName(getClass());
	}

	/**
	 * Returns a string that expresses the version of this task.
	 * 
	 * @return A string
	 */
	public abstract String getVersion();

	/**
	 * Returns a {@link Requirement} to this task instance.
	 * 
	 * @return a requirement
	 */
	public Requirement asRequirement() {
		return Requirement.get(getClassName(), getVersion());
	}

	/**
	 * True if this task can be safely repeated.
	 * 
	 * @return
	 */
	public final boolean isRepeatable() {
		return repeatable;
	}

	protected void addDependency(Requirement... dependencies) {
		Assertions.notNull(dependencies, "dependencies");
		for (Requirement dependency : dependencies) {
			this.dependencies.add(dependency);
		}
	}

	protected void addDependencyForAnyVersion(String... dependencyNames) {
		Assertions.notNull(dependencyNames, "dependencyNames");
		for (String dependencyName : dependencyNames) {
			this.dependencies.add(Requirement.getForAnyVersion(dependencyName));
		}
	}

	/**
	 * Performs any initialization needed to execute the request on this task.
	 */
	public abstract void initialize(Context context) throws Exception;

	/**
	 * Performs the inspect phase. This involves compiling a 'catalog' of tasks
	 * that the node needs to execute. Each task will output a value which is
	 * used in the update phase.
	 * 
	 * @param context
	 * @param catalog
	 */
	public abstract void inspect(Context context, Catalog catalog) throws Exception;

	/**
	 * Performs the update phase. This involves compiling a 'catalog' of tasks
	 * that the node needs to execute.
	 * 
	 * @param context
	 * @param catalog
	 */
	public abstract void update(Context context, Catalog catalog) throws Exception;

	/**
	 * Returns a resource to the node.
	 * 
	 * @param context
	 * @param outputStream
	 */
	public abstract void resource(Context context, OutputStream outputStream) throws Exception;

	/**
	 * Handles reporting information provided by the node.
	 * 
	 * @param context
	 * @param printWriter
	 */
	public abstract void report(Context context, PrintWriter printWriter) throws Exception;

	/**
	 * Performs any termination after executing the request on this task.
	 */
	public abstract void terminate(Context context) throws Exception;
}
