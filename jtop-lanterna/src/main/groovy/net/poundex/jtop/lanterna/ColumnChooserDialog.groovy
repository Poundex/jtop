package net.poundex.jtop.lanterna

import com.googlecode.lanterna.gui2.*
import groovy.transform.CompileStatic
import net.poundex.jtop.core.config.JtopConfig
import net.poundex.jtop.core.config.TableColumn

@CompileStatic
class ColumnChooserDialog extends JtopModalDialog
{
	private final JtopConfig jtopConfig
	private List<TableColumn> columnsInOrder
	private Integer nextFocus
	private Button nextFocusable
	private Map<TableColumn, Boolean> visibleColumns

	ColumnChooserDialog(JtopConfig jtopConfig)
	{
		super("Columns")
		this.jtopConfig = jtopConfig
		updateColumns()
		setComponent(panel)
	}

	private updateColumns()
	{
		columnsInOrder = jtopConfig.columns + (TableColumn.values() - jtopConfig.columns).toList()
		visibleColumns = columnsInOrder.collectEntries { col ->
			[col, jtopConfig.columns.contains(col)]
		}
	}

	private Panel getPanel()
	{
		Panel panel = new Panel(new BorderLayout())
		Panel cols = new Panel()
		int padTo = TableColumn.values().collect({ TableColumn it -> it.displayString.length() }).max() + 1
		int numCols = TableColumn.values().size()
		columnsInOrder.eachWithIndex { TableColumn col, int colIdx ->
			Panel p2 = new Panel(new LinearLayout(Direction.HORIZONTAL))
			CheckBox checkBox = new CheckBox(col.displayString.padRight(padTo))
			checkBox.checked = visibleColumns[col]
			checkBox.addListener({
				visibleColumns[col] = it
			})
			p2.addComponent(checkBox)
			if(colIdx != 0) {
				Button button = moveButton("Up", colIdx, col, 1)
				if(nextFocus == colIdx)
					nextFocusable = button
				p2.addComponent(button)
			}
			if(colIdx != numCols - 1) {
				Button button = moveButton("Down", colIdx, col, -1)
				if(nextFocus == -colIdx)
					nextFocusable = button
				p2.addComponent(button)
			}
			cols.addComponent(p2)
		}

		cols.setLayoutData(BorderLayout.Location.CENTER)
		panel.addComponent(cols)

		Button done = new Button("Done", this.&saveAndClose)
		done.setLayoutData(BorderLayout.Location.BOTTOM)
		panel.addComponent(done)

		return panel
	}

	private Button moveButton(String label, int colIdx, TableColumn col, int direction)
	{
		return new Button(label, {
					nextFocus = (colIdx - direction) * direction
					TableColumn swapTarget = columnsInOrder.get(colIdx - direction)
					columnsInOrder.set(colIdx - direction, col)
					columnsInOrder.set(colIdx, swapTarget)
					setComponent(getPanel())
					if(nextFocusable)
						nextFocusable.takeFocus()
				})
	}

	@Override
	void saveAndClose()
	{
		jtopConfig.columns.clear()
		jtopConfig.columns.addAll columnsInOrder.findAll { visibleColumns[it] }
		jtopConfig.write()
		close()
	}
}
