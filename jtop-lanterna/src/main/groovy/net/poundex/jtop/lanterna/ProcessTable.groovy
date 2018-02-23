package net.poundex.jtop.lanterna

import com.googlecode.lanterna.gui2.BorderLayout
import com.googlecode.lanterna.gui2.table.Table
import com.googlecode.lanterna.gui2.table.TableModel
import groovy.transform.CompileStatic
import net.poundex.jtop.core.snapshot.Snapshot
import net.poundex.jtop.core.snapshot.SnapshotListener
import net.poundex.jtop.core.snapshot.SnapshotManager
import org.springframework.beans.factory.InitializingBean

@CompileStatic
class ProcessTable extends Table<String> implements SnapshotListener, InitializingBean
{
	private final SnapshotManager snapshotManager

	ProcessTable(SnapshotManager snapshotManager)
	{
		super("Loading...")
		this.snapshotManager = snapshotManager
		setLayoutData(BorderLayout.Location.CENTER)
	}

	@Override
	void receiveSnapshot(Snapshot snapshot)
	{
		TableModel<String> newTableModel = new TableModel<>("PID", "User", "Pr", "N", "Virt", "Res", "Shr", "S", "%CPU", "%Mem", "Time", "Ports", "Application")
		snapshot.applications.each { pid, details ->
			newTableModel.addRow(
					pid.toString(),
					details.processInfo.user,
					details.processInfo.priority,
					details.processInfo.niceness,
					details.processInfo.virtual,
					details.processInfo.reserved,
					details.processInfo.shared,
					details.processInfo.status,
					details.processInfo.cpu,
					details.processInfo.mem,
					details.processInfo.cputime,
					details.ports.collect { "${it.protocol}:${it.port}" }.join(', '),
					details.vmInfo.mainClass)
		}
		tableModel = newTableModel
	}

	@Override
	void afterPropertiesSet()
	{
		snapshotManager.addSnapshotListener(this)
	}
}
