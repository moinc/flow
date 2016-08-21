package nl.agiletech.flow.project.types;

import nl.agiletech.flow.common.util.Assertions;

public class Instruction {
	public static final class TYPE {
		// must download
		public static final int FETCH_RESOURCE = 100;
		// must execute inline (resource is script)
		public static final int INLINE_SHELL = 200;
		// must download and execute
		public static final int RESOURCE_SHELL = 300;
	}

	public static Instruction createInstance(Class<?> clazz, String key, String description, int type,
			String resource) {
		return new Instruction(clazz.getName(), key, description, type, resource);
	}

	public static Instruction createInstance(String id, String key, String description, int type, String resource) {
		return new Instruction(id, key, description, type, resource);
	}

	String id = "";
	String key = "";
	String description = "";
	int type = 0;
	String resource = "";

	public Instruction() {
		// default ctor
	}

	public Instruction(String id, String key, String description, int type, String resource) {
		super();
		Assertions.notEmpty(id, "id");
		Assertions.notEmpty(key, "key");
		Assertions.notEmpty(description, "description");
		Assertions.notEmpty(type, "type");
		Assertions.notEmpty(resource, "resource");
		this.id = id;
		this.key = key;
		this.description = description;
		this.type = type;
		this.resource = resource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Instruction) {
			Instruction other = (Instruction) obj;
			return id.equals(other.id) && key.equals(other.key) && description.equals(other.description)
					&& type == other.type && resource.equals(other.resource);
		}
		return super.equals(obj);
	}
}
