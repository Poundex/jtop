package net.poundex.jtop.core.config;

import net.poundex.jtop.core.snapshot.RunningApplicationDetails;

import java.util.stream.Collectors;

public enum TableColumn
{
	PID("PID", RunningApplicationDetails::getPidString),
	USER("User", details -> details.getProcessInfo().getUser()),
	PRIORITY("Pr", details -> details.getProcessInfo().getPriority()),
	NICENESS("N",details -> details.getProcessInfo().getNiceness()),
	VIRTUAL("Virt", details -> details.getProcessInfo().getVirtual()),
	RESERVED("Res", details -> details.getProcessInfo().getReserved()),
	SHARED("Shr", details -> details.getProcessInfo().getShared()),
	STATUS("S", details -> details.getProcessInfo().getStatus()),
	PCPU("%CPU", details -> details.getProcessInfo().getCpu()),
	PMEM("%Mem", details -> details.getProcessInfo().getMem()),
	CPU_TIME("Time", details -> details.getProcessInfo().getCputime()),
	PORTS("Ports", details ->
			details.getPorts().stream().map(info -> "" + info.getProtocol() + ":" + info.getPort())
					.collect(Collectors.joining(" ")) ),
	APPLICATION("Application", details -> details.getVmInfo().getMainClass());

	private final String displayString;
	private final ColumnCellRenderer renderer;

	TableColumn(String displayString, ColumnCellRenderer renderer)
	{
		this.displayString = displayString;
		this.renderer = renderer;
	}

	public String render(RunningApplicationDetails details)
	{
		return renderer.render(details);
	}

	public final String getDisplayString()
	{
		return displayString;
	}

	@FunctionalInterface
	private interface ColumnCellRenderer
	{
		String render(RunningApplicationDetails details);
	}
}
