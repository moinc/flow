package nl.agiletech.flow.cmp.inspector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import nl.agiletech.flow.cmp.compiler.builtin.DefaultContextValidator;
import nl.agiletech.flow.cmp.jarinspector.ClassUtil;
import nl.agiletech.flow.cmp.jarinspector.ClassUtilException;
import nl.agiletech.flow.cmp.jarinspector.ObjectDiscoveryOptions;
import nl.agiletech.flow.cmp.project.ProjectConfiguration;
import nl.agiletech.flow.project.annotation.Flow;
import nl.agiletech.flow.project.types.ConfigurationSettings;
import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.ContextValidator;
import nl.agiletech.flow.project.types.Node;
import nl.agiletech.flow.project.types.NodeId;
import nl.agiletech.flow.project.types.Platform;
import nl.agiletech.flow.project.types.RequestType;

public class ClassUtilTest {
	public static class TestClassWithoutConstructor {
		//
	}

	public static class TestClassWithoutDefaultConstructor {
		public TestClassWithoutDefaultConstructor(boolean dummy) {
			//
		}
	}

	public static class TestClassWithDefaultConstructor {
		public TestClassWithDefaultConstructor() {
			//
		}
	}

	@Flow
	public static class SomePlatform implements Platform {

	}

	@Flow
	public static class SomeAnnotatedType0 {

	}

	@Flow
	public static class SomeAnnotatedType1 {

	}

	@Flow
	public static class SomeAnnotatedType2 {

	}

	@Flow
	public static class SomeAnnotatedType3 extends Node {
		public NodeId identity = NodeId.get("a");
	}

	@Flow
	public static class SomeAnnotatedType4 extends Node {
		public NodeId identity = NodeId.get("a");
	}

	@Flow
	public static class SomeAnnotatedType5 extends Node {
		public NodeId identity = NodeId.get("a");
	}

	@Flow
	public static class SomeAnnotatedType6 extends Node {
		public NodeId identity = NodeId.get("a");
	}

	public static class TestClassWithFields {
		SomeAnnotatedType0 privateField = new SomeAnnotatedType0();
		public String excludedFieldString = "hello world";
		public int excludedFieldInt = 0;
		public SomeAnnotatedType1 publicField = new SomeAnnotatedType1();
		public List<Node> publicFieldList = new ArrayList<>();
		public SomeAnnotatedType2[] publicFieldArray = new SomeAnnotatedType2[] { new SomeAnnotatedType2() };
		public Map<String, Node> publicFieldMap = new HashMap<>();

		public TestClassWithFields() {
			publicFieldList.add(new SomeAnnotatedType3());
			publicFieldList.add(new SomeAnnotatedType4());
			publicFieldMap.put("a", new SomeAnnotatedType5());
			publicFieldMap.put("b", new SomeAnnotatedType6());
		}
	}

	@Test
	public void testShouldCreateInstanceWhenClassHasNoCtor() throws Exception {
		TestClassWithoutConstructor instance = ClassUtil.createInstance(TestClassWithoutConstructor.class,
				createContext());
		assertNotNull(instance);
	}

	@Test(expected = ClassUtilException.class)
	public void testShouldThrowWhenClassHasNoDefaultCtor() throws Exception {
		TestClassWithoutDefaultConstructor instance = ClassUtil.createInstance(TestClassWithoutDefaultConstructor.class,
				createContext());
		assertNotNull(instance);
	}

	@Test
	public void testShouldCreateInstanceOfClassWithDefaultCtor() throws Exception {
		TestClassWithDefaultConstructor instance = ClassUtil.createInstance(TestClassWithDefaultConstructor.class,
				createContext());
		assertNotNull(instance);
	}

	@Test
	public void testShouldDiscoverObjects() throws Exception, IllegalAccessException, InvocationTargetException {
		Map<String, Object> objects = ClassUtil.discoverObjects(new TestClassWithFields(), createContext(),
				ObjectDiscoveryOptions.createInstanceForDependencyDiscovery());
		List<Class<?>> classes = new ArrayList<>();
		for (Object obj : objects.values()) {
			classes.add(obj.getClass());
		}
		assertFalse("expected the private field to be excluded", classes.contains(SomeAnnotatedType0.class));
		assertFalse("expected the String field to be excluded", classes.contains(String.class));
		assertFalse("expected a primitive (int) field to be excluded", classes.contains(int.class));
		assertTrue("expected public field to be included", classes.contains(SomeAnnotatedType1.class));
		assertTrue("expected the array to be included", classes.contains(SomeAnnotatedType2.class));
		assertTrue("expected the list to be included", classes.contains(SomeAnnotatedType3.class));
		assertTrue("expected the list to be included", classes.contains(SomeAnnotatedType4.class));
		assertTrue("expected the list to be included", classes.contains(SomeAnnotatedType5.class));
		assertTrue("expected the list to be included", classes.contains(SomeAnnotatedType6.class));
	}

	private Context createContext() throws Exception {
		ConfigurationSettings configurationSettings = ConfigurationSettings.EMPTY;
		ProjectConfiguration projectConfiguration = new ProjectConfiguration();
		ContextValidator contextValidator = new DefaultContextValidator(projectConfiguration);
		return Context.createInstance(contextValidator, configurationSettings, RequestType.INSPECT);
	}
}
