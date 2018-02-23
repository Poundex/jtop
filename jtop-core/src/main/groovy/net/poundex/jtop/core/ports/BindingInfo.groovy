package net.poundex.jtop.core.ports

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@Immutable
@CompileStatic
class BindingInfo
{
	int pid
	String protocol
	String address

	String getPort()
	{
		return address.substring(address.lastIndexOf(':') + 1)
	}
}
