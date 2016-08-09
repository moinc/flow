/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import nl.agiletech.flow.common.io.FileUtil;

// TODO: extend Task so Inspectors are invoked correctly to compile Catalogs.

abstract class AbstractInspector<O> extends Task implements CanInspect<O> {
	private static final Logger LOG = Logger.getLogger(AbstractInspector.class.getName());

	public AbstractInspector() {
		super(false);
	}

	@Override
	public O render() throws IOException {
		return render(context);
	}

	public abstract O render(Context context) throws IOException;

	@Override
	public String getVersion() {
		return "default";
	}

	@Override
	public String getScriptResourceName() {
		return getClass().getSimpleName() + ".sh";
	}

	protected String readScript() throws PlatformNotSupportedException {
		String resourceName = getScriptResourceName();
		try (InputStream inputStream = getPlatform().getClass().getResourceAsStream(resourceName)) {
			if (inputStream == null) {
				throw new FileNotFoundException("missing resource: " + resourceName);
			}
			return FileUtil.readAsString(inputStream, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new PlatformNotSupportedException("failed to read resource: " + resourceName, e);
		}
	}

	private Platform getPlatform() throws PlatformNotSupportedException {
		Platform platform = context.getPlatform();
		if (platform == null) {
			throw new PlatformNotSupportedException(
					"the platform '" + platform + "' specified by node: " + context.getNode() + " is not supported");
		}
		return platform;
	}
}
