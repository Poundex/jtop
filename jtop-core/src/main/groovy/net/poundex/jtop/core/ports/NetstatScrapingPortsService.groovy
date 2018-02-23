package net.poundex.jtop.core.ports

import groovy.transform.CompileStatic

import java.util.regex.Matcher
import java.util.regex.Pattern

@CompileStatic
class NetstatScrapingPortsService implements PortsService
{
	private static final Pattern ROW_PATTERN = ~/([a-zA-Z0-9]+)\s+[0-9]+\s+[0-9]+\s+([0-9:.]+)\s+[0-9:.*]+\s+LISTEN\s+([0-9]+)\\/.*/

	@Override
	Map<Integer, List<BindingInfo>> getProcessPorts()
	{
		String raw = "netstat -tlpn".execute().text

		return raw.readLines().collect { line ->
			Matcher things = line =~ ROW_PATTERN
			if( ! things.matches())
				return null

			String proto = things.group(1)
			String address = things.group(2)
			int pid = things.group(3).toInteger()
			new BindingInfo(pid, proto, address)
		}.findAll().groupBy { it.pid }
	}
}
