package org.eclipse.cei.vdframework.core.kernel;

import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Action;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Metadata;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Parameter;


public class KlangfarbeAction implements Action {

	// ATTRIBUTES
	protected String name = null;
	
	
   public KlangfarbeAction (String initName) {
	   this.name = initName;
   }
	
	
	
	public void execute(Metadata data, Parameter parameter) {
		
		
		
		
	}
	
    public void setName(String name) {
    	this.name = name;
    }
	public String getName() {
		return this.name;
	}
    
	
}
