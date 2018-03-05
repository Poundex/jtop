package net.poundex.jtop.core

import com.moandjiezana.toml.Toml
import groovy.transform.CompileStatic
import net.poundex.jtop.core.app.ApplicationService
import net.poundex.jtop.core.app.DefaultApplicationService
import net.poundex.jtop.core.config.JtopConfig
import net.poundex.jtop.core.jps.DefaultJpsService
import net.poundex.jtop.core.jps.JpsService
import net.poundex.jtop.core.ports.NetstatScrapingPortsService
import net.poundex.jtop.core.ports.PortsService
import net.poundex.jtop.core.ps.SystemProcessService
import net.poundex.jtop.core.ps.TopScrapingProcessService
import net.poundex.jtop.core.snapshot.SnapshotManager
import net.poundex.jtop.core.snapshot.SnapshotService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@CompileStatic
@Configuration
class JtopContextConfiguration
{
	@Bean
	DefaultJpsService jpsService()
	{
		return new DefaultJpsService()
	}

	@Bean
	SystemProcessService systemProcessService()
	{
		return new TopScrapingProcessService()
	}

	@Bean
	SnapshotService snapshotService(JpsService jpsService, SystemProcessService systemProcessService, PortsService portsService)
	{
		return new SnapshotService(jpsService, systemProcessService, portsService)
	}

	@Bean
	SnapshotManager snapshotManager(SnapshotService snapshotService, JtopConfig jtopConfig)
	{
		return new SnapshotManager(snapshotService, jtopConfig)
	}

	@Bean
	ApplicationService applicationService()
	{
		return new DefaultApplicationService()
	}

	@Bean
	PortsService portsService()
	{
		return new NetstatScrapingPortsService()
	}

	@Bean
	JtopConfig jtopConfig()
	{
		Toml toml = new Toml()
		Path userConfig = Paths.get(System.properties['user.home'].toString()).
				resolve(".jtop/").
				resolve("jtop.toml")
		JtopConfig config
		if(Files.isRegularFile(userConfig))
			config = toml.read(Files.newInputStream(userConfig)).to(JtopConfig)
		else
			config = toml.read(getClass().getResourceAsStream("/jtop.toml")).to(JtopConfig)
		config.writeTarget = userConfig
		return config
	}
}
