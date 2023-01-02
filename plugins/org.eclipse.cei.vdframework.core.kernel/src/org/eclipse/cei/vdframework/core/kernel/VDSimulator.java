package org.eclipse.cei.vdframework.core.kernel;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.cei.vdframework.core.kernel.klangfarbe.PseudoState;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Statechart;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.StatechartException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.polarsys.capella.core.data.capellacommon.AbstractState;
import org.polarsys.capella.core.data.capellacommon.Pseudostate;
import org.polarsys.capella.core.data.capellacommon.Region;
import org.polarsys.capella.core.data.capellacommon.StateMachine;

public class VDSimulator {
	
	public VDSimulator(ArrayList<StateMachine> input_SMS) throws StatechartException{
		if(input_SMS.size() > 1) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		 MessageDialog.openError(shell, "[CEI-VD]", 
				 "Multiple State Machine simulation is not supported yet!");
		}
		this.ownedSMS = new HashSet<StateMachine>(input_SMS);
		initializeStatechart();
	}
	
	public static VDSimulator instantiate(ArrayList<StateMachine> input_SMS)
			throws StatechartException{
		if(INSTANCE == null) {
			INSTANCE = new VDSimulator(input_SMS);
		}
		return INSTANCE;
	}
	
	public static VDSimulator getInstance() {
		return INSTANCE != null ? INSTANCE : null;
	}
	
	
	private void initializeStatechart() throws StatechartException {
		if(ownedSMS.size() == 0) {
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			 MessageDialog.openError(shell, "[CEI-VD]", 
					 "Could not find State Machine data!");
			 return;
			}
		klangfarbeStatechart = new Statechart("t1", 10, false);
		StateMachine sm = (StateMachine)(ownedSMS.toArray()[0]);
		EList<Region> regions = sm.getOwnedRegions();
		Region default_region = regions.get(0);
		EList<AbstractState> states = default_region.getOwnedStates();
		for(AbstractState state : states) {
			if(state.getName() == "init") {
				PseudoState state_start = new PseudoState("init", klangfarbeStatechart,
						PseudoState.pseudostate_start);
			}
			//else if(state.getName())
			
		}
		
		
		
	}
	
	
	private void processState(AbstractState states) {
		
		
		
		
		
	}
	
	
	
	
	
	
	// Members
	
	// Owned State Machines
	private HashSet<StateMachine> ownedSMS;
	// Singleton
	private static VDSimulator INSTANCE;
	// Klangfarbe Statechart
	private Statechart klangfarbeStatechart;
	
}
