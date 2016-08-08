package SimpleTemplateEngine;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import nl.agiletech.flow.common.template.TemplateEngine;
import nl.agiletech.flow.common.template.builtin.SimpleTemplateEngine;

public class SimpleTemplateEngineTest {
	@Test
	public void testSimpleTemplateEngineShouldParseSimpleTemplates() throws Exception {
		TemplateEngine te = new SimpleTemplateEngine();

		String template = "This is a simple template.\nIt should render A$a1, Ba${a2}, ${a.3}wi and S${0}berry just fine.\nOn this line we test a ${*.asterisk} ${*.?.asteriskAndPlaceholder}.";
		String expected = "This is a simple template.\nIt should render Apple, Banana, Kiwi and Strawberry just fine.\nOn this line we test a wildcard search.";

		Map<String, Object> dd = new HashMap<>();
		dd.put("a1", "pple");
		dd.put("a2", "nana");
		dd.put("a.3", "Ki");
		dd.put("0", "traw");
		dd.put("someobject.anotherobject.asterisk", "wildcard");
		dd.put("someobject.anotherobject.asteriskAndPlaceholder", "search");

		String actual = "";

		try (Reader src = new InputStreamReader(new ByteArrayInputStream(template.getBytes(StandardCharsets.UTF_8)))) {
			try (Writer dest = new StringWriter()) {
				te.render(src, dest, dd);
				actual = dest.toString();
			}
		}

		assertEquals(expected, actual);
	}
}
