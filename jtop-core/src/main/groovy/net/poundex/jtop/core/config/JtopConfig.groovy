package net.poundex.jtop.core.config

import groovy.transform.Canonical
import groovy.transform.CompileStatic

@CompileStatic
@Canonical
class JtopConfig implements ConfigWriter
{
	int tickInterval
	List<TableColumn> columns

	void setColumns(List<String> columns)
	{
		this.columns = columns.collect { TableColumn.valueOf(it) }
	}
}
