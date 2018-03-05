package net.poundex.jtop.lanterna

import com.googlecode.lanterna.gui2.Window
import com.googlecode.lanterna.gui2.WindowBasedTextGUI
import com.googlecode.lanterna.gui2.WindowListenerAdapter
import com.googlecode.lanterna.gui2.dialogs.DialogWindow
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors

import java.util.concurrent.atomic.AtomicBoolean;

@CompileStatic
@InheritConstructors
abstract class JtopModalDialog extends DialogWindow
{
	abstract void saveAndClose()

	@Override
	Object showDialog(WindowBasedTextGUI textGUI)
	{
		this.addWindowListener(new DialogKeyListener())
		return super.showDialog(textGUI)
	}

	private class DialogKeyListener extends WindowListenerAdapter
	{
		@Override
		void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled)
		{
			if(keyStroke.keyType == KeyType.Character && keyStroke.character.toUpperCase() == 'S'.charAt(0) && keyStroke.ctrlDown)
				JtopModalDialog.this.saveAndClose()
			else if(keyStroke.keyType == KeyType.Escape)
				JtopModalDialog.this.close()
			else
				return

			hasBeenHandled.set(true)
		}
	}
}
