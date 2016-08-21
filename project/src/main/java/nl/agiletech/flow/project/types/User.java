package nl.agiletech.flow.project.types;

import java.io.OutputStream;
import java.io.PrintWriter;

public class User extends Task {
	public User(boolean repeatable) {
		super(false);
	}

	@Override
	public String getVersion() {
		return null;
	}

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
