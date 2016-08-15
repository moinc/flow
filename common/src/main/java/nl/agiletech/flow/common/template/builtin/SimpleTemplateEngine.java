/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.template.builtin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.agiletech.flow.common.template.TemplateEngine;
import nl.agiletech.flow.common.template.TemplateRenderException;

// TODO: data dictionary is now a flat list, adjust lookup code to deal with that
public class SimpleTemplateEngine implements TemplateEngine {
	// private static final Logger LOG =
	// Logger.getLogger(SimpleTemplateEngine.class.getName());

	static final String placeholderExpr = "\\$\\w+|\\$\\{[a-zA-Z_0-9()'\\[\\] \\.]+\\}";
	static final Pattern placeholderPattern = Pattern.compile(placeholderExpr);
	static final String indexerExpr = "^.+(\\[.+\\]).*$";
	static final Pattern indexerPattern = Pattern.compile(indexerExpr);
	String lineEnd = "\n";

	@Override
	public void render(Reader src, Writer dest, Map<String, Object> dataDictionary)
			throws IOException, TemplateRenderException {
		assert src != null && dest != null && dataDictionary != null;
		try (BufferedReader reader = new BufferedReader(src)) {
			int lineNum = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				if (lineNum != 0) {
					dest.write("\n");
				}
				render(line, dest, dataDictionary);
				lineNum++;
			}
		}
		dest.flush();
	}

	private void render(String line, Writer dest, Map<String, Object> dataDictionary)
			throws IOException, TemplateRenderException {
		if (line.isEmpty()) {
			return;
		}
		Matcher m = placeholderPattern.matcher(line);
		int offset = 0;
		boolean found = m.find();
		if (!found) {
			dest.write(line);
			return;
		}
		while (found) {
			int start = m.start();
			int end = m.end();
			String left = start > 0 ? line.substring(offset, start) : "";
			dest.write(left);
			String expr = line.substring(start, end);
			String repl = parse(expr, dataDictionary);
			dest.write(repl);
			offset = end;
			found = m.find();
		}
		if (offset < line.length()) {
			dest.write(line.substring(offset));
		}
	}

	private String parse(String expr, Map<String, Object> dataDictionary) throws TemplateRenderException {
		assert expr != null;
		if (expr.isEmpty()) {
			throw new TemplateRenderException("unexpected error: unparsable placeholder");
		}
		String key = extractKeyFromPlaceholder(expr);
		if (key.isEmpty()) {
			throw new TemplateRenderException("encountered an empty keyword or expression");
		}
		Object value = lookup(key, dataDictionary);
		if (value != null) {
			return convertToString(value);
		}
		throw new TemplateRenderException("failed to replace key: " + key);
	}

	private String extractKeyFromPlaceholder(String expr) {
		String key = expr;
		if (expr.startsWith("${") && expr.endsWith("}")) {
			key = expr.substring(2, expr.length() - 1);
		} else if (expr.startsWith("$")) {
			key = expr.substring(1);
		}
		return key;
	}

	@SuppressWarnings("rawtypes")
	private Object lookup(String key, Map dataDictionary) throws TemplateRenderException {
		String keyToResolve = key;
		if (dataDictionary.containsKey(keyToResolve)) {
			return dataDictionary.get(keyToResolve);
		}
		// check indexer
		StringBuffer newKey = new StringBuffer();
		int arrayIndex = getIndexerValueFromExpressionAndReturnExpressionWithoutIndexer(keyToResolve, newKey);
		if (arrayIndex != -1) {
			keyToResolve = newKey.toString();
		}
		return this.getValue(dataDictionary, keyToResolve, arrayIndex);
	}

	private String convertToString(Object value) {
		if (value == null) {
			value = "";
		}
		return value.toString();
	}

	private int getIndexerValueFromExpressionAndReturnExpressionWithoutIndexer(String key, StringBuffer newKey) {
		Matcher m = indexerPattern.matcher(key);
		boolean found = m.find();
		if (found && m.groupCount() == 1) {
			String indexer = m.group(1);
			String k = key.substring(0, key.length() - indexer.length());
			newKey.append(k);
			String s = indexer.substring(1, indexer.length() - 1);
			return Integer.parseInt(s);
		}
		return -1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getValue(Map dataDictionary, String keyToResolve, int arrayIndex) throws TemplateRenderException {
		Object value = dataDictionary.get(keyToResolve);
		if (value == null) {
			return value;
		}
		if (value instanceof Map) {
			throw new TemplateRenderException("data dictionary returned a Map for key: " + keyToResolve);
		}
		if (value.getClass().isArray()) {
			value = Arrays.asList((Object[]) value);
		}
		if (value instanceof Collection) {
			value = new ArrayList((Collection) value);
		}
		if (arrayIndex > -1) {
			if (value instanceof List) {
				List list = (List) value;
				if (arrayIndex >= list.size()) {
					throw new TemplateRenderException(
							"value at '" + keyToResolve + "' is an array or collection but the specified index ["
									+ arrayIndex + "] was out of range (" + list.size() + ")");
				}
				value = list.get(arrayIndex);
			} else {
				throw new TemplateRenderException("indexer specified but value was not an array or collection");
			}
		}
		return value;
	}

	// @SuppressWarnings("rawtypes")
	// private Object lookup(Queue<String> queue, Map dataDictionary) throws
	// TemplateRenderException {
	// assert queue != null && dataDictionary != null;
	// String keyToResolve = queue.poll();
	//
	// // if the key contains an indexer, get the indexer value and return a
	// // key without the indexer
	// StringBuffer newKey = new StringBuffer();
	// int arrayIndex =
	// getIndexerValueFromExpressionAndReturnExpressionWithoutIndexer(keyToResolve,
	// newKey);
	// if (arrayIndex != -1) {
	// keyToResolve = newKey.toString();
	// }
	//
	// if (dataDictionary.containsKey(keyToResolve)) {
	// Object value = getValue(dataDictionary, keyToResolve, arrayIndex);
	// if (value != null) {
	// if (queue.isEmpty()) {
	// return value;
	// }
	// if (value instanceof Map) {
	// value = lookup(queue, (Map) value);
	// if (value != null) {
	// return value;
	// }
	// }
	// }
	// }
	// throw new TemplateRenderException("failed to find key: " + keyToResolve +
	// " in data dictionary");
	// }

	// /**
	// * Compare two arrays for equality. The first array may contain elements
	// * containing a wildcard (e.g. '*' or '?') in which case any element
	// should
	// * match.
	// *
	// * @throws TemplateRenderException
	// */
	// private boolean matches(String[] a, String[] b) throws
	// TemplateRenderException {
	// assert a != null && a.length != 0 && b != null && b.length != 0;
	// String[] q = Arrays.copyOf(a, a.length);
	// String last = q[q.length - 1];
	// if ("*".equals(last) || "?".equals(last)) {
	// throw new TemplateRenderException(
	// "specified key: " + StringUtil.join(q, ".") + " cannot end with
	// wildcard");
	// }
	// // find the last '*' from left to right
	// int lastAsteriskOnLeft = -1;
	// for (int i = 0; i < q.length; i++) {
	// if ("*".equals(q[i])) {
	// lastAsteriskOnLeft = Math.max(lastAsteriskOnLeft, i);
	// }
	// }
	// if (lastAsteriskOnLeft != -1) {
	// // remove all elements in a on left of and including the last
	// // asterisk
	// int offset = lastAsteriskOnLeft + 1;
	// String[] q1 = new String[q.length - offset];
	// System.arraycopy(q, offset, q1, 0, q1.length);
	// q = q1;
	// }
	// if (lastAsteriskOnLeft == -1) {
	// if (q.length != b.length) {
	// return false;
	// }
	// }
	// boolean matches = true;
	// int ql = q.length - 1;
	// int bl = b.length - 1;
	// for (int i = 0; matches && i < q.length; i++) {
	// String qq = q[ql - i];
	// if (!"?".equals(qq) && !qq.equals(b[bl - i])) {
	// matches = false;
	// }
	// }
	// return matches;
	// }
}
