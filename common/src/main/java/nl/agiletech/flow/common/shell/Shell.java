/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Shell {
	public static ShellResult execute(final File workingDirectory, final String... args) {
		try {

			ArrayList<String> command = new ArrayList<String>();
			command.add("bash");
			command.add("-c");
			for (String arg : args) {
				command.add(arg);
			}

			long time = System.currentTimeMillis();

			Process process = new ProcessBuilder(command).directory(workingDirectory).start();
			int exitCode = process.waitFor();

			List<String> output = readStream(process.getInputStream());
			List<String> errors = readStream(process.getErrorStream());

			time = System.currentTimeMillis() - time;

			// TODO: timeout?

			return new ShellResult(exitCode, output, errors, time);
		} catch (Exception e) {

		}
		return null;
	}

	private static List<String> readStream(InputStream inputStream) throws IOException {
		ArrayList<String> output = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		while ((line = br.readLine()) != null) {
			output.add(line);
		}
		return output;
	}
}
