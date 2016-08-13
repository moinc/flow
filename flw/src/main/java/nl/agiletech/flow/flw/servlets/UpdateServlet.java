package nl.agiletech.flow.flw.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.agiletech.flow.cmp.compiler.CompileException;
import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompiler;
import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompilerOptions;
import nl.agiletech.flow.cmp.exec.ProjectExecutor;
import nl.agiletech.flow.common.io.FileUtil;
import nl.agiletech.flow.common.io.TempFile;
import nl.agiletech.flow.flw.http.HttpServletConfig;
import nl.agiletech.flow.flw.matchers.AllMatchers;
import nl.agiletech.flow.project.types.ConfigurationSettings;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.NodeData;
import nl.agiletech.flow.project.types.RequestType;

@SuppressWarnings("serial")
public class UpdateServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(UpdateServlet.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.info("processing update request...");
		RequestType requestType = RequestType.UPDATE;

		// load config
		ConfigurationSettings configurationSettings = (getServletConfig() instanceof HttpServletConfig)
				? ((HttpServletConfig) getServletConfig()).getConfigurationSettings() : null;

		// get project name from url, then expand using configuration settings
		String projectName = (String) req.getAttribute(AllMatchers.PROP_PROJECTNAME);
		File projectRoot = configurationSettings.getProjectRoot();
		File projectFile = FileUtil.findFile(projectRoot, projectName, "jar");

		// the body of the request should have the node data
		NodeData nodeData = NodeData.loadFrom(req.getInputStream());

		try {
			// compile project
			DefaultCompilerOptions compileOptions = DefaultCompilerOptions.createInstance(configurationSettings,
					projectFile, requestType, nodeData);
			Context context = DefaultCompiler.createInstance(compileOptions).compile();

			// execute the project
			File temp = TempFile.create();
			try {
				try (OutputStream output = new FileOutputStream(temp)) {
					ProjectExecutor.createInstance(output, context).run();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} finally {
				// delete the temp file
				temp.delete();
			}

		} catch (CompileException e) {
			throw new ServletException(e);
		}
	}
}
