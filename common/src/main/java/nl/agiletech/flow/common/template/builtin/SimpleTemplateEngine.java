package nl.agiletech.flow.common.template.builtin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.agiletech.flow.common.template.TemplateEngine;
import nl.agiletech.flow.common.template.TemplateRenderException;
import nl.agiletech.flow.common.util.StringUtil;

public class SimpleTemplateEngine implements TemplateEngine {
	static final String expr = "\\$\\w+|\\$\\{[a-zA-Z_0-9()' \\.*\\?]+\\}";
	static final Pattern pattern = Pattern.compile(expr);
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
		Matcher m = pattern.matcher(line);
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
			return "";
		}
		String key = "";
		if (expr.startsWith("${") && expr.endsWith("}")) {
			key = expr.substring(2, expr.length() - 1);
		} else if (expr.startsWith("$")) {
			key = expr.substring(1);
		}
		if (key.isEmpty()) {
			return "";
		}
		String resolvedKey = findKey(key, dataDictionary);
		Object value = dataDictionary.get(resolvedKey);
		if (value == null) {
			value = "";
		}
		return value.toString();
	}

	private String findKey(String key, Map<String, Object> dataDictionary) throws TemplateRenderException {
		assert key != null;
		if (key.indexOf(".") == -1) {
			return key;
		}
		String[] lookForKeyComponents = key.split("\\.");
		for (String dataKey : dataDictionary.keySet()) {
			String[] dataKeyComponents = dataKey.split("\\.");
			if (matches(lookForKeyComponents, dataKeyComponents)) {
				return dataKey;
			}
		}
		throw new TemplateRenderException("failed to find key: " + key + " in data dictionary");
	}

	/**
	 * Compare two arrays for equality. The first array may contain elements
	 * containing a wildcard (e.g. '*' or '?') in which case any element should
	 * match.
	 * 
	 * @throws TemplateRenderException
	 */
	private boolean matches(String[] a, String[] b) throws TemplateRenderException {
		assert a != null && a.length != 0 && b != null && b.length != 0;
		String[] q = Arrays.copyOf(a, a.length);
		String last = q[q.length - 1];
		if ("*".equals(last) || "?".equals(last)) {
			throw new TemplateRenderException(
					"specified key: " + StringUtil.join(q, ".") + " cannot end with wildcard");
		}
		// find the last '*' from left to right
		int lastAsteriskOnLeft = -1;
		for (int i = 0; i < q.length; i++) {
			if ("*".equals(q[i])) {
				lastAsteriskOnLeft = Math.max(lastAsteriskOnLeft, i);
			}
		}
		if (lastAsteriskOnLeft != -1) {
			// remove all elements in a on left of and including the last
			// asterisk
			int offset = lastAsteriskOnLeft + 1;
			String[] q1 = new String[q.length - offset];
			System.arraycopy(q, offset, q1, 0, q1.length);
			q = q1;
		}
		if (lastAsteriskOnLeft == -1) {
			if (q.length != b.length) {
				return false;
			}
		}
		boolean matches = true;
		int ql = q.length - 1;
		int bl = b.length - 1;
		for (int i = 0; matches && i < q.length; i++) {
			String qq = q[ql - i];
			if (!"?".equals(qq) && !qq.equals(b[bl - i])) {
				matches = false;
			}
		}
		return matches;
	}
}
