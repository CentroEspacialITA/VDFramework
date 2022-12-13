package org.eclipse.cei.vdframework.core.kernel.klangfarbe.parser;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.*;
import org.polarsys.capella.common.data.behavior.AbstractEvent;
import org.polarsys.capella.core.data.capellacore.Constraint;


public class KlangfarbeParser {

	static public Event createEvent(AbstractEvent capellaEvent) {
		return new KlangfarbeEvent(capellaEvent);
	}
	
	void createMetadata()
	{
		// TO-DO: PARAMETRIC SIMULATION
	}
	
	// Capella makes no distinction between events and 
	// actions - both are of type AbstractEvent
	static public Action createAction(AbstractEvent capellaAction) {
		return new KlangfarbeAction(capellaAction);
	}
	
	static public Guard  createGuard(Constraint capellaGuard) {
		return new KlangfarbeGuard(capellaGuard);	
	}
	
	
	
	
	
	
	
	
}
