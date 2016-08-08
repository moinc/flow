/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

public interface TypeConverter<I, O> {
	O convert(I value);
}
