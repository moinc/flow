package nl.agiletech.flow.flw.http;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class HttpTestHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange ex) throws IOException {
		HttpRequest req = new HttpRequest(HttpAdapterUtil.createUnimplementAdapter(HttpServletRequest.class), ex);
		HttpResponse resp = new HttpResponse(HttpAdapterUtil.createUnimplementAdapter(HttpServletResponse.class), ex);
		handle(req, resp);
	}

	public abstract void handle(HttpRequest req, HttpResponse resp) throws IOException;
}
