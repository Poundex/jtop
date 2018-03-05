package net.poundex.jtop.lanterna

import com.googlecode.lanterna.gui2.*
import groovy.transform.CompileStatic

@CompileStatic
class HelpPanel extends Panel
{
	HelpPanel()
	{
		setLayoutData(BorderLayout.Location.BOTTOM)
		Panel panel = new Panel(new LinearLayout(Direction.HORIZONTAL))
		panel.addComponent(new Label("[DEL] Terminate"))
		panel.addComponent(new Label("[SHIFT]+[DEL] Kill"))
		panel.addComponent(new Label("[F2] Columns"))
		addComponent(panel)
	}
}
