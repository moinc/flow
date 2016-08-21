package nl.agiletech.flow.flw.http;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.sun.net.httpserver.HttpExchange;

import nl.agiletech.flow.common.util.Assertions;

/**
 * @author moincreemers
 *
 */
public class DefaultResourceMatcher implements ResourceMatcher {
	String matchMethod = null;
	final List<Map<String, String>> matchSegments = new ArrayList<>();

	public DefaultResourceMatcher() {
	}

	public void setMatchMethod(String matchMethod) {
		this.matchMethod = matchMethod;
	}

	/**
	 * For every expected segment in the requested URL - from left to right -
	 * call this method and pass a LinkedHashMap with expressions that can be
	 * matched against the requested URL. The value should contain the name or
	 * key of the segment which can be referenced by the processing logic.
	 * 
	 * @param matchSegments
	 */
	public void addMatchSegment(Map<String, String> matchSegments) {
		this.matchSegments.add(matchSegments);
	}

	public void addLiteralSegment(String matchSegment, String name) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(matchSegment, name);
		addMatchSegment(map);
	}

	public void addSubstitutionSegment(String name) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("*", name);
		addMatchSegment(map);
	}

	/**
	 * Create a FIFO queue that holds all segments in requested uri.
	 * 
	 * @param requestURL
	 * @return
	 */
	protected Queue<String> getPath(URI uri) {
		Assertions.notNull(uri, "uri");
		Queue<String> queue = new ConcurrentLinkedQueue<>();
		String[] segments = uri.getPath().split("/");
		for (String segment : segments) {
			if (!segment.isEmpty()) {
				queue.add(segment);
			}
		}
		return queue;
	}

	@Override
	public boolean match(HttpExchange ex, Map<String, Object> result) {
		boolean m = matchMethod(ex.getRequestMethod());
		if (m) {
			Queue<String> segmentQueue = getPath(ex.getRequestURI());
			int index = 0;
			while (!segmentQueue.isEmpty()) {
				String segment = segmentQueue.poll();
				if (!matchNextSegment(index, segment, result)) {
					m = false;
					break;
				}
				index++;
			}
			m = (index >= this.matchSegments.size());
		}
		return m;
	}

	protected boolean matchMethod(String method) {
		if (matchMethod == null || matchMethod.isEmpty() || "*".equals(matchMethod)) {
			return true;
		}
		return method.equalsIgnoreCase(matchMethod);
	}

	protected boolean matchNextSegment(int index, String segment, Map<String, Object> result) {
		boolean matched = false;
		if (index > -1 && index < matchSegments.size()) {
			String safeSegment = segment == null || segment.isEmpty() ? "" : segment;
			Map<String, String> matches = matchSegments.get(index);
			for (Entry<String, String> matchEntry : matches.entrySet()) {
				String matchExpr = matchEntry.getKey();

				String optionName = matchEntry.getValue();
				if (optionName == null || optionName.isEmpty()) {
					optionName = "segment_" + index;
				}

				if ("*".equals(matchExpr)) {
					// matches everything
					result.put(optionName, safeSegment);
					matched = true;
					break;
				} else if (isExpression(matchExpr)) {
					//
					matched = true;
				} else {
					// literal segment, just add
					result.put(optionName, safeSegment);
					matched = true;
				}
			}
		}
		return matched;
	}

	private boolean isExpression(String matchExpr) {
		return matchExpr != null && matchExpr.startsWith("{") && matchExpr.endsWith("}");
	}
}
