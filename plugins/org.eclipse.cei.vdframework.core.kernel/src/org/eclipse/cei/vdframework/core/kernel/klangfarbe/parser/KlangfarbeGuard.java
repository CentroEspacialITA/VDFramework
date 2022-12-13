package org.eclipse.cei.vdframework.core.kernel.klangfarbe.parser;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Guard;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Metadata;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Parameter;
import org.polarsys.capella.core.data.capellacore.Constraint;

public class KlangfarbeGuard implements Guard {

	
	private Constraint capellaGuard = null;
	
	KlangfarbeGuard (Constraint capellaGuard){
		this.capellaGuard = capellaGuard;
	}
	
	public boolean check(Metadata data, Parameter parameter) {
		
		return true;
	
	}
	
}
