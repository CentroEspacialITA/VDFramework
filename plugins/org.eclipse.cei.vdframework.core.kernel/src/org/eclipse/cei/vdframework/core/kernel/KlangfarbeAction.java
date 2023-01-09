package org.eclipse.cei.vdframework.core.kernel;

import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Action;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Event;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Metadata;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Parameter;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Statechart;
import org.polarsys.capella.common.data.behavior.AbstractEvent;


public class KlangfarbeAction implements Action {

	// ATTRIBUTES
	protected String name = null;
	
	private AbstractEvent capellaAssociatedEvent = null;
	
	private Event trigger = null;
	
	private Statechart myChart = null;
	// METHODS
	
	
	public KlangfarbeAction (AbstractEvent capellaEvent, Statechart chart) {
	   this.name = capellaEvent.getName();
	   this.capellaAssociatedEvent = capellaEvent;
	   this.myChart = chart;
   }
	
	
	
	public void execute(Metadata data, Parameter parameter) {
		// When executing this action (entry,do,exit,effect),
		// dispatch asynchronously the associated trigger (if it exists).
		if(trigger != null) {
			myChart.dispatchAsynchron(data, trigger);
		}
		
		
		
	}
	
	public void setTrigger(Event trigger) {
		this.trigger = trigger;
	}
	
    public void setName(String name) {
    	this.name = name;
    }
	public String getName() {
		return this.name;
	}
    
	
}
