package net.poundex.jtop.lanterna

import com.googlecode.lanterna.gui2.BorderLayout
import com.googlecode.lanterna.gui2.table.Table
import com.googlecode.lanterna.gui2.table.TableModel
import groovy.transform.CompileStatic
import net.poundex.jtop.core.config.JtopConfig
import net.poundex.jtop.core.snapshot.Snapshot
import net.poundex.jtop.core.snapshot.SnapshotListener
import net.poundex.jtop.core.snapshot.SnapshotManager
import org.springframework.beans.factory.InitializingBean

@CompileStatic
class ProcessTable extends Table<String> implements SnapshotListener, InitializingBean
{
	private final SnapshotManager snapshotManager
	private final JtopConfig jtopConfig
	private int[] rowToPid

	ProcessTable(SnapshotManager snapshotManager, JtopConfig jtopConfig)
	{
		super("Loading...")
		this.snapshotManager = snapshotManager
		this.jtopConfig = jtopConfig
		setLayoutData(BorderLayout.Location.CENTER)
	}

	@Override
	void receiveSnapshot(Snapshot snapshot)
	{
		rowToPid = new int[snapshot.applications.size()]
		TableModel<String> newTableModel = new TableModel<>(jtopConfig.columns*.displayString as String[])
		snapshot.applications.findAll { k, v ->
			! jtopConfig.filterExclude.any { fex ->
				v.vmInfo.mainClass.startsWith(fex)
			}
		}.eachWithIndex { pid, details, row ->
			newTableModel.addRow(jtopConfig.columns.collect { col ->
				col.render(details)
			})
			rowToPid[row] = pid
		}
		tableModel = newTableModel
	}

	@Override
	void afterPropertiesSet()
	{
		snapshotManager.addSnapshotListener(this)
	}

	Integer getSelectedPid()
	{
		return rowToPid[selectedRow]
	}
}
