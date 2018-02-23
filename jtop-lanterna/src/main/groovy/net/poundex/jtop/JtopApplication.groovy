package net.poundex.jtop

import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.gui2.table.Table
import com.googlecode.lanterna.gui2.table.TableModel
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import net.poundex.jtop.core.JpsService
import net.poundex.jtop.core.SystemProcessService

import java.util.concurrent.atomic.AtomicBoolean

class JtopApplication {
	static void main(String[] args) {
		Terminal terminal = new DefaultTerminalFactory().createTerminal()
		Screen screen = new TerminalScreen(terminal)
		screen.startScreen()

		Panel mainPanel = new Panel()
		mainPanel.setLayoutManager(new BorderLayout())

//        mainPanel.addComponent(new Label("Forename"))
//        mainPanel.addComponent(new TextBox())
//
//        mainPanel.addComponent(new Label("Surname"))
//        mainPanel.addComponent(new TextBox())
//
//        mainPanel.addComponent(new EmptySpace(new TerminalSize(0, 0)))
//        mainPanel.addComponent(new Button("Submit"))

		Panel topPanel = new Panel()
		topPanel.layoutData = BorderLayout.Location.TOP
		topPanel.addComponent(new Label("Some text"))

		Panel centrePanel = new Panel()
		centrePanel.setLayoutData(BorderLayout.Location.CENTER)
		Table<String> table = new Table("PID", "User", "Pr", "N", "Virt", "Res", "Shr", "S", "%CPU", "%Mem", "Time", "Application")
		Thread.startDaemon {
			while(true) {
				Map<Integer, SystemProcessService.RunningProcessInfo> procs = SystemProcessService.instance.runningProcesses
				List<JpsService.RunningVirtualMachineInfo> vms = JpsService.instance.runningVMs
				TableModel<String> tableModel = new TableModel<>("PID", "User", "Pr", "N", "Virt", "Res", "Shr", "S", "%CPU", "%Mem", "Time", "Application")
				vms.each { vm ->
					tableModel.addRow(
							procs[vm.pid].pid.toString(),
							procs[vm.pid].user,
							procs[vm.pid].priority,
							procs[vm.pid].niceness,
							procs[vm.pid].virtual,
							procs[vm.pid].reserved,
							procs[vm.pid].shared,
							procs[vm.pid].status,
							procs[vm.pid].cpu,
							procs[vm.pid].mem,
							procs[vm.pid].cputime,
							vm.mainClass
					)
				}
				table.setTableModel(tableModel)
				Thread.sleep(800)
			}
		}
		table.setLayoutData(BorderLayout.Location.CENTER)

//		Thread.start {
//			Thread.sleep(3000)
//			TableModel<String> nm = new TableModel<>("And", "I'm", "A", "Table")
//			nm.addRow("New!", "New!", "New!", "New!")
//			nm.addRow("New!", "New!", "New!", "New!")
//			nm.addRow("New!", "New!", "New!", "New!")
//			nm.addRow("New!", "New!", "New!", "New!")
//			table.setTableModel(nm)
//		}
		centrePanel.addComponent(table)

		mainPanel.addComponent(topPanel)
//        mainPanel.addComponent(centrePanel)
		mainPanel.addComponent(table)

		BasicWindow window = new BasicWindow()

		window.component = mainPanel
		window.hints = [Window.Hint.EXPANDED, Window.Hint.CENTERED, Window.Hint.NO_DECORATIONS ]
//        window.title = "JTop"

		window.addWindowListener(new WindowListener() {
			@Override
			void onResized(Window w, TerminalSize oldSize, TerminalSize newSize) {

			}

			@Override
			void onMoved(Window w, TerminalPosition oldPosition, TerminalPosition newPosition) {

			}

			@Override
			void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
//				println "Handled: ${keyStroke}"
			}

			@Override
			void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled)
			{
				println keyStroke
				if(keyStroke.keyType == KeyType.Delete)
				{
					int pid = table.getTableModel().getRow(table.selectedRow).first().toInteger()
					"kill ${pid}".execute().waitFor()
					hasBeenHandled.set(true)
					TextColor
				}
			}
		})

		MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
				new EmptySpace(TextColor.ANSI.BLUE))
		gui.addWindowAndWait(window)
	}
}
