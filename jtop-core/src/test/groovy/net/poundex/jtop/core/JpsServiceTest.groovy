package net.poundex.jtop.core

import spock.lang.Specification

class JpsServiceTest extends Specification {
	def "GetRunningVMs"() {
		when:
		JpsService.instance.runningVMs.each {
			println it
		}

		then:
		true
	}
}
