package nl.agiletech.flow.cmp.compiler;

import java.io.File;

import org.junit.Test;

import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompiler;
import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompilerOptions;
import nl.agiletech.flow.project.types.ConfigurationSettings;
import nl.agiletech.flow.project.types.NodeData;
import nl.agiletech.flow.project.types.RequestType;

public class DefaultCompilerTest {
	@Test
	public void testShouldCompileForWebserver1() throws Exception {
		ConfigurationSettings configurationSettings = ConfigurationSettings
				.loadFrom(getClass().getResourceAsStream("flowconfig.json"));
		NodeData nodeData = NodeData.loadFrom(getClass().getResourceAsStream("webserver1-nodedata.json"));

		File projectFile = new File(
				"/Users/moincreemers/Documents/Projecten/INTERNAL/flow/build/1.0/StarterProject/libs/StarterProject.jar");

		DefaultCompilerOptions co = DefaultCompilerOptions.createInstance(configurationSettings, projectFile,
				RequestType.INSPECT, nodeData);
		Compiler comp = new DefaultCompiler(co);
		comp.compile();
	}

	@Test
	public void testShouldCompileForWebserver2() throws Exception {
		ConfigurationSettings configurationSettings = ConfigurationSettings
				.loadFrom(getClass().getResourceAsStream("flowconfig.json"));
		NodeData nodeData = NodeData.loadFrom(getClass().getResourceAsStream("webserver2-nodedata.json"));

		File projectFile = new File(
				"/Users/moincreemers/Documents/Projecten/INTERNAL/flow/build/1.0/StarterProject/libs/StarterProject.jar");

		DefaultCompilerOptions co = DefaultCompilerOptions.createInstance(configurationSettings, projectFile,
				RequestType.INSPECT, nodeData);
		Compiler comp = new DefaultCompiler(co);
		comp.compile();
	}

	@Test(expected = CompileException.class)
	public void testShouldNotCompileForUnknownServer() throws Exception {
		ConfigurationSettings configurationSettings = ConfigurationSettings
				.loadFrom(getClass().getResourceAsStream("flowconfig.json"));
		NodeData nodeData = NodeData.loadFrom(getClass().getResourceAsStream("unknown-nodedata.json"));

		File projectFile = new File(
				"/Users/moincreemers/Documents/Projecten/INTERNAL/flow/build/1.0/StarterProject/libs/StarterProject.jar");

		DefaultCompilerOptions co = DefaultCompilerOptions.createInstance(configurationSettings, projectFile,
				RequestType.INSPECT, nodeData);
		Compiler comp = new DefaultCompiler(co);
		comp.compile();
	}
}
