package net.poundex.jtop.core.snapshot

import groovy.transform.CompileStatic
import groovy.transform.Immutable
import net.poundex.jtop.core.jps.RunningVirtualMachineInfo
import net.poundex.jtop.core.ports.BindingInfo
import net.poundex.jtop.core.ps.RunningProcessInfo

@CompileStatic
@Immutable
class RunningApplicationDetails
{
	int pid
	RunningProcessInfo processInfo
	RunningVirtualMachineInfo vmInfo
	List<BindingInfo> ports

	String getPidString()
	{
		return pid.toString()
	}
}
