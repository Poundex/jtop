package net.poundex.jtop.core.ps

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@CompileStatic
@Immutable
class RunningProcessInfo
{
	int pid
	String user
	String priority
	String niceness

	String virtual
	String reserved
	String shared

	String status
	String cpu
	String mem
	String cputime
	String command
}
