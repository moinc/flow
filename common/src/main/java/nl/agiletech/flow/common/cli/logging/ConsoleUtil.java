package nl.agiletech.flow.common.cli.logging;

import java.io.Closeable;
import java.io.PrintStream;

public class ConsoleUtil implements Closeable {
	public class ConsoleMessage {
		private static final String ANSI_ESC = "\u001B[";
		private static final String ANSI_RESET = "\u001B[0m";
		private static final int ANSI_FOREGROUND = 30;
		private static final int ANSI_BACKGROUND = 40;
		private static final int ANSI_STYLE = 0;

		final ConsoleUtil loggerUtil;
		final StringBuilder sb = new StringBuilder();
		boolean shouldReset = false;

		public ConsoleMessage(ConsoleUtil loggerUtil) {
			this.loggerUtil = loggerUtil;
		}

		public ConsoleUtil print() {
			reset();
			loggerUtil.printStream.println(sb.toString());
			loggerUtil.msg = null;
			return loggerUtil;
		}

		public ConsoleMessage foreground(Color color) {
			reset();
			String ansiCode = ANSI_ESC + (ANSI_FOREGROUND + color.getValue()) + "m";
			sb.append(ansiCode);
			shouldReset = true;
			return this;
		}

		public ConsoleMessage background(Color color) {
			reset();
			String ansiCode = ANSI_ESC + (ANSI_BACKGROUND + color.getValue()) + "m";
			sb.append(ansiCode);
			shouldReset = true;
			return this;
		}

		public ConsoleMessage style(Style style) {
			reset();
			String ansiCode = ANSI_ESC + (ANSI_STYLE + style.getValue()) + "m";
			sb.append(ansiCode);
			shouldReset = true;
			return this;
		}

		public ConsoleMessage reset() {
			if (shouldReset) {
				sb.append(ANSI_RESET);
				shouldReset = false;
			}
			return this;
		}

		public ConsoleMessage append(String text) {
			assert text != null;
			sb.append(text);
			return this;
		}

		public ConsoleMessage appendLn(String text) {
			assert text != null;
			sb.append(text);
			sb.append("\n");
			return this;
		}
	}

	public static final ConsoleUtil OUT = wrap(System.out);

	public static ConsoleUtil wrap(PrintStream printStream) {
		return new ConsoleUtil(printStream);
	}

	private final PrintStream printStream;
	private ConsoleMessage msg;

	public ConsoleUtil(PrintStream printStream) {
		this.printStream = printStream;
	}

	public ConsoleMessage normal() {
		return createMessage(Color.DEFAULT, Color.DEFAULT, Style.DEFAULT);
	}

	public ConsoleMessage warning() {
		return createMessage(Color.YELLOW, Color.DEFAULT, Style.DEFAULT);
	}

	public ConsoleMessage error() {
		return createMessage(Color.RED, Color.DEFAULT, Style.DEFAULT);
	}

	public ConsoleMessage faint() {
		return createMessage(Color.DEFAULT, Color.DEFAULT, Style.FAINT);
	}

	public ConsoleMessage bold() {
		return createMessage(Color.DEFAULT, Color.DEFAULT, Style.BOLD);
	}

	private ConsoleMessage createMessage(Color foreground, Color background, Style style) {
		ensureClosed();
		msg = new ConsoleMessage(this);
		if (foreground != Color.DEFAULT) {
			msg.foreground(foreground);
		}
		if (background != Color.DEFAULT) {
			msg.background(background);
		}
		if (style != Style.DEFAULT) {
			msg.style(style);
		}
		return msg;
	}

	private void ensureClosed() {
		if (msg != null) {
			msg.print();
			msg = null;
		}
	}

	@Override
	public void close() {
		ensureClosed();
	}
}
