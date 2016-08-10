/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.cmp.jarinspector;

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
import nl.agiletech.flow.common.util.StringUtil;
import nl.agiletech.flow.project.types.Context;

public class ClassUtil {
	private static final Logger LOG = Logger.getLogger(ClassUtil.class.getName());

	public static <T> T createInstance(Class<? extends T> clazz, Context context) throws ClassUtilException {
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
		assert obj != null && context != null;
		Class<?> clazz = obj.getClass();
		Map<String, Object> objects = new LinkedHashMap<>();
		discoverObjectsFromFields(obj, clazz, objects, options);
		context.applyTo(objects.values().toArray());
		return objects;
	}

	static void discoverObjectsFromFields(Object obj, Class<?> clazz, Map<String, Object> objects,
			ObjectDiscoveryOptions options) throws IllegalAccessException {
		assert obj != null && objects != null;
		String declaredInName = clazz.getName();
		Field[] fields = clazz.getFields();
		for (Field field : filter(fields, options)) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = field.get(obj);

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
		List<Field> result = new ArrayList<>();
		for (Field field : fields) {
			filterField(field, field.getType(), result, options);
		}
		return result.toArray(new Field[0]);
	}

	private static void filterField(Field field, Class<?> type, List<Field> result, ObjectDiscoveryOptions options) {
		List<Class<?>> componentTypes = new ArrayList<>();
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

	static boolean allowPackage(Class<?> clazz, ObjectDiscoveryOptions options) {
		assert clazz != null;
		if (clazz.isPrimitive()) {
			return true;
		}
		String packageName = getPackageName(clazz);
		List<String> packageNames = StringUtil.factors(packageName.split("\\."));
		return !CollectionUtil.containsAny(options.getExcludedPackages(), packageNames);
	}

	private static String getPackageName(Class<?> clazz) {
		String className = clazz.getCanonicalName();
		String[] names = className.split("\\.");
		names = ArrayUtil.copy(names, new String[Math.max(0, names.length - 1)]);
		return StringUtil.join(names, ".");
	}

	static boolean isCollectionClass(Field field, List<Class<?>> componentTypes) {
		assert field != null && componentTypes != null;
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
