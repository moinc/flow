/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import nl.agiletech.flow.common.io.FileUtil;
import nl.agiletech.flow.common.io.MD5CheckSum;
import nl.agiletech.flow.project.aspects.OwnershipAspect;
import nl.agiletech.flow.project.aspects.PermissionsAspect;

abstract class AbstractFile extends Task {
	private static final Logger LOG = Logger.getLogger(AbstractFile.class.getName());
	private static final String DEFAULT_VERSION = "default";
	public final OwnershipAspect ownership = OwnershipAspect.OPTIONAL;
	public final PermissionsAspect permissions = PermissionsAspect.OPTIONAL;

	public AbstractFile() {
		super(false);
	}

	String getFileHash() {
		String source = getSource();
		LOG.fine("checksum on source: " + source);
		if (source == null || source.isEmpty()) {
			return DEFAULT_VERSION;
		}
		java.io.File fileRoot = context.getConfigurationSettings().getFileRoot();
		LOG.fine("  file root: " + fileRoot.getAbsolutePath());
		java.io.File file = FileUtil.toFile(fileRoot, source);
		LOG.fine("  source file: " + file.getAbsolutePath());
		LOG.fine("  source file exists: " + file.exists());
		if (!file.exists()) {
			return DEFAULT_VERSION;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(getDestination());
		sb.append(ownership.toString());
		sb.append(permissions.toString());
		byte[] additionalBytes = sb.toString().getBytes(Charset.forName("utf-8"));
		LOG.fine("  additional bytes: " + additionalBytes.length);
		try {
			return MD5CheckSum.createChecksumHex(file, additionalBytes);
		} catch (NoSuchAlgorithmException | IOException e) {
			LOG.severe(
					"failed to calulate checksum for file: " + file.getAbsolutePath() + "\nerror: " + e.getMessage());
		}
		return DEFAULT_VERSION;
	}

	@Override
	public String getVersion() {
		return getFileHash();
	}

	/**
	 * The file name. The subclass must return either an absolute or relative
	 * path. If the path is relative, the
	 * {@link ConfigurationSettings.BuiltIn.FILEROOT} is used as the base path.
	 * 
	 * @return a file
	 */
	public abstract String getSource();

	/**
	 * The path of the file on the node.
	 * 
	 * @return a path
	 */
	public abstract String getDestination();

	/**
	 * Returns a dependency to this file.
	 * 
	 * @return a dependency
	 */
	@Override
	public Dependency asDependency() {
		return Dependency.get(getSource() + " ---> " + getDestination(), getVersion());
	}
}
