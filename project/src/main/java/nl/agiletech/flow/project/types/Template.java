/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Template implements TakesContext, ValueTransform<String> {
	interface TemplateSource {
		Reader getReader() throws IOException;
	}

	class InlineTemplateSource implements TemplateSource {
		final String src;
		final Charset charset;

		public InlineTemplateSource(String src) {
			assert src != null;
			this.src = src;
			this.charset = StandardCharsets.UTF_8;
		}

		public InlineTemplateSource(String src, Charset charset) {
			assert src != null && charset != null;
			this.src = src;
			this.charset = charset;
		}

		@Override
		public Reader getReader() throws UnsupportedEncodingException {
			byte[] raw = src.getBytes(charset);
			return new InputStreamReader(new ByteArrayInputStream(raw), charset);
		}
	}

	class InputStreamTemplateSource implements TemplateSource {
		final InputStream src;
		final Charset charset;

		public InputStreamTemplateSource(InputStream src) {
			assert src != null;
			this.src = src;
			this.charset = StandardCharsets.UTF_8;
		}

		public InputStreamTemplateSource(InputStream src, Charset charset) {
			assert src != null && charset != null;
			this.src = src;
			this.charset = charset;
		}

		@Override
		public Reader getReader() {
			return new InputStreamReader(src, charset);
		}
	}

	class FileTemplateSource implements TemplateSource {
		final java.io.File src;
		final Charset charset;

		public FileTemplateSource(java.io.File src) {
			assert src != null;
			this.src = src;
			this.charset = StandardCharsets.UTF_8;
		}

		public FileTemplateSource(java.io.File src, Charset charset) {
			assert src != null && charset != null;
			this.src = src;
			this.charset = charset;
		}

		@Override
		public Reader getReader() throws FileNotFoundException {
			return new InputStreamReader(new FileInputStream(src), charset);
		}
	}

	public static Template inline(String src) {
		return new Template(src);
	}

	public static Template inline(String src, Charset charset) {
		return new Template(src, charset);
	}

	public static Template resource(InputStream src) {
		return new Template(src);
	}

	public static Template resource(InputStream src, Charset charset) {
		return new Template(src, charset);
	}

	public static Template file(java.io.File src) {
		return new Template(src);
	}

	public static Template file(java.io.File src, Charset charset) {
		return new Template(src, charset);
	}

	Context context;
	final TemplateSource src;

	private Template(String src) {
		this.src = new InlineTemplateSource(src);
	}

	private Template(String src, Charset charset) {
		this.src = new InlineTemplateSource(src, charset);
	}

	private Template(InputStream src) {
		this.src = new InputStreamTemplateSource(src);
	}

	private Template(InputStream src, Charset charset) {
		this.src = new InputStreamTemplateSource(src, charset);
	}

	private Template(java.io.File src) {
		this.src = new FileTemplateSource(src);
	}

	private Template(java.io.File src, Charset charset) {
		this.src = new FileTemplateSource(src, charset);
	}

	@Override
	public void setContext(Context context) {
		assert context != null;
		this.context = context;
	}

	@Override
	public String render() throws IOException {
		if (context != null && src != null) {
			try (Reader reader = src.getReader()) {
				return context.renderTemplate(reader);
			}
		}
		return "[template error]";
	}

	@Override
	public String toString() {
		try {
			return render();
		} catch (IOException e) {
			return "[template error: " + e.getMessage() + "]";
		}
	}

	/**
	 * Parses the specified template and returns the value.
	 * 
	 * @param context
	 *            Context to apply to the template.
	 * @return String value containing the value.
	 */
	public static Object getValue(Template template, Context context) {
		assert template != null;
		if (context != null) {
			context.applyTo(template);
		}
		return template.toString();

	}

	/**
	 * Convenience method that checks if the specified value is a template.
	 * 
	 * @param maybeTemplate
	 * @return
	 */
	public static Object getValue(Object maybeTemplate, Context context) {
		if (maybeTemplate instanceof Template) {
			return getValue((Template) maybeTemplate, context);
		}
		return maybeTemplate;
	}

}
