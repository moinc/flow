/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.cli;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Table {
	public static Table getInstance(int columns) {
		return new Table(columns);
	}

	private final int columns;
	private final List<String[]> rows = new ArrayList<>();
	private final List<String> columnSeparators = new ArrayList<>();

	private Table(int columns) {
		this.columns = columns;
	}

	private String[] getRow(int row) {
		if (row >= rows.size()) {
			rows.add(new String[columns]);
		}
		return rows.get(row);
	}

	private List<String[]> getPaddedValues() {
		List<String[]> pv = new ArrayList<>();
		int[] len = new int[columns];
		for (String[] row : rows) {
			for (int col = 0; col < columns; col++) {
				len[col] = Math.max(len[col], notNull(row[col]).length());
			}
		}
		for (String[] row : rows) {
			String[] pr = new String[columns];
			for (int col = 0; col < columns; col++) {
				pr[col] = rpad(notNull(row[col]), len[col]);
			}
			pv.add(pr);
		}
		return pv;
	}

	private String notNull(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	private String rpad(String value, int len) {
		String pv = value;
		for (int x = pv.length(); x < len; x++) {
			pv += " ";
		}
		return pv;
	}

	private String getColumnSeparator(int col) {
		return col < columnSeparators.size() ? columnSeparators.get(col) : ":";
	}

	public void setColumnSeparators(String... separators) {
		this.columnSeparators.clear();
		for (String sep : separators) {
			this.columnSeparators.add(sep);
		}
	}

	public boolean addValue(int row, int column, String value) {
		if (column >= 0 && column < columns) {
			String[] r = getRow(row);
			r[column] = notNull(value);
			return true;
		}
		return false;
	}

	public boolean addRow(String... values) {
		if (values != null && values.length == columns) {
			rows.add(values);
			return true;
		}
		return false;
	}

	public void print(PrintStream out) {
		List<String[]> paddedRows = getPaddedValues();
		for (String[] row : paddedRows) {
			for (int col = 0; col < columns; col++) {
				out.print(row[col]);
				if (col < columns - 1) {
					out.print(getColumnSeparator(col));
				}
			}
			out.println("");
		}
	}

}
