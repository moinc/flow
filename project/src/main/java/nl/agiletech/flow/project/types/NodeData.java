/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class NodeData extends ConfigurationFile {
	private static final Logger LOG = Logger.getLogger(NodeData.class.getName());

	public static final NodeData EMPTY = new NodeData();

	public static NodeData loadFrom(File file) throws FileNotFoundException, IOException {
		return _loadFrom(file, new NodeData());
	}

	public static NodeData loadFrom(InputStream src) throws JsonParseException, JsonMappingException, IOException {
		return _loadFrom(src, new NodeData());
	}

	private NodeData() {
	}
}
