package net.poundex.jtop.core

import net.poundex.jtop.core.jps.DefaultJpsService
import spock.lang.Specification

class JpsServiceTest extends Specification {
	def "GetRunningVMs"() {
		when:
		DefaultJpsService.instance.runningVMs.each {
			println it
		}

		then:
		true
	}
}
