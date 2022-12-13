package org.eclipse.cei.vdframework.core.kernel.klangfarbe.parser;

import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Action;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Metadata;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Parameter;
import org.polarsys.capella.common.data.behavior.AbstractEvent;

public class KlangfarbeAction implements Action{
	// Wrapper around Klangfarbe event that contains a capellaEvent.
	
	public KlangfarbeAction(AbstractEvent in_event){
		capellaEvent = in_event;
	}
	// Class member
	AbstractEvent capellaEvent = null;
	
	@Override
	public void execute(Metadata data, Parameter param) {
	// Do nothing for now.
		
	}
	
}
