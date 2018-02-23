package net.poundex.jtop.core.snapshot

import groovy.transform.CompileStatic
import net.poundex.jtop.core.jps.JpsService
import net.poundex.jtop.core.jps.RunningVirtualMachineInfo
import net.poundex.jtop.core.ps.RunningProcessInfo
import net.poundex.jtop.core.ps.SystemProcessService

@CompileStatic
class SnapshotService
{
	private final JpsService jpsService
	private final SystemProcessService systemProcessService

	SnapshotService(JpsService jpsService, SystemProcessService systemProcessService)
	{
		this.jpsService = jpsService
		this.systemProcessService = systemProcessService
	}

	Snapshot getSnapshot()
	{
		Map<Integer, RunningProcessInfo> procs = systemProcessService.runningProcesses
		List<RunningVirtualMachineInfo> vms = jpsService.runningVMs
		new Snapshot(vms.collectEntries { vm ->
			[vm.pid, new RunningApplicationDetails(vm.pid, procs[vm.pid], vm)]
		})
	}
}
