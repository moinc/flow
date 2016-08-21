package nl.agiletech.flow.bot.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import nl.agiletech.flow.common.util.Assertions;

public class SimpleHttpServer {
	private static final Logger LOG = Logger.getLogger(SimpleHttpServer.class.getName());
	final HttpHandler handler;
	HttpServer server;
	HttpContext context;

	public SimpleHttpServer(HttpHandler handler) {
		Assertions.notNull(handler, "handler");
		this.handler = handler;
	}

	public void start(int port) throws IOException {
		LOG.info("--- http ---");
		InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
		server = HttpServer.create(inetSocketAddress, 0);
		context = server.createContext("/", handler);
		server.setExecutor(null);
		server.start();
		LOG.info("http service endpoint: " + server.getAddress().toString());
	}

	public void stop(int secondsDelay) {
		LOG.info("stopping...");
		server.stop(secondsDelay);
		LOG.info("http service stopped");
	}

	public int getServerPort() {
		return server.getAddress().getPort();
	}
}