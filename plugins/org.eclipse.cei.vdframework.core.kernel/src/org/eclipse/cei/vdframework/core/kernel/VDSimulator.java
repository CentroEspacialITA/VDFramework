package org.eclipse.cei.vdframework.core.kernel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Vector;
import java.util.HashMap;

import org.eclipse.cei.vdframework.core.kernel.klangfarbe.ConcurrentState;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Context;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.HierarchicalState;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.PseudoState;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Statechart;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.StatechartException;
import org.eclipse.cei.vdframework.core.kernel.klangfarbe.Transition;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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
import org.polarsys.capella.core.data.capellacommon.StateTransition;

public class VDSimulator {
	
	public VDSimulator(ArrayList<StateMachine> input_SMS) throws StatechartException{
		if(input_SMS.size() > 1) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		 MessageDialog.openError(shell, "[CEI-VD]", 
				 "Multiple State Machine simulation is not supported yet!");
		}

		this.ownedSMS = new HashSet<StateMachine>(input_SMS);
		this.initialKFStates = new HashMap<InitialPseudoState, PseudoState>();
		this.finalKFStates = new HashMap<FinalState, org.eclipse.cei.vdframework.core.kernel.klangfarbe.FinalState>();
		this.concurrentKFStates = new HashMap<State, ConcurrentState>();
		this.hierarchicalKFStates = new HashMap<State, HierarchicalState>();
		this.stateKFStates = new HashMap<State, org.eclipse.cei.vdframework.core.kernel.klangfarbe.State>();
		this.regionKFState = new HashMap<Region, HierarchicalState>();
		this.transitionKF = new HashMap<StateTransition, Transition>();
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
		DFSParser(defaultRegion);
	
	}	
	
	private void DFSParser(Region defaultRegion) throws StatechartException {
		// Get the list containing root states.
		EList<AbstractState> rootStates = defaultRegion.getOwnedStates();
		Stack<AbstractState> dfsStack = new Stack<>();
		HashMap<AbstractState, Boolean> mapVisited = new HashMap<AbstractState, Boolean>();
		
		// Push the root states	
		for(AbstractState absState : rootStates) {
			if((absState instanceof State) && !(absState instanceof FinalState)) {
				dfsStack.push((State)absState);
				//if(isConcurrent((State) absState)) {
				//	concurrentKFStates.put((State)absState, new ConcurrentState(absState.getName(), kfChart));
				//}
				//else if(isParent((State)absState)) {
				//	hierarchicalKFStates.put((State) absState, new HierarchicalState(absState.getName(), kfChart));	
				//}
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
			AbstractState s = dfsStack.peek();
			dfsStack.pop();
			
			if(mapVisited.get(s) == null || mapVisited.get(s) == false) {
				// Visit this AbstractState.
				Context parent = getParent(s);
				if(s instanceof InitialPseudoState) {
					// add the following block to a function
					 // Populate hash maps
					
					 initialKFStates.put((InitialPseudoState)s, new PseudoState(
							 s.getName(), parent, PseudoState.pseudostate_start));
		
				}else if (s instanceof FinalState) {
					 // Populate hash maps		
					finalKFStates.put((FinalState)s, new org.eclipse.cei.vdframework.core.kernel.klangfarbe.FinalState(
							 s.getName(), parent));
					 		
				}else if(s instanceof State) {
					// Add warnings		
					boolean isConcurrent = ((State)s).getOwnedRegions().size() > 1 ? true : false;
					boolean isHierarchical = false;
					for(Region or : ((State)s).getOwnedRegions()) {
						  isHierarchical = or.getOwnedStates().size() > 0 ? true : false;	
						}
					if(isConcurrent) {
						concurrentKFStates.put((State)s, new ConcurrentState(
								 s.getName(), parent));
						
					}else if(isHierarchical) {
						hierarchicalKFStates.put((State)s, new HierarchicalState(
								 s.getName(), parent));	
					}else {
						// Just an empty state
					    stateKFStates.put((State)s, new 
					    		org.eclipse.cei.vdframework.core.kernel.klangfarbe.State(s.getName(), parent));
					
					}
					
					EList<Region> sRegions = ((State)s).getOwnedRegions();
				    for(Region r : sRegions) {
				    	if(sRegions.size() > 1) {
				    		regionKFState.put(r,  new HierarchicalState(r.getName(), concurrentKFStates.get(s)));

				    	}
				    	
				    	// Treat Region as concurrent states.
				    	EList<AbstractState> subStates = r.getOwnedStates();
				    	if(subStates.size() == 0) {
				    		
				    	}
				    	
				    	for(AbstractState absState : subStates) {
				    		if(/*absState instanceof State && */
				    				(mapVisited.get(absState) == null || mapVisited.get(absState) == false)) {
				    				dfsStack.push(absState);
				    			}
				    		}
				    	}
				}
				
				mapVisited.put(s,  true);
			    
			}
			
			
		    }
			TransitionParser(defaultRegion);
			kfChart.toString();
		}
		
	private void TransitionParser(Region defaultRegion) {
		// Parse transitions now
		// States/substates/regions are already parsed
		
		// Add all transitions exiting an initial node
		initialKFStates.forEach(
				(key,value) ->
				{
				 EList<StateTransition> outTransition = key.getOutgoing();	
				 if(outTransition.size() > 1) {
					 Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					 MessageDialog.openError(shell, "[CEI-VD]", 
							 "Initial Pseudo State contains more than one outgoing transitions.");
					 return;
				 }
				 // Next AbstractState could be:
				 // State, HierarchicalState, ConcurrentState, FinalState.
				 if(outTransition.size()>0) {
				 AbstractState target = outTransition.get(0).getTarget();
				 Context targetKF = null;
				 if(isConcurrent(target)) {
					 targetKF = concurrentKFStates.get(target);
					 
				 }else if (isHierarchical(target)) {
					 targetKF = hierarchicalKFStates.get(target);
					 
				 }else {
					 transitionKF.put(outTransition.get(0), new Transition(value, stateKFStates.get(target)));
					 
				 }
				 if(targetKF != null) {
				 transitionKF.put(outTransition.get(0), new Transition(value, targetKF));
				 }
				 }
				});			
		finalKFStates.forEach(
				// Add all transitions entering a final node
				(key,value) ->
				{
				 EList<StateTransition> inTransition = key.getIncoming();	
				 for(StateTransition in : inTransition) {
				 AbstractState source = in.getSource();
				 Context sourceKF = null;
				 if(isConcurrent(source)) {
					 sourceKF = concurrentKFStates.get(source);
					 
				 }else if (isHierarchical(source)) {
					 sourceKF = hierarchicalKFStates.get(source);
					 
				 }else {
					 transitionKF.put(in, new Transition(stateKFStates.get(source), value));
					 
				 }
				 if(sourceKF != null) {
				 transitionKF.put(inTransition.get(0), new Transition(sourceKF,value));
				 }
				 }
				});	
		
		
	}
		
		
	private Context getParent(AbstractState s) {
		EList<Region> involverRegions = s.getInvolverRegions();
		EObject involverState = involverRegions.get(0).eContainer();
		Context ret = null;
		
		if(involverRegions.size() > 1) {
			 Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			 MessageDialog.openError(shell, "[CEI-VD]", 
					 "InitialPseudoState is included in two regions.");
			 
			 return ret;
		 }
		String name = involverRegions.get(0).getName();
		if(name.equals("Default Region")) {
			ret = kfChart;
		}
		
		else if(!(involverState instanceof State)) {
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			 MessageDialog.openError(shell, "[CEI-VD]", 
					 "eContainer is not a State");
			return null;
		}
		 
		if(s instanceof InitialPseudoState) {
			// The parent of an init node is the involver region.
			//ret = involverRegions.get(0);
			boolean isInsideConcurrentZone =((State)involverState).getOwnedRegions().size() > 1 ? true:false;
			if(isInsideConcurrentZone) {
				return regionKFState.get(involverRegions.get(0));
			}else {
				return (Context)involverState;
			}
			
		}
		
		 // Populate hash maps
		 if(hierarchicalKFStates.get(involverState) != null) {
			 ret = hierarchicalKFStates.get(involverState);
		 }else if(concurrentKFStates.get(involverState) != null) {
			 ret =  concurrentKFStates.get(involverState);
		 }
		 else {
			 // This PseudoInitialPoint doesn't have a parent? 
			 // A normal State can't be a parent.
		 } 
		 return ret;
	}
	
	private boolean isParent(AbstractState s) {
		if(!(s instanceof State)){
			return false;
		}
		EList<Region> ownedRegions = ((State)s).getOwnedRegions();
		boolean isParent = false;
		for(Region r : ownedRegions) {
			if(r.getOwnedStates().size() > 0) {
				isParent= true;
				break;
			}
		}
		return isParent;
	}
	
	private boolean isConcurrent(AbstractState s) {
		if(!(s instanceof State)){
			return false;
		}
		EList<Region> ownedRegions = ((State)s).getOwnedRegions();
		return ownedRegions.size() > 1 ? true : false;
	}
	
	private boolean isHierarchical(AbstractState s) {
		return isParent(s);
	}
	
	
	
	
	// Members
	
	// Owned State Machines
	private HashSet<StateMachine> ownedSMS;
	
	// State and Pseudostates from Klangfarbe
	private HashMap<InitialPseudoState, PseudoState> initialKFStates;
	private HashMap<FinalState, org.eclipse.cei.vdframework.core.kernel.klangfarbe.FinalState> finalKFStates;
	private HashMap<State, HierarchicalState> hierarchicalKFStates;
	private HashMap<State, ConcurrentState> concurrentKFStates;
	private HashMap<State, org.eclipse.cei.vdframework.core.kernel.klangfarbe.State> stateKFStates;
	private HashMap<Region, HierarchicalState> regionKFState;
	private HashMap<StateTransition, Transition> transitionKF;
	// Singleton
	private static VDSimulator INSTANCE;
	// Klangfarbe Statechart
	private Statechart kfChart;
	
}
