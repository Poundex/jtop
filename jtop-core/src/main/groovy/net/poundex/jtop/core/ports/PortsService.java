package net.poundex.jtop.core.ports;

import java.util.List;
import java.util.Map;

public interface PortsService
{
	Map<Integer, List<BindingInfo>> getProcessPorts();
}
