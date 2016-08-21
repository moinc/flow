package nl.agiletech.flow.project.aspects;

import nl.agiletech.flow.project.annotation.FlowIgnore;
import nl.agiletech.flow.project.types.Aspect;
import nl.agiletech.flow.project.types.Attribute;
import nl.agiletech.flow.project.types.HasAttributes;

public class OwnershipAspect implements Aspect {
	@FlowIgnore
	public static final OwnershipAspect OPTIONAL = new OwnershipAspect(false);
	@FlowIgnore
	public static final OwnershipAspect REQUIRED = new OwnershipAspect(true);

	final boolean required;
	boolean changed;
	String user;
	String group;

	public OwnershipAspect(boolean required) {
		this.required = required;
	}

	public OwnershipAspect(boolean required, String user, String group) {
		this.required = required;
		this.user = user;
		this.group = group;
	}

	@Override
	public void visit(HasAttributes hasAttributes) {
		if (isValid()) {
			hasAttributes.getAttributes().add(new Attribute(this, "user", user));
			hasAttributes.getAttributes().add(new Attribute(this, "group", group));
		}
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public boolean isValid() {
		return checkUser(user) && checkGroup(group);
	}

	@Override
	public boolean isEnabled() {
		return required || changed;
	}

	public OwnershipAspect change(String user, String group) {
		if (!checkUser(user)) {
			throw new IllegalArgumentException("the specified 'user' is invalid");
		}
		if (!checkGroup(group)) {
			throw new IllegalArgumentException("the specified 'group' is invalid");
		}
		this.user = user;
		this.group = group;
		changed = true;
		return this;
	}

	private boolean checkUser(String user) {
		return user != null && !user.isEmpty();
	}

	private boolean checkGroup(String group) {
		return group != null && !group.isEmpty();
	}
}
