package nl.agiletech.flow.flw.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import nl.agiletech.flow.project.types.ConfigurationSettings;

public class SimpleHttpServer {
	private static final Logger LOG = Logger.getLogger(SimpleHttpServer.class.getName());
	final ConfigurationSettings configurationSettings;
	final HttpHandler rootHandler;
	HttpServer server;
	HttpContext context;
	List<ServletContextListener> listeners = new ArrayList<>();

	public SimpleHttpServer(ConfigurationSettings configurationSettings, HttpHandler rootHandler) {
		assert configurationSettings != null && rootHandler != null;
		this.configurationSettings = configurationSettings;
		this.rootHandler = rootHandler;
	}

	public void start(int port) throws IOException, ServletException {
		LOG.info("--- http ---");
		InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
		server = HttpServer.create(inetSocketAddress, 0);
		context = server.createContext("/", rootHandler);
		onContextInitialized(context);
		server.setExecutor(null);
		server.start();
		LOG.info("http service endpoint: " + server.getAddress().toString());
	}

	public void stop(int secondsDelay) {
		LOG.info("stopping...");
		onContextDestroyed(context);
		server.stop(secondsDelay);
		LOG.info("http service stopped");
	}

	public void addListener(ServletContextListener listener) {
		listeners.add(listener);
	}

	public int getServerPort() {
		return server.getAddress().getPort();
	}

	private void onContextInitialized(HttpContext context) {
		HttpServletContext httpServletContext = new HttpServletContext(context);
		for (ServletContextListener listener : listeners) {
			ServletContextEvent sce = new ServletContextEvent(httpServletContext);
			listener.contextInitialized(sce);
		}
	}

	private void onContextDestroyed(HttpContext context) {
		HttpServletContext httpServletContext = new HttpServletContext(context);
		for (ServletContextListener listener : listeners) {
			ServletContextEvent sce = new ServletContextEvent(httpServletContext);
			listener.contextDestroyed(sce);
		}
	}
}