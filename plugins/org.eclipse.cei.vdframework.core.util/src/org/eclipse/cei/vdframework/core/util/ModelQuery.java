package org.eclipse.cei.vdframework.core.util;
import org.polarsys.capella.core.model.helpers.ModelQueryHelper;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.capellacommon.StateMachine;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.cs.Block;
import org.polarsys.capella.core.data.ctx.SystemComponent;
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponent;

// Based on ModelQueryHelper.
public class ModelQuery {

	static public Collection<EObject> getAllStateMachines(Project project){
		Collection<EObject> ret_lc = new HashSet<EObject>();
		LogicalComponent lc = ModelQueryHelper.getLogicalSystem(project);
		PhysicalComponent pc = ModelQueryHelper.getPhysicalSystem(project);
		SystemComponent sc = ModelQueryHelper.getSystem(project);
		
		if(lc != null) {
			for(StateMachine sm : ((Block)lc).getOwnedStateMachines()) {
				ret_lc.add(sm);
			}
		}
		if(pc != null) {
			for(StateMachine sm : ((Block)pc).getOwnedStateMachines()) {
				ret_lc.add(sm);
			}
		}
		if(sc != null) {
			for(StateMachine sm : ((Block)sc).getOwnedStateMachines()) {
				ret_lc.add(sm);
			}
		}
		return ret_lc;
	}
	
		
}
