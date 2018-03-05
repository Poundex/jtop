package net.poundex.jtop.core.config

import groovy.transform.Canonical
import groovy.transform.CompileStatic

import java.nio.file.Path

@CompileStatic
@Canonical
class JtopConfig implements ConfigWriter
{
	int tickInterval
	List<TableColumn> columns
	Path writeTarget

	void setColumns(List<String> columns)
	{
		this.columns = columns.collect { TableColumn.valueOf(it) }
	}
}
