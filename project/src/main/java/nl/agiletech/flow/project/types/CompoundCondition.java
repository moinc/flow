package nl.agiletech.flow.project.types;

import java.util.ArrayList;
import java.util.List;

public class CompoundCondition implements Condition {
	public static final CompoundCondition AND = new CompoundCondition(BooleanOperator.AND);
	public static final CompoundCondition OR = new CompoundCondition(BooleanOperator.OR);

	private final List<Condition> operands = new ArrayList<>();
	private BooleanOperator booleanOperator = BooleanOperator.AND;

	public CompoundCondition(BooleanOperator booleanOperator) {
		this.booleanOperator = booleanOperator;
	}

	@Override
	public boolean eval() {
		boolean result = false;
		switch (booleanOperator) {
		case AND:
			result = true;
			for (Condition condition : operands) {
				if (!condition.eval()) {
					result = false;
					break;
				}
			}
		case OR:
			result = false;
			for (Condition condition : operands) {
				if (condition.eval()) {
					result = true;
					break;
				}
			}
			break;
		}
		return result;
	}

	@Override
	public void setContext(Context context) {
		assert context != null;
		for (Condition operand : operands) {
			operand.setContext(context);
		}
	}

}
