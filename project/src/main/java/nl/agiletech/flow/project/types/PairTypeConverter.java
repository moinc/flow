package nl.agiletech.flow.project.types;

public class PairTypeConverter implements TypeConverter<Pair, Object> {
	@Override
	public Object convert(Pair value) {
		return value.getValue();
	}
}
