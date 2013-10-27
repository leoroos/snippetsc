package de.lev.snippetsc.preferences;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.lev.snippetsc.Activator;
import de.lev.snippetsc.preferences.SnippetContainer.SnippetKey;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	private static SnippetContainer[] defaultSnippets = {
			new SnippetContainer("->", "strg+ö", "->"),
			new SnippetContainer("$this->", "strg+ä", "$this->"),
			new SnippetContainer("static access", "strg+ü", "static::"), };
	private static List<SnippetContainer> snippets = Arrays
			.asList(defaultSnippets);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_BOOLEAN, true);
		store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
		store.setDefault(PreferenceConstants.P_STRING, "->");

		setDefaults(store);
	}

	private static void setDefaults(IPreferenceStore store) {
		for (int i = 0; i < snippets.size(); i++) {
			SnippetContainer sc = snippets.get(i);
			for (SnippetKey snipKey : SnippetKey.values()) {
				String preferenceKey = snipKey.generatePreferenceKey(i);
				store.setDefault(preferenceKey, sc.get(snipKey));
			}
		}
		store.setDefault(PreferenceConstants.NUMBER_OF_SNIPPETCONTAINER_INT,
				snippets.size());
	}

	public static void restoreDefaults(IPreferenceStore store) {
		for (int i = 0; i < snippets.size(); i++) {
			SnippetContainer sc = snippets.get(i);
			for (SnippetKey snipKey : SnippetKey.values()) {
				String preferenceKey = snipKey.generatePreferenceKey(i);
				store.setValue(preferenceKey, sc.get(snipKey));
			}
		}
		int numOfSnippets = store
				.getInt(PreferenceConstants.NUMBER_OF_SNIPPETCONTAINER_INT);
		if (numOfSnippets > snippets.size())
			System.out
					.println("should I delete them? I will just leave them for now...");
		else {
			store.setValue(PreferenceConstants.NUMBER_OF_SNIPPETCONTAINER_INT,
					snippets.size());
		}
	}

}
