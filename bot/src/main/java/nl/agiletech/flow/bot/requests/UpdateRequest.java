package nl.agiletech.flow.bot.requests;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.HttpException;

import nl.agiletech.flow.bot.httpclient.HttpClient;
import nl.agiletech.flow.common.cli.logging.ConsoleUtil;
import nl.agiletech.flow.project.types.Catalog;

public class UpdateRequest {
	private static final Logger LOG = Logger.getLogger(UpdateRequest.class.getName());
	private static final String SESSIONID_NEW = "new";
	final URI serviceURI;
	final String sessionId;
	final String projectName;

	public UpdateRequest(URI serviceURI, String sessionId, String projectName) {
		this.serviceURI = serviceURI;
		this.sessionId = createCorrectSessionId(sessionId);
		this.projectName = projectName;
	}

	private String createCorrectSessionId(String sessionId) {
		if (sessionId == null || sessionId.isEmpty()) {
			return SESSIONID_NEW;
		}
		return sessionId;
	}

	private URI createRequestURI() {
		String url = serviceURI.toString();
		url += "/" + sessionId;
		url += "/" + projectName;
		URI requestURI = URI.create(url);
		return requestURI;
	}

	public Catalog send(Map<String, Object> requestBody) throws URISyntaxException, IOException, HttpException {
		try (ConsoleUtil log = ConsoleUtil.OUT.withLogger(LOG)) {
			URI requestURI = createRequestURI();
			log.normal().append("request: " + requestURI);
			log.normal().append("nodedata: ").print();
			for (String key : requestBody.keySet()) {
				Object value = requestBody.get(key);
				if (value == null) {
					value = "<null>";
				}
				log.normal().append("  " + key + " = " + value).print();
			}
			HttpClient httpClient = new HttpClient();
			return httpClient.post(requestURI, requestBody, Catalog.class);
		}
	}
}
