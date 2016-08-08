package nl.agiletech.flow.project.types;

import java.io.IOException;

// TODO: extend Task so Inspectors are invoked correctly to compile Catalogs.

abstract class AbstractInspector<O> extends Task implements CanInspect<O> {
	public AbstractInspector() {
		super(false);
	}

	@Override
	public O render() throws IOException {
		return render(context);
	}

	public abstract O render(Context context) throws IOException;

	protected String getPlatformDependentScriptResourceName() throws PlatformNotSupportedException {
		String platform = context.getNode().getPlatform();
		String packageName = context.getConfigurationSettings().getSupportedPlatform(context.getNode().getPlatform(),
				"");
		if (packageName.isEmpty()) {
			throw new PlatformNotSupportedException(
					"the platform '" + platform + "' specified by node: " + context.getNode() + " is not supported");
		}
		return packageName + "." + getClass().getSimpleName() + ".sh";
	}

	@Override
	public String getVersion() {
		return "default";
	}

	protected String readScript() throws PlatformNotSupportedException {
		// try (InputStream inputStream =
		// getClass().getResourceAsStream(resourceName)) {
		// String script = FileUtil.readAsString(inputStream);
		// catalog.add(script);
		// }
		return getScriptResourceName();
	}
}
