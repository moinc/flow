/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

public interface Filter<T> {
	boolean include(T value);
}
