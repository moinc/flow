/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.util.Map.Entry;

public class MapEntryTypeConverter implements TypeConverter<Entry<String, Object>, Object> {
	@Override
	public Object convert(Entry<String, Object> value) {
		return value.getValue();
	}
}
