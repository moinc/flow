package nl.agiletech.flow.project.types;

/**
 * Immutable attribute.
 * 
 * @author moincreemers
 *
 */
public class Attribute {
	final Class<? extends AttributeSource> source;
	final String key;
	final Object value;

	public Attribute(AttributeSource source, String key, Object value) {
		this(source.getClass(), key, value);
	}

	public Attribute(Class<? extends AttributeSource> source, String key, Object value) {
		super();
		this.source = source;
		this.key = key;
		this.value = value;
	}

	public Class<? extends AttributeSource> getSource() {
		return source;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}
}
