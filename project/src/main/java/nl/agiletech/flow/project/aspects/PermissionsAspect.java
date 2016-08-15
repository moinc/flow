package nl.agiletech.flow.project.aspects;

import nl.agiletech.flow.project.types.Aspect;
import nl.agiletech.flow.project.types.Attribute;
import nl.agiletech.flow.project.types.HasAttributes;

public class PermissionsAspect implements Aspect {
	public static final PermissionsAspect OPTIONAL = new PermissionsAspect(false);
	public static final PermissionsAspect REQUIRED = new PermissionsAspect(true);

	public static final int READ = 4;
	public static final int WRITE = 2;
	public static final int EXECUTE = 1;

	public static final int ALL = READ | WRITE | EXECUTE;
	public static final int READ_WRITE = READ | WRITE;
	public static final int READ_EXECUTE = READ | EXECUTE;

	final boolean required;
	boolean changed;
	int owner = READ | WRITE | EXECUTE;
	int group = READ | EXECUTE;
	int other = READ | EXECUTE;

	public PermissionsAspect(boolean required) {
		this.required = required;
	}

	public PermissionsAspect(boolean required, int owner, int group, int other) {
		this.required = required;
		this.owner = owner;
		this.group = group;
		this.other = other;
	}

	@Override
	public void visit(HasAttributes hasAttributes) {
		if (isValid()) {
			String mode = "" + owner + "" + group + "" + other;
			hasAttributes.getAttributes().add(new Attribute(this, "mode", mode));
		}
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public boolean isValid() {
		return checkValue(owner) && checkValue(group) && checkValue(other);
	}

	@Override
	public boolean isEnabled() {
		return required || changed;
	}

	public PermissionsAspect change(int owner, int group, int other) {
		if (!checkValue(owner)) {
			throw new IllegalArgumentException("the specified permission 'owner' is invalid");
		}
		if (!checkValue(group)) {
			throw new IllegalArgumentException("the specified permission 'group' is invalid");
		}
		if (!checkValue(other)) {
			throw new IllegalArgumentException("the specified permission 'other' is invalid");
		}
		this.owner = owner;
		this.group = group;
		this.other = group;
		this.changed = true;
		return this;
	}

	private boolean checkValue(int value) {
		return value > -1 && value < 8;
	}
}
