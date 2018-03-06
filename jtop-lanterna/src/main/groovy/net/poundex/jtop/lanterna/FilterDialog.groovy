package net.poundex.jtop.lanterna

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.*
import groovy.transform.CompileStatic
import net.poundex.jtop.core.config.JtopConfig
import net.poundex.jtop.core.snapshot.SnapshotManager

@CompileStatic
class FilterDialog extends JtopModalDialog
{
	private final JtopConfig jtopConfig
	private final SnapshotManager snapshotManager
	private Set<String> filterExclude = new HashSet<>()
	private Map<String, CheckBox> packageToCheckbox = [:]
	private String lastFocus
	private CheckBox lastFocusable

	FilterDialog(SnapshotManager snapshotManager, JtopConfig jtopConfig)
	{
		super("Filter")
		this.jtopConfig = jtopConfig
		this.snapshotManager = snapshotManager
		updateFilters()
		setComponent(getPanel())
	}

	@Override
	void saveAndClose()
	{
		jtopConfig.filterExclude = filterExclude.collect().toSet()
		jtopConfig.write()
		close()
	}

	private void updateFilters()
	{
		filterExclude = jtopConfig.filterExclude.collect().toSet()
	}

	private Panel getPanel()
	{
		Panel p = new Panel()
		List<String> classes = snapshotManager.lastSnapshot.applications.values()*.vmInfo*.mainClass
		Map<String, Object> grouped = [:]
		Map<String, Object> m = grouped
		classes.each { klass ->
			if(klass.toLowerCase().endsWith(".jar")) {
				grouped[klass] = [:]
				return
			}
			klass.split("\\.").each { String part ->
				if( ! m.containsKey(part))
					m[part] = [:]
				m = m[part] as Map<String, Object>
			}
			m = grouped
		}
		renderFilterCheckboxesInto(p, grouped, 0)
		p.addComponent(new Button("Done", this.&saveAndClose))
		return p
	}

	private void renderFilterCheckboxesInto(Panel panel, Map<String, Object> grouped, int depth, String atPackage = "")
	{
		grouped.each { k, v ->
			Panel p2 = new Panel(new LinearLayout(Direction.HORIZONTAL))
			if(depth != 0)
				p2.addComponent(new EmptySpace(new TerminalSize(depth + 1, 1)))
			CheckBox checkBox = new CheckBox(k)
			String packageOrClass = "${atPackage}.${k}".substring(1)
			checkBox.checked = ! filterExclude.any {
				packageOrClass.startsWith(it)
			}
			checkBox.addListener { boolean newv ->
				if( ! newv)
					filterExclude << packageOrClass
				else
					filterExclude.removeAll { packageOrClass.startsWith(it) }
				lastFocus = packageOrClass
				setComponent(getPanel())
			}
			packageToCheckbox[packageOrClass] = checkBox
			if(lastFocus == packageOrClass)
				lastFocusable = checkBox
			p2.addComponent(checkBox)
			panel.addComponent(p2)
			if((v as Map).size() > 0)
				renderFilterCheckboxesInto(panel, v as Map<String, Object>, depth + 1, "${atPackage}.${k}")
		}
	}

	@Override
	void setComponent(Component component)
	{
		super.setComponent(component)
		if(lastFocusable)
			lastFocusable.takeFocus()
	}
}
