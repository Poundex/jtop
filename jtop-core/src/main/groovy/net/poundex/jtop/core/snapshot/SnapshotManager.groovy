package net.poundex.jtop.core.snapshot

import groovy.transform.CompileStatic
import net.poundex.jtop.core.config.JtopConfig
import org.springframework.beans.factory.InitializingBean

@CompileStatic
class SnapshotManager implements InitializingBean
{
	private final SnapshotService snapshotService
	private final List<SnapshotListener> snapshotListeners = []
	private final JtopConfig jtopConfig

	SnapshotManager(SnapshotService snapshotService, JtopConfig jtopConfig)
	{
		this.snapshotService = snapshotService
		this.jtopConfig = jtopConfig
	}

	void addSnapshotListener(SnapshotListener snapshotListener)
	{
		snapshotListeners << snapshotListener
	}

	@Override
	void afterPropertiesSet()
	{
		Thread.startDaemon {
			while(true)
			{
				snapshotListeners*.receiveSnapshot(snapshotService.snapshot)
				Thread.sleep(jtopConfig.tickInterval)
			}
		}
	}
}
