package nl.agiletech.flow.common.cli.logging;

public enum Style {
	DEFAULT(0), BOLD(1), FAINT(2), ITALIC(3), UNDERLINE(4);

	private final int value;

	Style(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
