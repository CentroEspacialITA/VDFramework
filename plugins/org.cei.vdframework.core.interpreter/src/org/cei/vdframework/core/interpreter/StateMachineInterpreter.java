package org.cei.vdframework.core.interpreter;

import org.polarsys.capella.core.data.capellacommon.StateMachine;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.model.handler.command.CapellaResourceHelper;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.business.api.session.Session;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;


public class StateMachineInterpreter {
	public StateMachineInterpreter() {
		
	}
	
	public void dumpAllSystemStateMachines(Session curSession) {
		
		if(curSession == null) {
			return;
		}
		Collection<Resource> resources = curSession.getSemanticResources();
		List<StateMachine> stateMachines = new ArrayList<>();
		
		resources.stream()
		.filter(CapellaResourceHelper::isCapellaResource)
		.forEach((resource) -> {
			resource.getContents().stream()
				.filter(Project.class::isInstance)
				.forEach((content) -> {
					((Project) content).getOwnedModelRoots().stream()
						.filter(SystemEngineering.class::isInstance)
						.forEach((modelRoot) -> {
							((SystemEngineering) modelRoot).getOwnedArchitectures().stream()
							.filter(SystemAnalysis.class::isInstance)
							.forEach((systemAnalysis) -> {
								stateMachines.addAll(((SystemAnalysis) systemAnalysis)
									.getOwnedSystemComponentPkg().getOwnedStateMachines()) ;
								});
						;
					});
			;
		});
		;
	});
		for (StateMachine sm : stateMachines) {
			java.lang.System.out.println(sm.getName());
		
		}
		
	}
		
		
	}
	

