package net.poundex.jtop.core.snapshot

import groovy.transform.CompileStatic
import net.poundex.jtop.core.jps.JpsService
import net.poundex.jtop.core.jps.RunningVirtualMachineInfo
import net.poundex.jtop.core.ports.BindingInfo
import net.poundex.jtop.core.ports.PortsService
import net.poundex.jtop.core.ps.RunningProcessInfo
import net.poundex.jtop.core.ps.SystemProcessService

@CompileStatic
class SnapshotService
{
	private final JpsService jpsService
	private final SystemProcessService systemProcessService
	private final PortsService portsService

	SnapshotService(JpsService jpsService, SystemProcessService systemProcessService, PortsService portsService)
	{
		this.jpsService = jpsService
		this.systemProcessService = systemProcessService
		this.portsService = portsService
	}

	Snapshot getSnapshot()
	{
		Map<Integer, RunningProcessInfo> procs = systemProcessService.runningProcesses
		List<RunningVirtualMachineInfo> vms = jpsService.runningVMs
		Map<Integer, List<BindingInfo>> ports = portsService.processPorts
		new Snapshot(vms.collectEntries { vm ->
			[vm.pid, new RunningApplicationDetails(
					vm.pid,
					procs[vm.pid],
					vm,
					ports.getOrDefault(vm.pid, Collections.<BindingInfo>emptyList()))
			]
		})
	}
}
