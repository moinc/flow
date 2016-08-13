package nl.agiletech.flow.flw.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import nl.agiletech.flow.common.io.IOUtil;
import nl.agiletech.flow.project.types.ConfigurationSettings;

public class SimpleHttpServerTest {
	@Test
	public void testShouldServerRespond() throws Exception {
		final Map<String, String> data = new HashMap<>();

		class TestHandler extends HttpTestHandler {
			@Override
			public void handle(HttpRequest req, HttpResponse resp) throws IOException {
				String responseText = data.get(req.getRequestURL().toString());
				resp.getOutputStream().write(responseText.getBytes(StandardCharsets.UTF_8));
				resp.setStatus(200);
				resp.complete();
			}

		}

		ConfigurationSettings configurationSettings = ConfigurationSettings.EMPTY;
		SimpleHttpServer server = new SimpleHttpServer(configurationSettings, new TestHandler());

		// start server at any free port
		server.start(0);

		try {
			int port = server.getServerPort();

			// send a request

			String path = "/";
			String expectedResponseText = "Hello world!";
			data.put(path, expectedResponseText);

			URL req = new URL("http://localhost:" + port + path);
			byte[] response = IOUtil.copy(req.openStream());
			String actualResponseText = new String(response, StandardCharsets.UTF_8);

			assertTrue("expected requested path to be processed by test servlet", data.containsKey(path));
			assertEquals("expected correct response", expectedResponseText, actualResponseText);

		} finally {
			server.stop(0);
		}
	}
}
