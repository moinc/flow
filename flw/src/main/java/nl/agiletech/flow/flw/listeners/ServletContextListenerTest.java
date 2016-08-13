package nl.agiletech.flow.flw.listeners;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextListenerTest implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(ServletContextListenerTest.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.info("START");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOG.info("STOP");
	}
}
