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
import nl.agiletech.flow.common.util.Assertions;

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
		Assertions.notNull(src, "src");
		Assertions.notNull(dest, "dest");
		Assertions.notNull(dataDictionary, "dataDictionary");
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
		Assertions.notNull(expr, "expr");
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
		Assertions.notEmpty(key, "key");
		Assertions.notNull(dataDictionary, "dataDictionary");
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
}
