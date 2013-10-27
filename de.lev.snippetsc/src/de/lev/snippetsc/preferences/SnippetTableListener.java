package de.lev.snippetsc.preferences;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableItem;

public interface SnippetTableListener {

	void tableItemSelected(SelectionEvent e, TableItem item,
			SnippetContainer data);

}
