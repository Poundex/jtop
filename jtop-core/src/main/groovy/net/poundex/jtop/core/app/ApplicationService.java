package net.poundex.jtop.core.app;

public interface ApplicationService
{
	void terminate(int pid);
	void kill(int pid);
}
