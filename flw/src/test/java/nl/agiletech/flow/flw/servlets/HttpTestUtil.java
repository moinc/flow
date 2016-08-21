package nl.agiletech.flow.flw.servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpHandler;

import nl.agiletech.flow.flw.http.ResourceRouterHttpHandler;
import nl.agiletech.flow.flw.http.SimpleHttpServer;
import nl.agiletech.flow.flw.matchers.AllMatchers;
import nl.agiletech.flow.project.types.ConfigurationSettings;

public final class HttpTestUtil {
	public static ConfigurationSettings createConfigurationSettings(Class<?> clazz) throws IOException {
		try (InputStream src = clazz.getResourceAsStream("flowconfig.test.json")) {
			return ConfigurationSettings.loadFrom(src);
		}
	}

	public static HttpHandler createUpdateServletResourceRouter(ConfigurationSettings configurationSettings)
			throws ServletException {
		ResourceRouterHttpHandler resourceRouter = new ResourceRouterHttpHandler(configurationSettings);
		resourceRouter.addServlet(AllMatchers.createUpdateMatcher(), new UpdateServlet());
		return resourceRouter;
	}

	public static HttpHandler createResourceServletResourceRouter(ConfigurationSettings configurationSettings)
			throws ServletException {
		ResourceRouterHttpHandler resourceRouter = new ResourceRouterHttpHandler(configurationSettings);
		resourceRouter.addServlet(AllMatchers.createResourceMatcher(), new ResourceServlet());
		return resourceRouter;
	}

	public static SimpleHttpServer createHttpServer(ConfigurationSettings configurationSettings,
			HttpHandler rootHandler) throws Exception {
		SimpleHttpServer server = new SimpleHttpServer(configurationSettings, rootHandler);
		server.start(0);
		return server;
	}

	public static <T> T post(String url, Object requestBody, Class<T> responseType) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
			HttpPost request = new HttpPost(url);

			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "application/json");

			String json = objectMapper.writeValueAsString(requestBody);

			StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
			request.setEntity(requestEntity);

			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() >= 400) {
				// error
				System.out.println("http error: " + response.getStatusLine().getStatusCode() + " "
						+ response.getStatusLine().getReasonPhrase());
				throw new HttpException(response.getStatusLine().getReasonPhrase());
			}
			HttpEntity responseEntity = response.getEntity();

			try (InputStream inputStream = responseEntity.getContent()) {
				return objectMapper.readValue(inputStream, responseType);
			}
		}
	}
}
