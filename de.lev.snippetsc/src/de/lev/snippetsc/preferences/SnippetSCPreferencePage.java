package de.lev.snippetsc.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import de.lev.snippetsc.Activator;
import de.lev.snippetsc.preferences.SnippetContainer.SnippetKey;

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

	private List<SnippetContainer> snippets;
	private SnippetList listeditor;
	private Text previewText;

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

		Composite listcomp = new Composite(getFieldEditorParent(), SWT.NONE);
		listcomp.setLayout(new GridLayout());
		GridData gd_listcomp = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_listcomp.horizontalSpan = 2;
		listcomp.setLayoutData(gd_listcomp);
		
		SnippetList listeditor = new SnippetList();
		listeditor.init(listcomp);	
		listeditor.addTableListener(new SnippetTableListener() {
			
			@Override
			public void tableItemSelected(SelectionEvent e, TableItem item,
					SnippetContainer data) {
				previewText.setText(data.get(SnippetKey.snippet));
			}
		});
		
		//create snipet container init values add selection listener
		
		Text previewText = createPreviewText(getFieldEditorParent());
		
		this.listeditor = listeditor;
		this.previewText = previewText;
		
		syncSnippetsWithPreferenceStore();
	}	
	
	private Text createPreviewText(Composite parent) {
		Text text = new Text(parent, SWT.MULTI | SWT.READ_ONLY );
		GridData gd = new GridData(SWT.FILL | SWT.V_SCROLL | SWT.H_SCROLL);
		gd.horizontalAlignment = SWT.FILL;
		gd.widthHint = 400;
		gd.heightHint = 100;		
		gd.verticalAlignment = SWT.FILL;
		gd.horizontalSpan = 2;
		text.setLayoutData(gd);
		return text;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		
	}

	private void syncSnippetsWithPreferenceStore() {
		this.snippets = readSnippetsFromStore();
		listeditor.setStore(this.snippets);
		listeditor.loadTable();
	}

	private List<SnippetContainer> readSnippetsFromStore() {
		int numSCs = getPreferenceStore().getInt(PreferenceConstants.NUMBER_OF_SNIPPETCONTAINER_INT);
		List<SnippetContainer> snippets = new ArrayList<SnippetContainer>(numSCs);
		for (int i = 0; i < numSCs ; i ++){
			SnippetContainer sc = new SnippetContainer();
			for (SnippetKey key: SnippetKey.values()){
				sc.set(key, getPreferenceStore().getString(key.generatePreferenceKey(i)) );
			}
			snippets.add(i, sc);
		}
		return snippets;
	}
	
	@Override
	public boolean performOk() {
		List<SnippetContainer> store = this.listeditor.store;
		this.snippets = store;
		saveSnippets(getPreferenceStore(), snippets);
		return super.performOk();
	}
	
	public static void saveSnippets(IPreferenceStore store, List<SnippetContainer> snippets) {
		for (int i = 0; i < snippets.size(); i++) {
			SnippetContainer sc = snippets.get(i);			
			for (SnippetKey snipKey : SnippetKey.values()){				
				String preferenceKey = snipKey.generatePreferenceKey(i);
				store.setValue(preferenceKey, sc.get(snipKey));
			}
		}
		store.setValue(PreferenceConstants.NUMBER_OF_SNIPPETCONTAINER_INT,snippets.size());
	}
	
	@Override
	protected void performDefaults() {
		PreferenceInitializer.restoreDefaults(getPreferenceStore());
		syncSnippetsWithPreferenceStore();
		super.performDefaults();
	}
	
	
}