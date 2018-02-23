package net.poundex.jtop.core.config

import com.moandjiezana.toml.Toml
import spock.lang.Specification

class JtopConfigTest extends Specification
{
	void "test thing"()
	{
		when:
		JtopConfig config = new Toml().read(getClass().getResourceAsStream("/jtop.toml")).to(JtopConfig)
		int four = 2 + 2

		then:
		true
	}
}
