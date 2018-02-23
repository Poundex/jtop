package net.poundex.jtop.core.ps;

import java.util.Map;

public interface SystemProcessService
{
	Map<Integer, RunningProcessInfo> getRunningProcesses();
}
