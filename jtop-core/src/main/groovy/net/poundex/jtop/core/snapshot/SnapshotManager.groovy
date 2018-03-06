package net.poundex.jtop.core.snapshot

import groovy.transform.CompileStatic
import net.poundex.jtop.core.config.JtopConfig
import org.springframework.beans.factory.InitializingBean

@CompileStatic
class SnapshotManager implements InitializingBean
{
	private Snapshot lastSnapshot

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
				Snapshot snapshot = snapshotService.snapshot
				lastSnapshot = snapshot
				snapshotListeners*.receiveSnapshot(snapshot)
				Thread.sleep(jtopConfig.tickInterval)
			}
		}
	}

	Snapshot getLastSnapshot()
	{
		return lastSnapshot
	}
}
