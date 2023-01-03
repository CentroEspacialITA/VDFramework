package org.eclipse.cei.vdframework.core.kernel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Vector;
import java.util.HashMap;

import org.eclipse.cei.vdframework.core.kernel.klangfarbe.ConcurrentState;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.HierarchicalState;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.PseudoState;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Statechart;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.StatechartException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.polarsys.capella.core.data.capellacommon.AbstractState;
import org.polarsys.capella.core.data.capellacommon.FinalState;
import org.polarsys.capella.core.data.capellacommon.InitialPseudoState;
import org.polarsys.capella.core.data.capellacommon.Pseudostate;
import org.polarsys.capella.core.data.capellacommon.Region;
import org.polarsys.capella.core.data.capellacommon.State;
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
			// TODO: exception
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			 MessageDialog.openError(shell, "[CEI-VD]", 
					 "Could not find State Machine data!");
			 return;
			}
		// Early Prototyping
		kfChart = new Statechart("t1", 10, false);
		StateMachine sm = (StateMachine)(ownedSMS.toArray()[0]);
		EList<Region> regions = sm.getOwnedRegions();
		Region defaultRegion = regions.get(0);
		EList<AbstractState> states = defaultRegion.getOwnedStates();
	
	}	
	
	private void DFSParser(Region defaultRegion) throws StatechartException {
		// Get the list containing root states.
		EList<AbstractState> rootStates = defaultRegion.getOwnedStates();
		Stack<State> dfsStack = new Stack<>();
		Stack<State> parentStack = new Stack<>();
		HashMap<State, Boolean> mapVisited = new HashMap<State, Boolean>();
		
		// Push the first root state that is a <State>		
		for(AbstractState absState : rootStates) {
			if(absState instanceof State) {
				dfsStack.push((State)absState);
				parentStack.push((State)absState);
				break;
			}
		}
		
		// Also push the initial, final nodes.
		for(AbstractState absState : rootStates) {
			if(absState instanceof InitialPseudoState) {
			    initialKFStates.put((InitialPseudoState)absState, new PseudoState(absState.getName(),
			    		kfChart, PseudoState.pseudostate_start));
			    
			}
			else if(absState instanceof FinalState) {
			    finalKFStates.put((FinalState) absState, new org.eclipse.cei.vdframework.core.kernel.klangfarbe.FinalState(
			    		absState.getName(), kfChart));
			}
		}

		while(!dfsStack.empty()) {
			State s = dfsStack.peek();
			dfsStack.pop();
			
			if(mapVisited.get(s) == false || mapVisited.get(s) == null) {
				// Now we visit this state.
				
			
				if(s instanceof InitialPseudoState) {
					// add the following block to a function
					
					 EList<Region> involvedRegions = s.getInvolverRegions();
					 if(involvedRegions.size() > 1) {
						 Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						 MessageDialog.openError(shell, "[CEI-VD]", 
								 "InitialPseudoState is included in two regions.");
						 return;
						 
					 }
					 EList<AbstractState> involvedStates = involvedRegions.get(0).getInvolvedStates();
					 if(involvedStates.size() > 1) {
						 Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						 MessageDialog.openError(shell, "[CEI-VD]", 
								 "InitialPseudoState is included in two states.");
						 return;
						 
					 }
					
					 // Populate hash maps
					 if(hierarchicalKFStates.get(involvedStates.get(0)) != null) {
						 initialKFStates.put((InitialPseudoState)s, new PseudoState(
								 s.getName(), hierarchicalKFStates.get(involvedStates.get(0)), PseudoState.pseudostate_start));
					 }else if(concurrentKFStates.get(involvedStates.get(0)) != null) {
						 initialKFStates.put((InitialPseudoState)s, new PseudoState(
								 s.getName(), concurrentKFStates.get(involvedStates.get(0)), PseudoState.pseudostate_start));
					 }
					 else {
						 // This PseudoInitialPoint doesn't have a parent?
						 
					 } 
				}
				else if(s instanceof State) {
					// Add warnings
					EList<Region> involvedRegions = s.getInvolverRegions();
					EList<AbstractState> involvedStates = involvedRegions.get(0).getInvolvedStates();
					
					boolean isConcurrent = s.getOwnedRegions().size() > 1 ? true : false;
					if(isConcurrent) {
						if(hierarchicalKFStates.get(involvedStates.get(0)) != null) {
							 concurrentKFStates.put((State)s, new ConcurrentState(
									 s.getName(), hierarchicalKFStates.get(involvedStates.get(0))));
						 }else if(concurrentKFStates.get(involvedStates.get(0)) != null) {
							 concurrentKFStates.put((State)s, new ConcurrentState(
									 s.getName(), concurrentKFStates.get(involvedStates.get(0))));
						 }
						 else {
							 // This PseudoInitialPoint doesn't have a parent?
							 
						 }
					}		
					boolean isHierarchical;
					for(Region or : s.getOwnedRegions()) {
						  isHierarchical = or.getOwnedStates().size() > 0 ? true : false;	
						}
					
					if(isHierarchical) {
						
						
						
						
						
					}
					
					
					
					
					
						
					
					
					// Populate hash maps
					 if(hierarchicalKFStates.get(involvedStates.get(0)) != null) {
						 initialKFStates.put((InitialPseudoState)s, new PseudoState(
								 s.getName(), hierarchicalKFStates.get(involvedStates.get(0)), PseudoState.pseudostate_start));
					 }else if(concurrentKFStates.get(involvedStates.get(0)) != null) {
						 initialKFStates.put((InitialPseudoState)s, new PseudoState(
								 s.getName(), concurrentKFStates.get(involvedStates.get(0)), PseudoState.pseudostate_start));
					 }
					 else {
						 // This PseudoInitialPoint doesn't have a parent?
						 
					 } 
					
				}
			
				
				mapVisited.put(s,  true);
			    
			}
			
			EList<Region> sRegions = s.getOwnedRegions();
			
			// Treat each region as a 'state' for DFS

			
		    for(Region r : sRegions) {
		    	EList<AbstractState> subStates = r.getOwnedStates();
		    	if(subStates.size() == 0) {
		    		
		    	}
		    	
		    	
		    	for(AbstractState absState : subStates) {
		    		if(absState instanceof State && 
		    				(mapVisited.get(absState) == false || mapVisited.get(absState) == null)) {
		    				dfsStack.push((State)absState);
		    			}
		    		}
		    	}
		    
		    
		    	
		    	
		    }
			
			
		}
		
		
		
		
		
	
	
	
	
	
	
	
	// Members
	
	// Owned State Machines
	private HashSet<StateMachine> ownedSMS;
	
	private HashMap<InitialPseudoState, PseudoState> initialKFStates;
	private HashMap<FinalState, org.eclipse.cei.vdframework.core.kernel.klangfarbe.FinalState> finalKFStates;
	private HashMap<State, HierarchicalState> hierarchicalKFStates;
	private HashMap<State, ConcurrentState> concurrentKFStates;
	
	
	// Singleton
	private static VDSimulator INSTANCE;
	// Klangfarbe Statechart
	private Statechart kfChart;
	
}
