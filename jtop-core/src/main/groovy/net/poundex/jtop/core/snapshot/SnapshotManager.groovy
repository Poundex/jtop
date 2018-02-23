package net.poundex.jtop.core.snapshot

import groovy.transform.CompileStatic
import org.springframework.beans.factory.InitializingBean

@CompileStatic
class SnapshotManager implements InitializingBean
{
	private final SnapshotService snapshotService
	private final List<SnapshotListener> snapshotListeners = []

	SnapshotManager(SnapshotService snapshotService)
	{
		this.snapshotService = snapshotService
	}

	void addSnapshotListener(SnapshotListener snapshotListener) {
		snapshotListeners << snapshotListener
	}

	@Override
	void afterPropertiesSet()
	{
		Thread.startDaemon {
			while(true)
			{
				snapshotListeners*.receiveSnapshot(snapshotService.snapshot)
				Thread.sleep(800)
			}
		}
	}
}
