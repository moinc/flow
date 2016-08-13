package nl.agiletech.flow.flw.servlets;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;

import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompiler;
import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompilerOptions;
import nl.agiletech.flow.cmp.exec.ProjectExecutor;
import nl.agiletech.flow.common.io.FileUtil;
import nl.agiletech.flow.flw.http.HttpServletConfig;
import nl.agiletech.flow.flw.matchers.AllMatchers;
import nl.agiletech.flow.project.types.ConfigurationSettings;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.NodeData;
import nl.agiletech.flow.project.types.RequestType;

@SuppressWarnings("serial")
public class InspectServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(InspectServlet.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.info("processing update request...");
		RequestType requestType = RequestType.INSPECT;

		// load config
		ConfigurationSettings configurationSettings = (getServletConfig() instanceof HttpServletConfig)
				? ((HttpServletConfig) getServletConfig()).getConfigurationSettings() : null;

		// get project name from url, then expand using configuration settings
		String projectName = (String) req.getAttribute(AllMatchers.PROP_PROJECTNAME);
		if (projectName == null || projectName.isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "bad projectname");
			return;
		}

		File projectRoot = configurationSettings.getProjectRoot();
		if (projectRoot == null || !projectRoot.exists()) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "bad configuration: projects.projectRoot");
			return;
		}

		File projectFile = FileUtil.findFile(projectRoot, projectName, "jar");
		if (projectFile == null || !projectRoot.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "project not found: " + projectName);
			return;
		}

		// the body of the request should have the node data
		NodeData nodeData = NodeData.loadFrom(req.getInputStream());

		try {
			// compile project
			DefaultCompilerOptions compileOptions = DefaultCompilerOptions.createInstance(configurationSettings,
					projectFile, requestType, nodeData);
			Context context = DefaultCompiler.createInstance(compileOptions).compile();

			// set mime type
			resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());

			// execute the project
			ProjectExecutor.createInstance(resp.getOutputStream(), context).run();

			resp.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
