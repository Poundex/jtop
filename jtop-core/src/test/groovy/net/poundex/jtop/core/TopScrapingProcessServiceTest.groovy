package net.poundex.jtop.core

import net.poundex.jtop.core.ps.TopScrapingProcessService
import spock.lang.Specification

class TopScrapingProcessServiceTest extends Specification {
	void "test1"()
	{
		expect:
		new TopScrapingProcessService().runningProcesses.each {
			println it
		}
		true
	}
}
