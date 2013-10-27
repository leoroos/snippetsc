package de.lev.snippetsc.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.lev.snippetsc.preferences.SnippetContainer.SnippetKey;

public class SnippetEditor extends Dialog {

	final public SnippetContainer snippetContainer;

	private List<Text> textControls = new ArrayList<Text>(3);

	public SnippetEditor(Shell parentShell) {
		this(parentShell, new SnippetContainer());
	}

	public SnippetEditor(Shell parentShell, SnippetContainer data) {
		super(parentShell);
		if (data == null)
			throw new IllegalArgumentException(
					"null is not a valid argument for Snippetcontainer data");
		this.snippetContainer = data;
	}	

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Edit Snippet");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout dialogLayout = new GridLayout(2, false);
		container.setLayout(dialogLayout);

		createFieldLabel(container, "Snippet name:");
		Text nameText = createFieldText(container);
		
		createFieldLabel(container, "Shortcut:");
		Text shortcutText = createFieldText(container);

		Label lblEditSnippet = new Label(container, SWT.NONE);
		lblEditSnippet.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true,
				false, 2, 0));
		lblEditSnippet.setText("Template Content:");

		Text templateText = new Text(container, SWT.MULTI
				| SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd_templateText = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_templateText.horizontalSpan = 2;
		gd_templateText.widthHint = 400;
		gd_templateText.heightHint = 150;
		templateText.setLayoutData(gd_templateText);

		nameText.setData(SnippetKey.name);
		templateText.setData(SnippetKey.snippet);
		shortcutText.setData(SnippetKey.shortcut);
		
		this.textControls.add(nameText);
		this.textControls.add(templateText);
		this.textControls.add(shortcutText);
		
		for (Text t : this.textControls) {
			t.setText(this.snippetContainer.get((SnippetKey) t.getData()));
		}

		return container;
	}


	private void createFieldLabel(Composite container, String lblName) {
		Label lblSnippetName = new Label(container, SWT.NONE);
		lblSnippetName.setText(lblName);
	}

	private Text createFieldText(Composite container) {
		Text nameText = new Text(container, SWT.BORDER);
		GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalIndent = 8;
		nameText.setLayoutData(gd_nameText);
		return nameText;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void okPressed() {
	    for (Text text : this.textControls ) {
			SnippetKey data = (SnippetKey) text.getData();
			this.snippetContainer.set(data, text.getText());
		}
		super.okPressed();
	}

}