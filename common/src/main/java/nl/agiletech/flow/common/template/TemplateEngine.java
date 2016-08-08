package nl.agiletech.flow.common.template;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public interface TemplateEngine {
	void render(Reader src, Writer dest, Map<String, Object> dataDictionary)
			throws IOException, TemplateRenderException;
}
