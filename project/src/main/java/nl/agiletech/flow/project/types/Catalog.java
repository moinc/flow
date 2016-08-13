/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.util.LinkedHashMap;
import java.util.Map;

public class Catalog {
	Map<String, Instruction> instructions = new LinkedHashMap<>();

	public void addTask(Instruction instruction) {
		instructions.put(instruction.getId(), instruction);
	}

	public Map<String, Instruction> getInstructions() {
		return instructions;
	}
}
