package nl.agiletech.flow.project.types;

public class ContextTestUtil {
	public static Context createContext() throws Exception {
		return Context.createInstance(ContextValidator.NULL_VALIDATOR, ConfigurationSettings.EMPTY,
				RequestType.INSPECT);
	}
}
