package nl.agiletech.flow.project.types;

import java.io.IOException;

public interface ValueTransform<O> {
	static Object getValue(Object value, Context context) throws IOException {
		if (value instanceof ValueTransform) {
			return ((ValueTransform<?>) value).render();
		}
		return value;
	}

	O render() throws IOException;
}
