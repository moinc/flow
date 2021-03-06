package nl.agiletech.flow.flw.servlets;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.net.httpserver.HttpHandler;

import nl.agiletech.flow.flw.http.SimpleHttpServer;
import nl.agiletech.flow.project.types.ConfigurationSettings;
import nl.agiletech.flow.project.types.NodeData;

public class UpdateServletTest {
	@Test(expected = HttpException.class)
	public void testShouldRespondWithResourceMatchErrorIfNoSessionAndProjectSpecified() throws Exception {
		ConfigurationSettings configurationSettings = HttpTestUtil.createConfigurationSettings(getClass());
		HttpHandler handler = HttpTestUtil.createUpdateServletResourceRouter(configurationSettings);
		try (InputStream nodeDataInputStream = getClass().getResourceAsStream("webserver1-nodedata.json")) {
			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("dummy", "some value");
			SimpleHttpServer server = HttpTestUtil.createHttpServer(configurationSettings, handler);
			try {
				String url = "http://localhost:" + server.getServerPort() + "/update";
				HttpTestUtil.post(url, requestBody, Map.class);
			} finally {
				server.stop(0);
			}
		}
	}

	@Test(expected = HttpException.class)
	public void testShouldRespondWithResourceMatchErrorIfNoProjectSpecified() throws Exception {
		ConfigurationSettings configurationSettings = HttpTestUtil.createConfigurationSettings(getClass());
		HttpHandler handler = HttpTestUtil.createUpdateServletResourceRouter(configurationSettings);
		try (InputStream nodeDataInputStream = getClass().getResourceAsStream("webserver1-nodedata.json")) {
			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("dummy", "some value");
			SimpleHttpServer server = HttpTestUtil.createHttpServer(configurationSettings, handler);
			try {
				String url = "http://localhost:" + server.getServerPort() + "/update//SomeProject";
				HttpTestUtil.post(url, requestBody, Map.class);
			} finally {
				server.stop(0);
			}
		}
	}

	@Test(expected = HttpException.class)
	public void testShouldRespondWithProjectNotFoundError() throws Exception {
		ConfigurationSettings configurationSettings = HttpTestUtil.createConfigurationSettings(getClass());
		HttpHandler handler = HttpTestUtil.createUpdateServletResourceRouter(configurationSettings);
		try (InputStream nodeDataInputStream = getClass().getResourceAsStream("webserver1-nodedata.json")) {
			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("dummy", "some value");
			SimpleHttpServer server = HttpTestUtil.createHttpServer(configurationSettings, handler);
			try {
				String url = "http://localhost:" + server.getServerPort() + "/update/new/nonexistantproject";
				HttpTestUtil.post(url, requestBody, Map.class);
			} finally {
				server.stop(0);
			}
		}
	}

	@Test
	@SuppressWarnings("rawtypes")
	@Ignore
	public void testShouldRespondToUpdateRequest() throws Exception {
		ConfigurationSettings configurationSettings = HttpTestUtil.createConfigurationSettings(getClass());
		HttpHandler handler = HttpTestUtil.createUpdateServletResourceRouter(configurationSettings);
		try (InputStream nodeDataInputStream = getClass().getResourceAsStream("webserver1-nodedata.json")) {
			NodeData nodeData = NodeData.loadFrom(nodeDataInputStream);
			Map<String, Object> requestBody = nodeData.getValues();
			SimpleHttpServer server = HttpTestUtil.createHttpServer(configurationSettings, handler);
			try {
				String url = "http://localhost:" + server.getServerPort() + "/update/new/StarterProject";
				Map catalog = HttpTestUtil.post(url, requestBody, Map.class);

				assertTrue("expected response to contain root field: 'instructions'",
						catalog.containsKey("instructions"));

				Map instructions = (Map) catalog.get("instructions");

				for (Object key : instructions.keySet()) {
					Object instruction = instructions.get(key);
					System.out.println("instruction: " + key + "--->" + instruction);
				}
			} finally {
				server.stop(0);
			}
		}
	}

}
