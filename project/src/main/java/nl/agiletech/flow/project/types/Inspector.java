package nl.agiletech.flow.project.types;

import java.io.OutputStream;
import java.io.PrintWriter;

public abstract class Inspector<O> extends AbstractInspector<O> {
	@Override
	public void initialize(Context context) throws Exception {
	}

	@Override
	public void inspect(Context context, Catalog catalog) throws Exception {
		catalog.add(readScript());
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

	@Override
	public String getScriptResourceName() throws PlatformNotSupportedException {
		return getPlatformDependentScriptResourceName();
	}

}
