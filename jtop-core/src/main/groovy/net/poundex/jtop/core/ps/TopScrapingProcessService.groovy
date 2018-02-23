package net.poundex.jtop.core.ps

import groovy.transform.CompileStatic

@CompileStatic
class TopScrapingProcessService implements SystemProcessService
{
	@Override
	Map<Integer, RunningProcessInfo> getRunningProcesses()
	{
		String rawTop = "top -bn1".execute().text
		Scanner s =  new Scanner(rawTop).useDelimiter('PID.*\n')
		s.next()
		String rawProcs =  s.next()
		return rawProcs.readLines().collectEntries { line ->
			Scanner s2 = new Scanner(line).useDelimiter(/\s+/)
			int pid = s2.nextInt()
			[pid, new RunningProcessInfo(
					pid,
					s2.next(),
					s2.next(),
					s2.next(),
					s2.next(),
					s2.next(),
					s2.next(),
					s2.next(),
					s2.next(),
					s2.next(),
					s2.next(),
					s2.next())]
		}
	}
}
