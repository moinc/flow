package nl.agiletech.flow.bot.http;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

public final class HttpServerTestUtil {
	private static final Map<Integer, SimpleHttpServer> servers = new HashMap<>();

	public static String createURL(int port, String path) {
		return "http://localhost:" + port + (path != null ? path : "");
	}

	public static int startServer(HttpServlet servlet) throws Exception {
		RootHttpHandler handler = new RootHttpHandler(servlet);
		SimpleHttpServer server = new SimpleHttpServer(handler);
		server.start(0);
		servers.put(server.getServerPort(), server);
		return server.getServerPort();
	}

	public static void stopServer(int port) {
		SimpleHttpServer server = servers.get(port);
		servers.remove(port);
		server.stop(0);
	}

	private HttpServerTestUtil() {
		// prevent instantiation
	}
}
