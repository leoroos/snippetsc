package de.lev.snippetsc.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import de.lev.snippetsc.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class SnippetSCPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public SnippetSCPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Snippet Shortcut Configuration");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
			new StringFieldEditor(PreferenceConstants.P_STRING, "The &inserted snippet:", getFieldEditorParent()));		
//		addField(new SnippetListEditor("snippet name", "whatever 2nd", getFieldEditorParent()));
		
		SnippetListEditor listeditor = new SnippetListEditor("snippetlist", "snippetlist", getFieldEditorParent());
		
		
		
		
		
		Text text = new Text(getFieldEditorParent(), SWT.MULTI | SWT.READ_ONLY );
		GridData gd = new GridData(SWT.FILL | SWT.V_SCROLL | SWT.H_SCROLL);
		gd.horizontalAlignment = SWT.FILL;
		gd.widthHint = 400;
		gd.verticalAlignment = SWT.FILL;
		gd.horizontalSpan = 2;

		
		text.setLayoutData(gd);
		text.setText("current String content \n"
				+ "even with multline \n"
				+ "asdf and verrrrrrrrrrrrrrrrrrrrrrryyyyyyyyyyyyyyyyyyyyyyyy llllllllllllllooooooooooong linesdf and verrrrrrrrrrrrrrrrrrrrrrryyyyyyyyyyyyyyyyyyyyyyyy llllllllllllllooooooooooong linesdf and verrrrrrrrrrrrrrrrrrrrrrryyyyyyyyyyyyyyyyyyyyyyyy llllllllllllllooooooooooong linesdf and verrrrrrrrrrrrrrrrrrrrrrryyyyyyyyyyyyyyyyyyyyyyyy llllllllllllllooooooooooong linesdf and verrrrrrrrrrrrrrrrrrrrrrryyyyyyyyyyyyyyyyyyyyyyyy llllllllllllllooooooooooong line");
//		text.setLayoutData(layoutData);
		
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}