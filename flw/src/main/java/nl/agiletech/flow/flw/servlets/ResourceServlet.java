package nl.agiletech.flow.flw.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;

import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompiler;
import nl.agiletech.flow.cmp.compiler.builtin.DefaultCompilerOptions;
import nl.agiletech.flow.cmp.exec.ProjectExecutor;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.RequestType;

@SuppressWarnings("serial")
public class ResourceServlet extends AbstractServlet {
	private static final Logger LOG = Logger.getLogger(ResourceServlet.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			log.faint().append("--- processing request ---").print();
			log.normal().append("request url: " + req.getRequestURL().toString()).print();

			RequestType requestType = RequestType.RESOURCE;

			try {
				// compile project
				DefaultCompilerOptions compileOptions = DefaultCompilerOptions
						.createInstance(getConfigurationSettings(), getProjectFile(), requestType, getNodeData());
				Context context = DefaultCompiler.createInstance(compileOptions).compile();

				// set mime type
				resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());

				// execute the project
				ProjectExecutor.createInstance(resp.getOutputStream(), context).run();

				resp.setStatus(200);
			} catch (Exception e) {
				log.error().append(e.getMessage()).print();
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			} finally {
				log.faint().append("\n--- done ---").print();
			}
		}
	}
}
