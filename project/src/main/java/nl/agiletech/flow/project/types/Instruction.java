package nl.agiletech.flow.project.types;

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
		assert id != null && id.length() != 0;
		assert key != null && key.length() != 0;
		assert description != null && description.length() != 0;
		assert type != 0;
		assert resource != null && resource.length() != 0;
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

}
