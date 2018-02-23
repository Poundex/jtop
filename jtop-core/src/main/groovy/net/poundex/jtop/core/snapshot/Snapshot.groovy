package net.poundex.jtop.core.snapshot

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@Immutable
@CompileStatic
class Snapshot
{
	Map<Integer, RunningApplicationDetails> applications
}
