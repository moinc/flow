package nl.agiletech.flow.flw.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HttpServletTest extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(HttpServletTest.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.info("get " + req.getRequestURL().toString());
		resp.getOutputStream().write("Hello world!".getBytes(StandardCharsets.UTF_8));
		resp.setStatus(200);
	}
}
