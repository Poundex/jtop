package net.poundex.jtop.core

import spock.lang.Specification

class SystemProcessServiceTest extends Specification {
	void "test1"()
	{
		expect:
		new SystemProcessService().runningProcesses.each {
			println it
		}
		true
	}
}
