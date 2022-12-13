package org.eclipse.cei.vdframework.core.kernel.klangfarbe.parser;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Event;
import org.polarsys.capella.common.data.behavior.AbstractEvent;

public class KlangfarbeEvent extends Event{
	// Wrapper around Klangfarbe event that contains a capellaEvent.
	
	KlangfarbeEvent(AbstractEvent in_event){
		capellaEvent = in_event;
	}
	AbstractEvent capellaEvent = null;
}
