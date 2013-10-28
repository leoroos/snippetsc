package learning;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.lev.snippetsc.preferences.SnippetContainer;
import de.lev.snippetsc.preferences.SnippetContainer.SnippetKey;
import de.lev.snippetsc.preferences.SnippetList;
import de.lev.snippetsc.preferences.SnippetList.SnippetTableListener;

public class SnippetListWPreviewExample {
	
	public static void main(String args[]) {
		new SnippetListWPreviewExample().mine();
//		example(args);
	}

	private Text text;

	public void mine() {
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
		snippetList.addTableListener(new SnippetTableListener() {
			
			@Override
			public void tableItemSelected(SelectionEvent e, TableItem item,
					SnippetContainer data) {
				text.setText(data.get(SnippetKey.snippet));				
			}
		});
		snippetList.setStore(store);
		snippetList.init(shell);
		
		createPreviewText(shell);
				
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}

	private Text createPreviewText(Shell shell) {
		Text text = new Text(shell, SWT.MULTI | SWT.READ_ONLY );
		GridData gd = new GridData(SWT.FILL | SWT.V_SCROLL | SWT.H_SCROLL);
		gd.horizontalAlignment = SWT.FILL;
		gd.widthHint = 400;
		gd.heightHint = 100;		
		gd.verticalAlignment = SWT.FILL;
		gd.horizontalSpan = 2;
		text.setLayoutData(gd);
		return text;
	}
	
} 