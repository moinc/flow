package nl.agiletech.flow.bot.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.AbstractHttpMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClient {
	private static final Logger LOG = Logger.getLogger(HttpClient.class.getName());
	private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

	public <T> T post(URI uri, Object requestBody, Class<T> responseType) throws IOException, HttpException {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
			HttpPost request = new HttpPost(uri);

			// add the default headers
			setHeaders(request, ContentType.APPLICATION_JSON);

			// serialize request body
			String json = OBJECTMAPPER.writeValueAsString(requestBody);
			StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
			request.setEntity(requestEntity);

			// send request
			HttpResponse response = trySendRequest(httpClient, request);

			// read response
			HttpEntity responseEntity = response.getEntity();
			try (InputStream inputStream = responseEntity.getContent()) {
				return OBJECTMAPPER.readValue(inputStream, responseType);
			}
		}
	}

	private HttpResponse trySendRequest(CloseableHttpClient httpClient, HttpPost request)
			throws IOException, ClientProtocolException, HttpException {
		HttpResponse response = httpClient.execute(request);

		int statusCode = response.getStatusLine().getStatusCode();

		// error cases
		if (statusCode >= HttpStatus.SC_BAD_REQUEST) {
			// error
			System.out.println("http error: " + response.getStatusLine().getStatusCode() + " "
					+ response.getStatusLine().getReasonPhrase());

			throw new HttpException(response.getStatusLine().getReasonPhrase());
		}

		return response;
	}

	private void setHeaders(AbstractHttpMessage request, ContentType contentType) {
		request.addHeader("content-type", contentType.getMimeType());
		request.addHeader("Accept", contentType.getMimeType());
	}
}
