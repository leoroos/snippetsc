/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package learning;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.lev.snippetsc.exceptions.UnreachableCodeException;
import de.lev.snippetsc.preferences.SnippetContainer;
import de.lev.snippetsc.preferences.SnippetContainer.SnippetKey;
import de.lev.snippetsc.preferences.SnippetEditor;

public class SnippetEditorExample {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);


		SnippetContainer dc = new SnippetContainer("prior name","prior shortcut","prior textstuffffff");
		
		SnippetEditor myDialog = new SnippetEditor(shell, dc);
		myDialog.open();
		
		int returnCode = myDialog.getReturnCode();
		
		switch (returnCode) {
		case Window.OK:
			handleOK(myDialog);
			break;
		case Window.CANCEL:
			System.out.println("cancel pressed");
			break;
		default:
			throw new UnreachableCodeException("unexpected return code: " + returnCode);
		}

	}

	private static void handleOK(SnippetEditor myDialog) {
		System.out.println("ok pressed");
		for (SnippetKey key : SnippetKey.values()) {			
			System.out.println(key.name() + " " + myDialog.snippetContainer.get(key));
		}
	}
}