package nl.agiletech.flow.project.types;

import java.io.OutputStream;
import java.io.PrintWriter;

public abstract class Component extends AbstractComponent {
	@Override
	public void initialize(Context context) {
	}

	@Override
	public void inspect(Context context, Catalog catalog) {
		catalog.add("which " + getPackageName());
	}

	@Override
	public void update(Context context, Catalog catalog) {
	}

	@Override
	public void resource(Context context, OutputStream outputStream) {
	}

	@Override
	public void report(Context context, PrintWriter printWriter) {
	}

	@Override
	public void terminate(Context context) {
	}
}
