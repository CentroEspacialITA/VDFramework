package org.eclipse.cei.vdframework.core.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import java.util.Collection;
import java.util.HashSet;

import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.model.helpers.ModelQueryHelper;

import org.polarsys.capella.core.sirius.analysis.actions.extensions.SelectElementsFromTransferWizard;
import org.polarsys.capella.common.ui.toolkit.dialogs.TransferTreeListDialog;
import org.polarsys.capella.core.sirius.ui.helper.SessionHelper;

import org.eclipse.cei.vdframework.core.util.ModelQuery;

public class InitHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
	 
		Collection<Session> col_Sessions =  SessionManager.INSTANCE.getSessions();
		List<String> str = new ArrayList<String>();
		for (Session x : col_Sessions) {
			java.lang.System.out.println(x.getID());
			str.add(x.getID());
		}
		ElementListSelectionDialog diag = new ElementListSelectionDialog(window.getShell(), new LabelProvider());
		diag.setElements(col_Sessions.toArray());
		
		diag.setMessage("Please select the session (.aird file)");
		diag.setTitle("[CEI-VD] Project Selection");
		diag.setMultipleSelection(false);
		Session result = null; 
		if (diag.open() == Window.OK) {
		   result =  (Session)diag.getFirstResult();
		}
		else {
			return null;
		}
		
		/*
		//Collection<EObject> current = (Collection) propertyContext.getCurrentValue(restraintProperty);
		//Collection<EObject> current = new HashSet<EObject>();
		//Collection<EObject> left = new HashSet<EObject>();
        left.addAll((Collection) restraintProperty.getValue(propertyContext));
        left.addAll((Collection) restraintProperty.getChoiceValues(propertyContext));
        left.removeAll(current);
        left.remove(null);
        Collection<EObject> right = new HashSet<EObject>();
        right.addAll(current);
        right.remove(null);
			*/
        TransferTreeListDialog dialog = new TransferTreeListDialog(window.getShell(), "Selection wizard", "Select elements.");//$NON-NLS-2$
        
  
        Project proj = SessionHelper.getCapellaProject(result);
        Collection<EObject> sms = ModelQuery.getAllStateMachines(proj);
        Collection<EObject> left = new HashSet<EObject>(sms);
        
        dialog.setLeftInput(new ArrayList<EObject>(left), null);
        dialog.setRightInput(new ArrayList<EObject>(), null);
        if (dialog.open() == Window.OK) {
          Object[] dialogResult = dialog.getResult();
          ArrayList<Object> result2 = new ArrayList<Object>();
          if (dialogResult != null) {
            for (Object res : dialogResult) {
              result2.add(res);
            }
          }

          
          
          
        }
		
		return null;
	}
}


