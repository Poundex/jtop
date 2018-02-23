package net.poundex.jtop.core.jps

import groovy.transform.CompileStatic
import sun.jvmstat.monitor.*

@CompileStatic
class DefaultJpsService implements JpsService
{
	@Override
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
}

// /:([0-9]+)\s.*LISTEN\s+([0-9]+)\/.*/
