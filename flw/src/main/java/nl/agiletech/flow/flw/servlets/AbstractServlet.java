package nl.agiletech.flow.flw.servlets;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.common.io.FileUtil;
import nl.agiletech.flow.flw.http.HttpServletConfig;
import nl.agiletech.flow.flw.matchers.AllMatchers;
import nl.agiletech.flow.flw.util.SessionIdUtil;
import nl.agiletech.flow.project.types.ConfigurationSettings;
import nl.agiletech.flow.project.types.NodeData;

@SuppressWarnings("serial")
public class AbstractServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(AbstractServlet.class.getName());

	private boolean initialized;
	private ConfigurationSettings configurationSettings;
	private boolean newSession;
	private String sessionId;
	private String projectName;
	private File projectRoot;
	private File projectFile;
	private NodeData nodeData;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		init(req, resp);
		super.service(req, resp);
	}

	private void init(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (!initialized) {
			loadConfigurationSettings();
			readSessionId(req);
			readProject(req, resp);
			readNodeData(req);
			initialized = true;
		}
	}

	private void loadConfigurationSettings() {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			if (configurationSettings == null) {
				// load config
				log.normal().append("read configuration file").print();
				configurationSettings = (getServletConfig() instanceof HttpServletConfig)
						? ((HttpServletConfig) getServletConfig()).getConfigurationSettings() : null;
			}
		}
	}

	private void readSessionId(HttpServletRequest req) {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			if (sessionId == null || sessionId.isEmpty()) {
				// get sessionId from url
				String sessionIdString = (String) req.getAttribute(AllMatchers.PROP_SESSIONID);
				if (sessionIdString == null || sessionIdString.isEmpty() || sessionIdString.equals("new")) {
					newSession = true;
					sessionId = SessionIdUtil.createSessionId();
					log.normal().append("session id: " + sessionId + " <-- new").print();
				} else {
					newSession = false;
					sessionId = sessionIdString;
					log.normal().append("session id: " + sessionId).print();
				}
			}
		}
	}

	private void readProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			// get project name from url, then expand using configuration
			// settings
			projectName = (String) req.getAttribute(AllMatchers.PROP_PROJECTNAME);
			if (projectName == null || projectName.isEmpty()) {
				log.error().append("bad project name").print();
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "bad projectname");
				return;
			}
			log.normal().append("project name: " + projectName).print();

			projectRoot = configurationSettings.getProjectRoot();
			if (projectRoot == null || !projectRoot.exists()) {
				log.error().append("bad configuration: projects.projectRoot: " + projectRoot.getAbsolutePath()).print();
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "bad configuration: projects.projectRoot");
				return;
			}
			log.normal().append("project root: " + projectRoot.getAbsolutePath()).print();

			projectFile = FileUtil.findFile(projectRoot, projectName, "jar");
			if (projectFile == null || !projectRoot.exists()) {
				log.error().append("project not found: " + projectName).print();
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "project not found: " + projectName);
				return;
			}
			log.normal().append("project file: " + projectFile.getAbsolutePath()).print();
		}
	}

	private void readNodeData(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			// the body of the request should have the node data
			log.normal().append("reading node data").print();
			nodeData = NodeData.loadFrom(req.getInputStream());
		}
	}

	private void assertInitialized() {
		if (!initialized) {
			throw new java.lang.IllegalStateException("not initialized");
		}
	}

	public ConfigurationSettings getConfigurationSettings() {
		assertInitialized();
		return configurationSettings;
	}

	public boolean isNewSession() {
		assertInitialized();
		return newSession;
	}

	public String getSessionId() {
		assertInitialized();
		return sessionId;
	}

	public String getProjectName() {
		assertInitialized();
		return projectName;
	}

	public File getProjectRoot() {
		assertInitialized();
		return projectRoot;
	}

	public File getProjectFile() {
		assertInitialized();
		return projectFile;
	}

	public NodeData getNodeData() {
		assertInitialized();
		return nodeData;
	}
}
