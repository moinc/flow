package nl.agiletech.flow.project.types;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import nl.agiletech.flow.common.collections.CollectionUtil;

public class TemplateTest {
	@Test
	public void shouldRenderTemplate() throws Exception {
		Context context = ContextTestUtil.createContext();

		Map<String, Object> map = new HashMap<>();
		map.put("somevalue", "hello");

		Map<String, Object> a = new HashMap<>();
		map.put("a", a);

		Map<String, Object> b = new HashMap<>();
		a.put("b", b);

		b.put("c", "abc");

		Map<String, Object> configuration = new HashMap<>();
		CollectionUtil.flatten(map, configuration);

		context.setConfiguration(configuration);

		Template helloTemplate = Template.inline("$somevalue");
		helloTemplate.setContext(context);
		assertEquals("hello", helloTemplate.toString());

		Template abcTemplate = Template.inline("${a.b.c}");
		abcTemplate.setContext(context);
		assertEquals("abc", abcTemplate.toString());
	}
}
