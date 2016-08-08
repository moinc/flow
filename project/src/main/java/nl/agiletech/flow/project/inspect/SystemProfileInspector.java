/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.inspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.agiletech.flow.project.types.Context;
import nl.agiletech.flow.project.types.Inspector;

// Mac OSX: use the system_profiler utility to retrieve system profiler information.
//	boot_mode (string) — The boot mode.
//	boot_rom_version (string) — The boot ROM version.
//	boot_volume (string) — The boot volume.
//	computer_name (string) — The name of the computer.
//	cores (string) — The total number of processor cores.
//	hardware_uuid (string) — The hardware unique identifier.
//	kernel_version (string) — The version of the kernel.
//	l2_cache_per_core (string) — The size of the processor per-core L2 cache.
//	l3_cache (string) — The size of the processor L3 cache.
//	memory (string) — The size of the system memory.
//	model_identifier (string) — The identifier of the computer model.
//	model_name (string) — The name of the computer model.
//	processor_name (string) — The model name of the processor.
//	processor_speed (string) — The speed of the processor.
//	processors (string) — The total number of processors.
//	secure_virtual_memory (string) — Whether or not secure virtual memory is enabled.
//	serial_number (string) — The serial number of the computer.
//	smc_version (string) — The System Management Controller (SMC) version.
//	system_version (string) — The operating system version.
//	uptime (string) — The uptime of the system.
//	username (string) — The name of the user running facter.
public class SystemProfileInspector extends Inspector<Map<String, Object>> {
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> render(Context context) throws IOException {
		String key = getScriptResourceName();
		return (Map<String, Object>) context.getNodeData().get(key, new HashMap<String, Object>());
	}
}
