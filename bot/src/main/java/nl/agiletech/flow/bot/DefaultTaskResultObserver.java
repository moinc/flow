package nl.agiletech.flow.bot;

import java.util.LinkedHashMap;
import java.util.Map;

import nl.agiletech.flow.bot.exec.TaskResultObserver;
import nl.agiletech.flow.project.types.Instruction;

public class DefaultTaskResultObserver implements TaskResultObserver {
	final Map<String, Object> result = new LinkedHashMap<>();

	@Override
	public void observe(Instruction instruction, int status, String statusMessage, Map<String, Object> taskResult) {
		result.put(instruction.getKey(), taskResult);
	}

	public Map<String, Object> getResult() {
		return result;
	}
}
