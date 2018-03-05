package net.poundex.jtop.lanterna

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import groovy.transform.CompileStatic
import net.poundex.jtop.core.app.ApplicationService
import net.poundex.jtop.core.JtopContextConfiguration
import net.poundex.jtop.core.config.JtopConfig
import net.poundex.jtop.core.snapshot.SnapshotManager
import org.springframework.beans.factory.ObjectFactory
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Scope

@Configuration
@Import(JtopContextConfiguration)
@CompileStatic
class JtopLanternaContextConfiguration
{
	@Bean
	Screen terminalScreen()
	{
		Terminal terminal = new DefaultTerminalFactory().createTerminal()
		Screen screen = new TerminalScreen(terminal)
		screen.startScreen()
		return screen
	}

	@Bean
	InfoPanel infoPanel()
	{
		return new InfoPanel()
	}

	@Bean
	HelpPanel helpPanel()
	{
		return new HelpPanel()
	}

	@Bean
	ProcessTable processTable(SnapshotManager snapshotManager, JtopConfig jtopConfig)
	{
		return new ProcessTable(snapshotManager, jtopConfig)
	}

	@Bean
	KeyListener keyListener(ApplicationService applicationService, ProcessTable processTable, ObjectFactory<ColumnChooserDialog> columnChooserDialog)
	{
		return new KeyListener(applicationService, processTable, columnChooserDialog)
	}

	@Bean
	Window window(Screen screen, InfoPanel infoPanel, ProcessTable processTable, KeyListener keyListener, HelpPanel helpPanel)
	{
		Panel mainPanel = new Panel(new BorderLayout())

		mainPanel.addComponent(infoPanel)
		mainPanel.addComponent(processTable)
		mainPanel.addComponent(helpPanel)

		BasicWindow window = new BasicWindow()
		window.component = mainPanel
		window.hints = [ Window.Hint.EXPANDED, Window.Hint.CENTERED, Window.Hint.NO_DECORATIONS ]
		window.addWindowListener(keyListener)

		Thread.start {
			MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
					new EmptySpace(TextColor.ANSI.BLUE))
			GuiHolder.multiWindowTextGUI = gui
			gui.addWindowAndWait(window)
		}

		return window
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	ColumnChooserDialog columnChooserDialog(JtopConfig config)
	{
		return new ColumnChooserDialog(config)
	}
}
