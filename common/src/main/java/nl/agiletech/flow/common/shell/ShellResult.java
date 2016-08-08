/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.shell;

import java.util.ArrayList;
import java.util.List;

public class ShellResult {
	private final int exitCode;
	private List<String> output = new ArrayList<>();
	private List<String> errors = new ArrayList<>();
	private long time;

	public ShellResult(int exitCode, List<String> output, List<String> errors, long time) {
		super();
		this.exitCode = exitCode;
		this.output = output;
		this.errors = errors;
		this.time = time;
	}

	public int getExitCode() {
		return exitCode;
	}

	public boolean isSuccess() {
		return exitCode == 0;
	}

	public List<String> getOutput() {
		return output;
	}

	public List<String> getErrors() {
		return errors;
	}

	public long getTime() {
		return time;
	}

}
