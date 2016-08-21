/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.reflect;

import java.lang.annotation.Annotation;

import nl.agiletech.flow.common.util.Assertions;

public class ClassUtil {
	public static String getClassName(Class<?> clazz) {
		return clazz.getName();
	}

	public static boolean isAnnotationPresent(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		return isAnnotationPresent(clazz.getAnnotations(), annotationClass);
	}

	public static boolean isAnnotationPresent(Annotation[] annotations, Class<? extends Annotation> annotationClass) {
		Assertions.notNull(annotations, "annotations");
		Assertions.notNull(annotationClass, "annotationClass");
		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == annotationClass) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSubclassOf(Class<?> clazz, Class<?> superClass) {
		Assertions.notNull(clazz, "clazz");
		Assertions.notNull(superClass, "superClass");
		return (clazz.equals(superClass) || superClass.isAssignableFrom(clazz));
	}
}
