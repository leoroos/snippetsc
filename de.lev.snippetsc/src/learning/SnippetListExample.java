package learning;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import de.lev.snippetsc.preferences.SnippetContainer;
import de.lev.snippetsc.preferences.SnippetList;

public class SnippetListExample {
	
	public static void main(String[] args) {
		mine(args);
//		example(args);
	}

	public static void mine(String[] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setLayout(new GridLayout());
		
		SnippetContainer[] scs ={
				new SnippetContainer("->", "strg+ö", "->"),
				new SnippetContainer("$this->", "strg+ä", "$this->"),
				new SnippetContainer("static access", "strg+ü", "static::"),
		};
		List<SnippetContainer> store = Arrays.asList(scs);
		
		SnippetList snippetList = new SnippetList();
		snippetList.setStore(store);
		snippetList.init(shell);
				
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
public static void example (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new GridLayout());
	Table table = new Table (shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
	table.setLinesVisible (true);
	table.setHeaderVisible (true);
	GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
	data.heightHint = 200;
	table.setLayoutData(data);
	String[] titles = {" ", "C", "!", "Description", "Resource", "In Folder", "Location"};
	for (int i=0; i<titles.length; i++) {
		TableColumn column = new TableColumn (table, SWT.NONE);
		column.setText (titles [i]);
	}	
	int count = 128;
	for (int i=0; i<count; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText (0, "x");
		item.setText (1, "y");
		item.setText (2, "!");
		item.setText (3, "this stuff behaves the way I expect");
		item.setText (4, "almost everywhere");
		item.setText (5, "some.folder");
		item.setText (6, "line " + i + " in nowhere");
	}
	for (int i=0; i<titles.length; i++) {
		table.getColumn (i).pack ();
	}	
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 