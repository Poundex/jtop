package net.poundex.jtop.lanterna

import com.googlecode.lanterna.gui2.Window
import com.googlecode.lanterna.gui2.WindowListenerAdapter
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import groovy.transform.CompileStatic
import net.poundex.jtop.core.app.ApplicationService

import java.util.concurrent.atomic.AtomicBoolean

@CompileStatic
class KeyListener extends WindowListenerAdapter
{
	private final ApplicationService applicationService
	private final ProcessTable processTable

	KeyListener(ApplicationService applicationService, ProcessTable processTable)
	{
		this.applicationService = applicationService
		this.processTable = processTable
	}

	@Override
	void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled)
	{
		if(keyStroke.keyType == KeyType.Delete)
			if(keyStroke.shiftDown)
				applicationService.kill(selectedPid)
			else
				applicationService.terminate(selectedPid)
		else
			return

		hasBeenHandled.set(true)
	}

	private int getSelectedPid()
	{
		return processTable.tableModel.getRow(processTable.getSelectedRow()).first().toInteger()
	}
}
