/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import nl.agiletech.flow.common.reflect.ClassUtil;

public abstract class Task implements TakesContext {
	protected Context context;
	private final boolean repeatable;
	private final List<Condition> preConditions = new ArrayList<>();
	private final List<Condition> postConditions = new ArrayList<>();
	private final List<Dependency> dependencies = new ArrayList<>();

	public Task(boolean repeatable) {
		this.repeatable = repeatable;
	}

	@Override
	public void setContext(Context context) {
		assert context != null;
		this.context = context;
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
	 * Returns a dependency to this task.
	 * 
	 * @return a dependency
	 */
	public Dependency asDependency() {
		return Dependency.get(getClassName(), getVersion());
	}

	/**
	 * True if this task can be safely repeated.
	 * 
	 * @return
	 */
	public final boolean isRepeatable() {
		return repeatable;
	}

	protected void addPreCondition(Condition... conditions) {
		assert conditions != null;
		for (Condition condition : conditions) {
			preConditions.add(condition);
		}
	}

	protected void addPostCondition(Condition... conditions) {
		assert conditions != null;
		for (Condition condition : conditions) {
			postConditions.add(condition);
		}
	}

	protected void addDependency(Dependency... dependencies) {
		assert dependencies != null;
		for (Dependency dependency : dependencies) {
			this.dependencies.add(dependency);
		}
	}

	protected void addDependencyForAnyVersion(String... dependencyNames) {
		assert dependencyNames != null;
		for (String dependencyName : dependencyNames) {
			this.dependencies.add(Dependency.getForAnyVersion(dependencyName));
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
