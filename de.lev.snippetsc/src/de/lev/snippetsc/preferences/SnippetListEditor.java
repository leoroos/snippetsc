package de.lev.snippetsc.preferences;

import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SnippetListEditor extends ListEditor {

	private Composite parent;

	public SnippetListEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
		this.parent = parent;
	}

	@Override
	protected String createList(String[] items) {
		String result = new String();
		for (String string : items) {
			result += string + ",";
		}
		return result;
	}

	@Override
	protected String getNewInputObject() {

		MyDialog myDialog = new MyDialog(getShell(),SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL);
		
		myDialog.open();
		
		String result = myDialog.getResultText();

		return result;
	}

	@Override
	protected String[] parseString(String stringList) {
		String[] split = stringList.split(",");
		return split;
	}

	public static class MyDialog extends Window {

		public MyDialog(Shell parent, int style) {
			super(parent);
			this.setShellStyle(SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);
		}

		public String getResultText() {
			return text;
		}

		
		public Composite createContents(final Composite composite) {
			composite.setLayout(new FormLayout());

			// Create the sash first, so the other controls
			// can be attached to it.
			final Sash sash = new Sash(composite, SWT.VERTICAL);
			FormData data = new FormData();
			data.top = new FormAttachment(0, 0); // Attach to top
			data.bottom = new FormAttachment(100, 0); // Attach to bottom
			data.left = new FormAttachment(50, 0); // Attach halfway across
			sash.setLayoutData(data);
			sash.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					// We reattach to the left edge, and we use the x value of
					// the
					// event to
					// determine the offset from the left
					((FormData) sash.getLayoutData()).left = new FormAttachment(
							0, event.x);

					// Until the parent window does a layout, the sash will not
					// be
					// redrawn in
					// its new location.
					sash.getParent().layout();
				}
			});

			// Create the first text box and attach its right edge
			// to the sash
			final Text one = new Text(composite, SWT.BORDER);
			data = new FormData();
			data.top = new FormAttachment(0, 0);
			data.bottom = new FormAttachment(100, 0);
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(sash, 0);
			one.setLayoutData(data);

			// Create the second text box and attach its left edge
			// to the sash
			final Text Three = new Text(composite, SWT.BORDER);
			data = new FormData();
			data.top = new FormAttachment(0, 0);
			data.bottom = new FormAttachment(100, 0);
			data.left = new FormAttachment(sash, 0);
			data.right = new FormAttachment(100, 0);
			Three.setLayoutData(data);			

			Button button = new Button(composite, SWT.PUSH);
			button.setText("foobutton");
			button.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					String text2 = one.getText();
					text = Three.getText();
					composite.dispose();		
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					String text2 = one.getText();
					text = Three.getText();
					composite.dispose();
				}

			});
			return composite;
		}
		private String text;
	}

}
