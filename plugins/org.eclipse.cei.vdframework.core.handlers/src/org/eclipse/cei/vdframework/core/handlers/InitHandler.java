package org.eclipse.cei.vdframework.core.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
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
		Session result; 
		if (diag.open() == Window.OK) {
		   result =  (Session)diag.getFirstResult();
		}
		else {
			return null;
		}
		
		
		
		
		
		
		return null;
	}
}


