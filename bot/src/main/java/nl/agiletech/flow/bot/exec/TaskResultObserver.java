package nl.agiletech.flow.bot.exec;

import java.util.Map;

import nl.agiletech.flow.project.types.Instruction;

public interface TaskResultObserver {
	void observe(Instruction instruction, int status, String statusMessage, Map<String, Object> taskResult);
}
