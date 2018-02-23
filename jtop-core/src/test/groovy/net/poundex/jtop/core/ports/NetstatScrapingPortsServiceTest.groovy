package net.poundex.jtop.core.ports

import spock.lang.Specification

class NetstatScrapingPortsServiceTest extends Specification
{
	def "GetProcessPorts"()
	{
		when:
		new NetstatScrapingPortsService().processPorts.each { k, v ->
			println k
			println v
			println " "
		}

		then:
		true
	}
}
