package nl.agiletech.flow.flw.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import nl.agiletech.flow.project.types.ConfigurationSettings;

public class ResourceRouterHttpHandler implements HttpHandler {
	private static final Logger LOG = Logger.getLogger(ResourceRouterHttpHandler.class.getName());
	final ConfigurationSettings configurationSettings;
	Map<HttpHandler, HttpServlet> servlets = new HashMap<>();
	Map<ResourceMatcher, RootHttpHandler> handlers = new LinkedHashMap<>();

	public ResourceRouterHttpHandler(ConfigurationSettings configurationSettings) {
		super();
		this.configurationSettings = configurationSettings;
	}

	@Override
	public void handle(HttpExchange ex) throws IOException {
		assertValid();
		for (Entry<ResourceMatcher, RootHttpHandler> handlerEntry : handlers.entrySet()) {
			ResourceMatcher resourceMatcher = handlerEntry.getKey();
			Map<String, Object> data = new LinkedHashMap<>();
			if (resourceMatcher.match(ex, data)) {
				RootHttpHandler handler = handlerEntry.getValue();
				handler.handle(ex, data);
				return;
			}
		}

		HttpResponse resp = new HttpResponse(HttpAdapterUtil.createUnimplementAdapter(HttpServletResponse.class), ex);
		resp.sendError(HttpServletResponse.SC_NOT_FOUND, "resource not found");
		resp.complete();
	}

	// public void addHandler(ResourceMatcher matcher, HttpHandler handler) {
	// handlers.put(matcher, handler);
	// }

	public void addServlet(ResourceMatcher matcher, HttpServlet servlet) throws ServletException {
		ServletConfig servletConfig = new HttpServletConfig(servlet, configurationSettings);
		servlet.init(servletConfig);
		RootHttpHandler handler = new RootHttpHandler(servlet);
		servlets.put(handler, servlet);
		handlers.put(matcher, handler);
	}

	protected void assertValid() throws IOException {
		// validate
		if (handlers.isEmpty()) {
			// error
			throw new IOException("no handlers registered");
		}
	}

	protected void destroyHandlers() {
		for (HttpServlet servlet : servlets.values()) {
			servlet.destroy();
		}
	}
}
