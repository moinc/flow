package nl.agiletech.flow.common.cli.logging;

public enum Color {
	BLACK(0), RED(1), GREEN(2), YELLOW(3), BLUE(4), PURPLE(5), CYAN(6), WHITE(7), DEFAULT(9);

	private final int value;

	Color(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
