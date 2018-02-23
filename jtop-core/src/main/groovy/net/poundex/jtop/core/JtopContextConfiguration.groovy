package net.poundex.jtop.core

import groovy.transform.CompileStatic
import net.poundex.jtop.core.app.ApplicationService
import net.poundex.jtop.core.app.DefaultApplicationService
import net.poundex.jtop.core.jps.DefaultJpsService
import net.poundex.jtop.core.jps.JpsService
import net.poundex.jtop.core.ps.SystemProcessService
import net.poundex.jtop.core.ps.TopScrapingProcessService
import net.poundex.jtop.core.snapshot.SnapshotManager
import net.poundex.jtop.core.snapshot.SnapshotService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
	SnapshotService snapshotService(JpsService jpsService, SystemProcessService systemProcessService)
	{
		return new SnapshotService(jpsService, systemProcessService)
	}

	@Bean
	SnapshotManager snapshotManager(SnapshotService snapshotService)
	{
		return new SnapshotManager(snapshotService)
	}

	@Bean
	ApplicationService applicationService()
	{
		return new DefaultApplicationService()
	}
}
