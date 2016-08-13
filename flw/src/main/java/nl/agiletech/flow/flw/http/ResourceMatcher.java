package nl.agiletech.flow.flw.http;

import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public interface ResourceMatcher {
	boolean match(HttpExchange ex, Map<String, Object> result);
}
