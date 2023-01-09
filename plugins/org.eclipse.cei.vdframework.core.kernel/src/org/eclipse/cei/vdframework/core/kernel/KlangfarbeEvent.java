package org.eclipse.cei.vdframework.core.kernel;

import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Event;
import org.polarsys.capella.common.data.behavior.AbstractEvent;

public class KlangfarbeEvent extends Event {
	
	private AbstractEvent myCapellaEvent = null;
	
	public KlangfarbeEvent(AbstractEvent capellaEvent) {
		super(capellaEvent.getName());
		this.myCapellaEvent = capellaEvent;
	}
	
}
