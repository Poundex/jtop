package net.poundex.jtop.core.jps

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@Immutable
@CompileStatic
class RunningVirtualMachineInfo
{
	int pid
	String mainClass
	String mainArgs
	String jvmArgs
	String jvmFlags
	String vmVersion
}
