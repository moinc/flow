/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.compiler.builtin;

import java.io.File;

import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.project.types.ConfigurationSettings;
import nl.agiletech.flow.project.types.NodeData;
import nl.agiletech.flow.project.types.RequestType;

public class DefaultCompilerOptions {
	final ConfigurationSettings configurationSettings;
	final File projectFile;
	final RequestType requestType;
	final NodeData nodeData;

	public static DefaultCompilerOptions createInstance(ConfigurationSettings configurationSettings, File projectFile,
			RequestType requestType, NodeData nodeData) {
		return new DefaultCompilerOptions(configurationSettings, projectFile, requestType, nodeData);
	}

	private DefaultCompilerOptions(ConfigurationSettings configurationSettings, File projectFile,
			RequestType requestType, NodeData nodeData) {
		super();
		Assertions.notNull(configurationSettings, "configurationSettings");
		Assertions.notNull(projectFile, "projectFile");
		Assertions.notNull(requestType, "requestType");
		Assertions.notNull(nodeData, "nodeData");
		this.configurationSettings = configurationSettings;
		this.projectFile = projectFile;
		this.requestType = requestType;
		this.nodeData = nodeData;
	}

}
