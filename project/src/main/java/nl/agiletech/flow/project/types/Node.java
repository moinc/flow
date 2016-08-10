/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.OutputStream;
import java.io.PrintWriter;

public abstract class Node extends AbstractNode {
	@Override
	public void initialize(Context context) throws Exception {
	}

	@Override
	public void inspect(Context context, Catalog catalog) throws Exception {
	}

	@Override
	public void update(Context context, Catalog catalog) throws Exception {
	}

	@Override
	public void resource(Context context, OutputStream outputStream) throws Exception {
	}

	@Override
	public void report(Context context, PrintWriter printWriter) throws Exception {
	}

	@Override
	public void terminate(Context context) throws Exception {
	}
}
