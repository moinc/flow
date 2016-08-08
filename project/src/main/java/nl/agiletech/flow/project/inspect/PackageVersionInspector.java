package nl.agiletech.flow.project.inspect;

import java.io.IOException;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

public class PackageVersionInspector extends Inspector<String> {
	final String packageName;

	public PackageVersionInspector(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (String) context.getNodeData().get(key, "");
	}
}
