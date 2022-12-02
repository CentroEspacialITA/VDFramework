package org.cei.vdframework.core.interpreter;

import org.polarsys.capella.core.data.capellamodeller.ModelRoot;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.business.api.session.Session;
import java.util.Collection;

public class StateMachineInterpreter {

	
	public StateMachineInterpreter() {
		
		Collection<Session> col_Sessions =  SessionManager.INSTANCE.getSessions();
		for (Session x : col_Sessions) {
			java.lang.System.out.println(x.getID());
		}
		
		
	}
	
	
	
}
