package de.lev.snippetsc.preferences;

import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;

public class SnippetListEditor extends ListEditor {

	public SnippetListEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
	}
	
	@Override
	protected String createList(String[] items) {		
		return "the created list";
	}

	@Override
	protected String getNewInputObject() {
		return "getNewInputObject";
	}

	@Override
	protected String[] parseString(String stringList) {
		return new String[] {"asdf", "asdf"};
	}

}
