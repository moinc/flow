package nl.agiletech.flow.project.types;

public class KeyPairTypeConverter implements TypeConverter<KeyPair, Object> {
	@Override
	public Object convert(KeyPair value) {
		return value.getValue();
	}
}
