package net.poundex.jtop.lanterna

import com.googlecode.lanterna.gui2.Window
import com.googlecode.lanterna.gui2.WindowListenerAdapter
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import groovy.transform.CompileStatic
import net.poundex.jtop.core.app.ApplicationService
import org.springframework.beans.factory.ObjectFactory

import java.util.concurrent.atomic.AtomicBoolean

@CompileStatic
class KeyListener extends WindowListenerAdapter
{
	private final ApplicationService applicationService
	private final ProcessTable processTable
	private final ObjectFactory<ColumnChooserDialog> columnChooserDialogFactory

	KeyListener(ApplicationService applicationService, ProcessTable processTable, ObjectFactory<ColumnChooserDialog> columnChooserDialogFactory)
	{
		this.applicationService = applicationService
		this.processTable = processTable
		this.columnChooserDialogFactory = columnChooserDialogFactory
	}

	@Override
	void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled)
	{
		if(keyStroke.keyType == KeyType.Delete)
			if(keyStroke.shiftDown)
				applicationService.kill(processTable.selectedPid)
			else
				applicationService.terminate(processTable.selectedPid)
		else if(keyStroke.keyType == KeyType.F2)
			columnChooserDialogFactory.object.showDialog(GuiHolder.multiWindowTextGUI)
		else
			return

		hasBeenHandled.set(true)
	}
}
