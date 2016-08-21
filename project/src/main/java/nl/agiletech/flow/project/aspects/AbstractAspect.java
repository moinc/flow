package nl.agiletech.flow.project.aspects;

import java.util.LinkedHashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Aspect;
import nl.agiletech.flow.project.types.Attribute;
import nl.agiletech.flow.project.types.HasAttributes;

public abstract class AbstractAspect implements Aspect {
	final boolean required;
	boolean changed;
	final Map<String, Object> values = new LinkedHashMap<>();

	public AbstractAspect(boolean required) {
		this.required = required;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public boolean isEnabled() {
		return required || changed;
	}

	@Override
	public void visit(HasAttributes hasAttributes) {
		if (isValid()) {
			for (String key : values.keySet()) {
				Object value = values.get(key);
				hasAttributes.getAttributes().add(new Attribute(this, key, value));
			}
		}
	}

	public Object getValue(String key) {
		return values.get(key);
	}

	public void setValue(String key, Object value) {
		values.put(key, value);
		changed = true;
	}

	public void clear() {
		values.clear();
	}
}
