package nl.agiletech.flow.flw.servlets;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.agiletech.flow.common.cli.CliException;
import nl.agiletech.flow.flw.http.ResourceRouterHttpHandler;
import nl.agiletech.flow.flw.http.SimpleHttpServer;
import nl.agiletech.flow.flw.matchers.AllMatchers;
import nl.agiletech.flow.project.types.ConfigurationSettings;
import nl.agiletech.flow.project.types.NodeData;

public class ResourceServletTest {
	@Test
	public void testShouldRespondToResourceRequest() throws Exception {
		// try (InputStream nodeDataInputStream =
		// getClass().getResourceAsStream("webserver1-nodedata.json")) {
		// NodeData nodeData = NodeData.loadFrom(nodeDataInputStream);
		// Map<String, Object> requestBody = nodeData.getValues();
		// SimpleHttpServer server = createHttpServer();
		// try {
		// String url = "http://localhost:" + server.getServerPort() +
		// "/update";
		// Map response = post(url, requestBody, Map.class);
		// for (Object key : response.keySet()) {
		// System.out.println("key: " + key);
		// }
		// } finally {
		// server.stop(0);
		// }
		// }
	}

}
