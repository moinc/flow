package nl.agiletech.flow.project.types;

import nl.agiletech.flow.common.util.Assertions;
import nl.agiletech.flow.common.util.NumberUtil;

public class ConfigurationCondition implements Condition {

	public static ConfigurationCondition equals(String key, Object expectedValue) {
		return new ConfigurationCondition(key, RelationalOperator.EQUALS, expectedValue);
	}

	public static ConfigurationCondition notEquals(String key, Object expectedValue) {
		return new ConfigurationCondition(key, RelationalOperator.NOT_EQUALS, expectedValue);
	}

	public static ConfigurationCondition greaterThan(String key, Object expectedValue) {
		return new ConfigurationCondition(key, RelationalOperator.GREATER_THAN, expectedValue);
	}

	public static ConfigurationCondition greaterThanOrEquals(String key, Object expectedValue) {
		return new ConfigurationCondition(key, RelationalOperator.GREATER_THAN_OR_EQUALS, expectedValue);
	}

	public static ConfigurationCondition lessThan(String key, Object expectedValue) {
		return new ConfigurationCondition(key, RelationalOperator.LESS_THAN, expectedValue);
	}

	public static ConfigurationCondition lessThanOrEquals(String key, Object expectedValue) {
		return new ConfigurationCondition(key, RelationalOperator.LESS_THAN_OR_EQUALS, expectedValue);
	}

	public static ConfigurationCondition expr(String key, RelationalOperator logicalOperator, Object expectedValue) {
		return new ConfigurationCondition(key, logicalOperator, expectedValue);
	}

	private Context context;
	private String key;
	private RelationalOperator logicalOperator = RelationalOperator.EQUALS;
	private Object expectedValue;

	private ConfigurationCondition(String key, RelationalOperator logicalOperator, Object expectedValue) {
		assert key != null && !key.isEmpty() && logicalOperator != null;
		this.key = key;
		this.logicalOperator = logicalOperator;
		this.expectedValue = expectedValue;
	}

	@Override
	public boolean eval() {
		if (context == null) {
			throw new ConditionEvaluationException("cannot evaluate condition without a context");
		}
		if (!context.getConfiguration().containsKey(key)) {
			throw new ConditionEvaluationException("missing key " + key + " in data dictionary");
		}
		boolean result = false;
		Object actualValue = context.getConfiguration().get(key);
		switch (logicalOperator) {
		case EQUALS:
			result = _equals(expectedValue, actualValue);
		case GREATER_THAN:
			result = _greaterThan(expectedValue, actualValue);
			break;
		case GREATER_THAN_OR_EQUALS:
			result = _equals(expectedValue, actualValue) || _greaterThan(expectedValue, actualValue);
			break;
		case LESS_THAN:
			result = _lessThan(expectedValue, actualValue);
			break;
		case LESS_THAN_OR_EQUALS:
			result = _equals(expectedValue, actualValue) || _lessThan(expectedValue, actualValue);
			break;
		case NOT_EQUALS:
			result = !_equals(expectedValue, actualValue);
			break;
		}
		return result;
	}

	private boolean _equals(Object a, Object b) {
		if (a == null && b == null) {
			return true;
		}
		if (a == null && b != null) {
			return false;
		}
		if (a != null && b == null) {
			return false;
		}
		return a.equals(b);
	}

	private boolean _greaterThan(Object a, Object b) {
		if (a == null || b == null) {
			return false;
		}
		if (a instanceof String && b instanceof String) {
			String x = (String) a;
			String y = (String) b;
			return x.compareTo(y) > 0;
		}
		double x = NumberUtil.toDouble(a);
		double y = NumberUtil.toDouble(b);
		return Double.compare(x, y) > 0;
	}

	private boolean _lessThan(Object a, Object b) {
		if (a == null || b == null) {
			return false;
		}
		if (a instanceof String && b instanceof String) {
			String x = (String) a;
			String y = (String) b;
			return x.compareTo(y) < 0;
		}
		double x = NumberUtil.toDouble(a);
		double y = NumberUtil.toDouble(b);
		return Double.compare(x, y) < 0;
	}

	@Override
	public void setContext(Context context) {
		Assertions.notNull(context, "context");
		this.context = context;
	}
}
