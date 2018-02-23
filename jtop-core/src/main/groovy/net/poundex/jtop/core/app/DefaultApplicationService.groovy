package net.poundex.jtop.core.app

import groovy.transform.CompileStatic

@CompileStatic
class DefaultApplicationService implements ApplicationService
{
	@Override
	void terminate(int pid)
	{
		"kill ${pid}".execute().waitFor()
	}

	@Override
	void kill(int pid)
	{
		"kill -9 ${pid}".execute().waitFor()
	}
}
