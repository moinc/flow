/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Catalog implements Comparable<Catalog> {
	public static final Catalog EMPTY = new Catalog();

	String sessionId;
	CatalogStatus catalogStatus;
	final Map<String, Instruction> instructions = new LinkedHashMap<>();

	public Catalog() {
		this("", CatalogStatus.EMPTY);
	}

	public Catalog(String sessionId, CatalogStatus catalogStatus) {
		this.sessionId = sessionId;
		this.catalogStatus = catalogStatus;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public CatalogStatus getCatalogStatus() {
		return catalogStatus;
	}

	public void setCatalogStatus(CatalogStatus catalogStatus) {
		this.catalogStatus = catalogStatus;
	}

	public void addTask(Instruction instruction) {
		instructions.put(instruction.getId(), instruction);
	}

	public Map<String, Instruction> getInstructions() {
		return instructions;
	}

	@Override
	public int compareTo(Catalog other) {
		if (other != null) {
			int cs = Integer.compare(instructions.size(), other.instructions.size());
			if (cs != 0) {
				return cs;
			}
			List<String> keys = new ArrayList<>(instructions.keySet());
			List<String> otherKeys = new ArrayList<>(other.instructions.keySet());
			if (!keys.containsAll(otherKeys)) {
				return -1;
			}
			if (!otherKeys.containsAll(keys)) {
				return 1;
			}
			// check order of keys
			for (int k = 0; k < keys.size(); k++) {
				int ck = keys.get(k).compareTo(otherKeys.get(k));
				if (ck != 0) {
					return ck;
				}
			}
			List<Instruction> values = new ArrayList<>(instructions.values());
			List<Instruction> otherValues = new ArrayList<>(other.instructions.values());
			for (int i = 0; i < values.size(); i++) {
				if (!values.get(i).equals(otherValues.get(i))) {
					return -1;
				}
			}
			return 0;
		}
		return -1;
	}
}
