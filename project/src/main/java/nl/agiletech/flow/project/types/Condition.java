/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

public interface Condition extends TakesContext {
	public static final Condition TRUE = new Condition() {
		@Override
		public boolean eval() {
			return true;
		}

		@Override
		public void setContext(Context context) {
		}
	};

	public boolean eval();
}
