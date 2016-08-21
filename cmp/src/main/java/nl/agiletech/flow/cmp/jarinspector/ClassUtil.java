/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.jarinspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import nl.agiletech.flow.common.collections.CollectionUtil;
import nl.agiletech.flow.common.util.ArrayUtil;
import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.common.util.StringUtil;
import nl.agiletech.flow.project.types.Context;

public class ClassUtil {
	private static final Logger LOG = Logger.getLogger(ClassUtil.class.getName());

	public static <T> T createInstance(Class<? extends T> clazz, Context context) throws ClassUtilException {
		Assertions.notNull(clazz, "clazz");
		Assertions.notNull(context, "context");
		LOG.fine("creating instance from class: " + clazz.getName());
		try {
			Constructor<? extends T> ctor = clazz.getConstructor();
			ctor.setAccessible(true);
			T t = ctor.newInstance();
			context.applyTo(t);
			return t;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new ClassUtilException("unable to instantiate class: " + clazz.getName(), e);
		}
	}

	public static Map<String, Object> discoverObjects(Object obj, Context context, ObjectDiscoveryOptions options)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assertions.notNull(obj, "obj");
		Assertions.notNull(context, "context");
		Assertions.notNull(options, "options");
		Class<?> clazz = obj.getClass();
		Map<String, Object> objects = new LinkedHashMap<>();
		discoverObjectsFromFields(obj, clazz, objects, options);
		context.applyTo(objects.values().toArray());
		return objects;
	}

	static void discoverObjectsFromFields(Object obj, Class<?> clazz, Map<String, Object> objects,
			ObjectDiscoveryOptions options) throws IllegalAccessException {
		Assertions.notNull(obj, "obj");
		Assertions.notNull(clazz, "clazz");
		Assertions.notNull(objects, "objects");
		Assertions.notNull(options, "options");
		String declaredInName = clazz.getName();
		Field[] fields = clazz.getFields();
		for (Field field : filter(fields, options)) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = field.get(obj);

			// TODO: if the field is an inline type, then use the field name to
			// give the object instance name more meaning

			if (options.isExpandCollections() && value instanceof Map<?, ?>) {
				Map<?, ?> map = (Map<?, ?>) value;
				for (Object k : map.keySet()) {
					if (options.getTypeFilter().include(obj.getClass())) {
						String key = k == null ? "" : k.toString();
						Object val = map.get(k);
						String concatenatedName = StringUtil.join(new Object[] { declaredInName, fieldName, key }, ".");
						objects.put(concatenatedName, val);
					}
				}
			} else if (options.isExpandCollections() && value instanceof Collection<?>) {
				Collection<?> col = (Collection<?>) value;
				int i = 0;
				for (Object val : col) {
					if (options.getTypeFilter().include(val.getClass())) {
						String key = "item_" + i;
						String concatenatedName = StringUtil.join(new Object[] { declaredInName, fieldName, key }, ".");
						objects.put(concatenatedName, val);
						i++;
					}
				}
			} else if (options.isExpandCollections() && value != null && value.getClass().isArray()) {
				Object[] array = (Object[]) value;
				int j = 0;
				for (int i = 0; i < array.length; i++) {
					if (array[i] != null && options.getTypeFilter().include(array[i].getClass())) {
						String key = "item_" + j;
						String concatenatedName = StringUtil.join(new Object[] { declaredInName, fieldName, key }, ".");
						objects.put(concatenatedName, array[i]);
						j++;
					}
				}
			} else if (options.getTypeFilter().include(value.getClass())) {
				String concatenatedName = StringUtil.join(new Object[] { declaredInName, fieldName }, ".");
				objects.put(concatenatedName, value);
			}
		}
	}

	static Field[] filter(Field[] fields, ObjectDiscoveryOptions options) {
		Assertions.notNull(fields, "fields");
		Assertions.notNull(options, "options");
		List<Field> result = new ArrayList<>();
		for (Field field : fields) {
			filterField(field, field.getType(), result, options);
		}
		return result.toArray(new Field[0]);
	}

	private static void filterField(Field field, Class<?> type, List<Field> result, ObjectDiscoveryOptions options) {
		Assertions.notNull(field, "field");
		Assertions.notNull(result, "result");
		Assertions.notNull(options, "options");
		List<Class<?>> componentTypes = new ArrayList<>();
		if (hasAnnotation(field, options)) {
			LOG.fine("-field: " + field.getDeclaringClass().getSimpleName() + "." + field.getName());
		} else {
			if (allowPackage(type, options)) {
				LOG.fine("+field: " + field.getDeclaringClass().getSimpleName() + "." + field.getName());
				result.add(field);
			} else if (isCollectionClass(field, componentTypes)) {
				Class<?> componentType = componentTypes.get(0);
				filterField(field, componentType, result, options);
			} else {
				LOG.fine("-field: " + field.getDeclaringClass().getSimpleName() + "." + field.getName());
			}
		}
	}

	private static boolean hasAnnotation(Field field, ObjectDiscoveryOptions options) {
		for (Class<? extends Annotation> annotationClass : options.getAnnotationClasses()) {
			if (field.isAnnotationPresent(annotationClass)) {
				return true;
			}
		}
		return false;
	}

	static boolean allowPackage(Class<?> clazz, ObjectDiscoveryOptions options) {
		Assertions.notNull(clazz, "clazz");
		Assertions.notNull(options, "options");
		if (clazz.isPrimitive()) {
			return true;
		}
		String packageName = getPackageName(clazz);
		List<String> packageNames = StringUtil.factors(packageName.split("\\."));
		return !CollectionUtil.containsAny(options.getExcludedPackages(), packageNames);
	}

	private static String getPackageName(Class<?> clazz) {
		Assertions.notNull(clazz, "clazz");
		String className = clazz.getCanonicalName();
		String[] names = className.split("\\.");
		names = ArrayUtil.copy(names, new String[Math.max(0, names.length - 1)]);
		return StringUtil.join(names, ".");
	}

	static boolean isCollectionClass(Field field, List<Class<?>> componentTypes) {
		Assertions.notNull(field, "field");
		Assertions.notNull(componentTypes, "componentTypes");
		if (Map.class.isAssignableFrom(field.getType())) {
			ParameterizedType pType = (ParameterizedType) field.getGenericType();
			componentTypes.add((Class<?>) pType.getActualTypeArguments()[1]);
			return true;
		}
		if (Collection.class.isAssignableFrom(field.getType())) {
			ParameterizedType pType = (ParameterizedType) field.getGenericType();
			componentTypes.add((Class<?>) pType.getActualTypeArguments()[0]);
			return true;
		}
		if (field.getType().isArray()) {
			componentTypes.add(field.getType().getComponentType());
			return true;
		}
		return false;
	}
}
