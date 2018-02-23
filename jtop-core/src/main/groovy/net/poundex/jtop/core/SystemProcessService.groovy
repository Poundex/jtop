package net.poundex.jtop.core

import groovy.transform.Immutable

@Singleton
class SystemProcessService {

	Map<Integer, RunningProcessInfo> getRunningProcesses() {
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

	@Immutable
	static class RunningProcessInfo {
		int pid
		String user
		String priority
		String niceness

		String virtual
		String reserved
		String shared

		String status
		String cpu
		String mem
		String cputime
		String command
	}
}
