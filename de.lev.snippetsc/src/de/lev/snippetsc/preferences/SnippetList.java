package de.lev.snippetsc.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.lev.snippetsc.exceptions.UnreachableCodeException;
import de.lev.snippetsc.preferences.SnippetContainer.SnippetKey;

/**
 * List component for Snippets. If you want to add data call
 * SnippetList#setStore Call SnippetList#init with the parent composite to
 * create the controls
 * 
 * @author leo
 * 
 */
public class SnippetList {

	private Button addButton;
	private Button editButton;
	private Button removeButton;

	// store should now about changed or not
	public List<SnippetContainer> store = new ArrayList<SnippetContainer>();
	private Table table;
	private Collection<SnippetTableListener> tableListener = new ArrayList<SnippetTableListener>();

	/**
	 * Empty constructor! You have to call SnippetList#createContents(Composite)
	 * to actually the contents
	 */
	public SnippetList() {
	}
	
	public void addTableListener(SnippetTableListener l){
		this.tableListener.add(l);
	}
	
	public void removeTableListener(SnippetTableListener l){
		this.tableListener.remove(l);
	}

	public interface SnippetTableListener {

		void tableItemSelected(SelectionEvent e, TableItem item,
				SnippetContainer data);

	}
	
	private void addTableListener(final Table table) {
		table.addListener(SWT.SetData, new Listener() {

			@Override
			public void handleEvent(Event event) {
				TableItem item = (TableItem) event.item;
				int index = table.indexOf(item);
				item.setText("Snippet " + index);
				System.out.println(item.getText());
			}
		});
	}

	private void createContents(Composite grandParent) {
		Composite parent = new Composite(grandParent, SWT.NONE);
		GridLayout gl_parent = new GridLayout(2, false);
		parent.setLayout(gl_parent);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Table table = new Table(parent, SWT.BORDER);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_table.widthHint = 300;
		gd_table.heightHint = 100;
		gd_table.grabExcessHorizontalSpace = true;
		table.setLayoutData(gd_table);

		table.setHeaderVisible(true);

		String[] titles = { "Name", "Shortcut", "Snippet"};
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}

		this.table = table;

		Composite btngroup = new Composite(parent, SWT.NONE);
		GridLayout gl_group = new GridLayout(1, false);
		btngroup.setLayout(gl_group);

		GridData gd_group = new GridData(120, 120);
		btngroup.setLayoutData(gd_group);
		this.addButton = createPushButton(btngroup, "add", false);
		this.editButton = createPushButton(btngroup, "edit", true);
		this.removeButton = createPushButton(btngroup, "remove", false);
	}

	private void createListener() {
		this.editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// take element from table
				handleEdit();
			}
		});

		this.table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				fireTableItemSelected(e);
			}

		});

		this.table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				handleEdit();
			}
		});
	}
	
	private void fireTableItemSelected(SelectionEvent e) {
		TableItem item = this.table.getSelection()[0];
		SnippetContainer data = (SnippetContainer) item.getData();
		for (SnippetTableListener l : this.tableListener) {
			l.tableItemSelected(e,item,data);			
		}		
	}

	private void handleEdit() {
		TableItem[] selection = SnippetList.this.table.getSelection();
		if (selection.length < 1) {
			return;
		}
		if (selection.length > 1) {
			throw new IllegalStateException(
					"table sould only support single selection");
		}
		TableItem item = selection[0];
		SnippetContainer sc = (SnippetContainer) item.getData();
		SnippetContainer result = editSnippet(sc);
		updateSnippetItem(item, result);
	}

	private Button createPushButton(Composite parent, String text,
			boolean enabled) {
		Button button = new Button(parent, SWT.NONE | SWT.PUSH | SWT.CENTER);
		GridData gd_button = new GridData();
		gd_button.grabExcessHorizontalSpace = true;
		gd_button.minimumWidth = 100;
		button.setLayoutData(gd_button);
		button.setEnabled(enabled);
		button.setText(text);
		return button;
	}

	private Shell getShell() {
		return this.addButton.getShell();
	}

	private SnippetContainer editSnippet(SnippetContainer oldsc) {
		SnippetEditor snippetEditor = new SnippetEditor(getShell(),
				oldsc.copy());
		int returnCode = snippetEditor.open();

		SnippetContainer res;
		switch (returnCode) {
		case Window.OK:
			res = snippetEditor.snippetContainer;
			break;
		case Window.CANCEL:
			res = oldsc;
			break;
		default:
			throw new UnreachableCodeException("unexpected return code: "
					+ returnCode);
		}
		return res;
	}

	public void init(Composite parent) {
		createContents(parent);
		loadTable();
		createListener();
	}

	public void loadTable() {
		this.table.removeAll();		
		for (SnippetContainer sc : this.store) {
			TableItem item = new TableItem(this.table, SWT.NONE);
			updateSnippetItem(item, sc);
		}

		for (int i = 0; i < this.table.getColumnCount(); i++) {
			table.getColumn(i).pack();
		}
	}

	public void setStore(List<SnippetContainer> scs) {
		this.store = scs;
	}

	private void updateSnippetItem(TableItem item, SnippetContainer newSC) {
		item.setText(0, newSC.get(SnippetKey.name));
		item.setText(1, newSC.get(SnippetKey.shortcut));
		String snippet = newSC.get(SnippetKey.snippet);
		int firstNewline = snippet.indexOf("\n");
		int until = firstNewline >= 0 ? firstNewline : snippet.length();
//		int maxChars = 50;
//		if(until < snippet.length())
		item.setText(2, snippet.substring(0, until));
		
		Object itemData = item.getData();
		if (itemData != null) {
			if (itemData instanceof SnippetContainer) {
				SnippetContainer sc = (SnippetContainer) itemData;
				sc.update(newSC);
			} else {
				throw new IllegalStateException("only "
						+ SnippetContainer.class.getSimpleName() + " allowed");
			}
		} else {
			item.setData(newSC);
		}
	}

}
