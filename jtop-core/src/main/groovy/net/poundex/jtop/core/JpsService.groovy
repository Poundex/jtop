package net.poundex.jtop.core

import groovy.transform.Immutable
import sun.jvmstat.monitor.*

@Singleton
class JpsService
{
	List<RunningVirtualMachineInfo> getRunningVMs()
	{
		HostIdentifier hostIdentifier = new HostIdentifier('localhost')
		MonitoredHost monitoredHost = MonitoredHost.getMonitoredHost(hostIdentifier)
		monitoredHost.activeVms().collect { vmId ->
			try {
				VmIdentifier vmIdentifier = new VmIdentifier("//${vmId}?mode=r")
				MonitoredVm monitoredVm = monitoredHost.getMonitoredVm(vmIdentifier, 0)

				new RunningVirtualMachineInfo(
						vmId,
						MonitoredVmUtil.mainClass(monitoredVm, true),
						MonitoredVmUtil.mainArgs(monitoredVm),
						MonitoredVmUtil.jvmArgs(monitoredVm),
						MonitoredVmUtil.jvmFlags(monitoredVm),
						MonitoredVmUtil.vmVersion(monitoredVm))
			} catch (Exception ignored) {
				null
			}
		}.findAll()
	}

	@Immutable
	static class RunningVirtualMachineInfo
	{
		int pid
		String mainClass
		String mainArgs
		String jvmArgs
		String jvmFlags
		String vmVersion
	}
}

// /:([0-9]+)\s.*LISTEN\s+([0-9]+)\/.*/
