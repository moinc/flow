/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.common.io;

import java.io.File;
import java.io.IOException;

public class TempFile {
	public static File create() throws IOException {
		return File.createTempFile("flow", null);
	}
}
