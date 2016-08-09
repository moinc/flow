package nl.agiletech.flow.cmp.compiler;

import java.io.File;

import org.junit.Test;

import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompiler;
import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompilerOptions;
import nl.agiletech.flow.project.types.ConfigurationSettings;
import nl.agiletech.flow.project.types.NodeData;
import nl.agiletech.flow.project.types.RequestType;

public class DefaultCompilerTest {

	static File getProjectRoot() {
		String currentDir = new File(".").getAbsolutePath();
		return new File(currentDir).getParentFile().getParentFile();
	}

	static File projectFile = new File(getProjectRoot(), "build/1.0/StarterProject/libs/StarterProject.jar");

	@Test
	public void testShouldCompileForWebserver1() throws Exception {
		ConfigurationSettings configurationSettings = ConfigurationSettings
				.loadFrom(getClass().getResourceAsStream("flowconfig.json"));
		NodeData nodeData = NodeData.loadFrom(getClass().getResourceAsStream("webserver1-nodedata.json"));

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

		DefaultCompilerOptions co = DefaultCompilerOptions.createInstance(configurationSettings, projectFile,
				RequestType.INSPECT, nodeData);
		Compiler comp = new DefaultCompiler(co);
		comp.compile();
	}
}
