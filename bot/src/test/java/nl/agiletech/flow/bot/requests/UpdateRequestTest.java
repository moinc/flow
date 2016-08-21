package nl.agiletech.flow.bot.requests;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import nl.agiletech.flow.bot.http.HttpServerTestUtil;
import nl.agiletech.flow.bot.http.TestServlet;
import nl.agiletech.flow.project.types.Catalog;
import nl.agiletech.flow.project.types.Instruction;

public class UpdateRequestTest {
	@Test
	public void testThatRequestReturnsCatalog() throws Exception {
		Catalog expectedCatalog = new Catalog();
		expectedCatalog.addTask(Instruction.createInstance("1", "a", "SomeTask", 123, "SomeResource"));

		String apiMethod = "/update";
		String sessionId = "SomeSession";
		String projectName = "SomeProject";

		TestServlet servlet = new TestServlet(apiMethod + "/" + sessionId + "/" + projectName, expectedCatalog);
		int port = HttpServerTestUtil.startServer(servlet);
		try {
			URI serviceURI = URI.create(HttpServerTestUtil.createURL(port, apiMethod));
			UpdateRequest request = new UpdateRequest(serviceURI, sessionId, projectName);
			Map<String, Object> data = new HashMap<>();
			Catalog actualCatalog = request.send(data);

			assertTrue(actualCatalog.compareTo(expectedCatalog) == 0);

		} finally {
			HttpServerTestUtil.stopServer(port);
		}
	}
}
