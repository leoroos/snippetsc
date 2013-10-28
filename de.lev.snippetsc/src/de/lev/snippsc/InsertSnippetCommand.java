package de.lev.snippsc;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import de.lev.snippetsc.Activator;
import de.lev.snippetsc.preferences.SnippetContainer.SnippetKey;

public abstract class InsertSnippetCommand extends AbstractHandler {

	public static class InsertSnippetCommand1 extends InsertSnippetCommand {
		@Override
		protected int getSnippetNumber() {
			return 0;
		}
	}

	public static class InsertSnippetCommand2 extends InsertSnippetCommand {
		@Override
		protected int getSnippetNumber() {
			return 1;
		}
	}

	public static class InsertSnippetCommand3 extends InsertSnippetCommand {
		@Override
		protected int getSnippetNumber() {
			return 2;
		}
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorInput activeEditorInput = HandlerUtil.getActiveEditorInput(event);
		IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		
		if (activeEditor instanceof ITextEditor) {
			ITextEditor texteditor = (ITextEditor) activeEditor;
			ISelectionProvider selp = texteditor.getSelectionProvider();
			IDocumentProvider documentProvider = texteditor.getDocumentProvider();
			IDocument document = documentProvider.getDocument(activeEditorInput);
			ISelection selection = selp.getSelection();

			if (selection instanceof ITextSelection) {
				ITextSelection textsel = (ITextSelection) selection;
				int offset = textsel.getOffset();				
				try {
					int snippetnr = getSnippetNumber();
					String generatePreferenceKey = SnippetKey.snippet.generatePreferenceKey(snippetnr);					
					String insertString = Activator.getDefault().getPreferenceStore().getString(generatePreferenceKey);
					
					document.replace(offset, 0, insertString);
					selp.setSelection(new TextSelection(offset + insertString.length(), 0));
				} catch (BadLocationException e) {
					//TODO use logging subsystem
					e.printStackTrace();
				}
			}
		}
		/*
		 * Nothing to do otherwise
		 */
		return null;
	}

	protected abstract int getSnippetNumber();
	

}
