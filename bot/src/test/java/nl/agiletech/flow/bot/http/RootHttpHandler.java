package nl.agiletech.flow.bot.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import nl.agiletech.flow.common.util.Assertions;

public class RootHttpHandler implements HttpHandler {
	private static final Logger LOG = Logger.getLogger(RootHttpHandler.class.getName());
	HttpServlet servlet;

	public RootHttpHandler(HttpServlet servlet) {
		Assertions.notNull(servlet, "servlet");
		this.servlet = servlet;
	}

	@Override
	public void handle(final HttpExchange ex) throws IOException {
		LOG.info(ex.getProtocol() + " " + ex.getRequestMethod() + " " + ex.getRequestURI().getPath());
		switch (ex.getRequestMethod()) {
		case "GET":
			handleGet(ex, new HashMap<String, Object>());
			break;
		case "POST":
			handlePost(ex, new HashMap<String, Object>());
			break;
		}
	}

	public void handle(HttpExchange ex, Map<String, Object> resourceData) throws IOException {
		LOG.info(ex.getProtocol() + " " + ex.getRequestMethod() + " " + ex.getRequestURI().getPath());
		switch (ex.getRequestMethod()) {
		case "GET":
			handleGet(ex, resourceData);
			break;
		case "POST":
			handlePost(ex, resourceData);
			break;
		}
	}

	private void handleGet(final HttpExchange ex, Map<String, Object> resourceData) throws IOException {
		HttpRequest req = new HttpRequest(HttpAdapterUtil.createUnimplementAdapter(HttpServletRequest.class), ex);
		HttpResponse resp = new HttpResponse(HttpAdapterUtil.createUnimplementAdapter(HttpServletResponse.class), ex);
		copyResourceDataToRequest(resourceData, req);
		try {
			LOG.info("dispatching...");
			servlet.service(req, resp);
			resp.complete();
		} catch (ServletException e) {
			throw new IOException(e);
		}
	}

	private void handlePost(final HttpExchange ex, Map<String, Object> resourceData) throws IOException {
		HttpRequest req = new HttpRequest(HttpAdapterUtil.createUnimplementAdapter(HttpServletRequest.class), ex);
		HttpResponse resp = new HttpResponse(HttpAdapterUtil.createUnimplementAdapter(HttpServletResponse.class), ex);
		copyResourceDataToRequest(resourceData, req);
		try {
			LOG.info("dispatching...");
			servlet.service(req, resp);
			resp.complete();
		} catch (ServletException e) {
			throw new IOException(e);
		}
	}

	private void copyResourceDataToRequest(Map<String, Object> resourceData, HttpRequest req) {
		for (String dataKey : resourceData.keySet()) {
			req.setAttribute(dataKey, resourceData.get(dataKey));
		}
	}

}
