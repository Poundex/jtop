package net.poundex.jtop.lanterna

import com.googlecode.lanterna.gui2.BorderLayout
import com.googlecode.lanterna.gui2.Label
import com.googlecode.lanterna.gui2.Panel
import groovy.transform.CompileStatic

@CompileStatic
class InfoPanel extends Panel
{
	InfoPanel()
	{
		setLayoutData(BorderLayout.Location.TOP)
		addComponent(new Label("Some Text"))
	}
}
