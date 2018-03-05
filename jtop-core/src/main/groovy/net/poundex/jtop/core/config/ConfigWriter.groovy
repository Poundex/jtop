package net.poundex.jtop.core.config

import com.moandjiezana.toml.TomlWriter
import groovy.transform.CompileStatic

import java.nio.file.Files
import java.nio.file.Path

@CompileStatic
trait ConfigWriter
{
	abstract Path getWriteTarget()

	void write()
	{
		if( ! Files.isRegularFile(writeTarget))
			Files.createDirectories(writeTarget.parent)

		writeTarget.withOutputStream { os ->
			Map<String, Object> configProperties = this.properties
			["class", "writeTarget"].each(configProperties.&remove)
			new TomlWriter().write(configProperties, os)
		}
	}
}
