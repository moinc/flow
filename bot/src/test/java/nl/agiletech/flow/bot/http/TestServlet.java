package nl.agiletech.flow.bot.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	static final ObjectMapper OBJECTMAPPER = new ObjectMapper();
	final String testRequestURL;
	final Object responseBody;

	public TestServlet(String testRequestURL, Object responseBody) {
		this.testRequestURL = testRequestURL;
		this.responseBody = responseBody;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURL = req.getRequestURL().toString();
		if (testRequestURL.equals(requestURL)) {
			OBJECTMAPPER.writeValue(resp.getOutputStream(), responseBody);
		} else {
			resp.sendError(500, "request did not match test url: " + testRequestURL + " but was: " + requestURL);
		}
	}

}
