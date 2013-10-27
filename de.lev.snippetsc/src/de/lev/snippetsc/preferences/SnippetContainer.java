package de.lev.snippetsc.preferences;

import java.util.HashMap;
import java.util.Map;

final public class SnippetContainer {

	public enum SnippetKey {
		name("Name"), shortcut("Shortcut"), snippet("Snippet");
		final private String humanreadable;
		private SnippetKey(String humanreadable) {
			this.humanreadable = humanreadable;
		}
		public String generatePreferenceKey(int snippetNumber){
			return "de.lev.snippetsc.snippetcontainer."+snippetNumber+"."+this.name();
		};
		@Override
		public String toString() {
			return this.humanreadable;
		}
	}

	private Map<SnippetKey, String> data = new HashMap<SnippetKey, String>();

	public SnippetContainer() {
	}

	/**
	 * Copy constructor
	 */
	public SnippetContainer(SnippetContainer dc) {
		this.data.putAll(dc.data);
	}

	public SnippetContainer(String name, String shortcut, String snippet) {
		this.data.put(SnippetKey.name, name);
		this.data.put(SnippetKey.shortcut, shortcut);
		this.data.put(SnippetKey.snippet, snippet);
	}

	public SnippetContainer copy() {
		return new SnippetContainer(this);
	}

	public String get(SnippetKey key) {
		return this.data.get(key);
	}

	public void set(SnippetKey key, String value) {
		this.data.put(key, value);
	}
	
	public void update(SnippetContainer sc){
		for (SnippetKey k : SnippetKey.values()) {
			this.set(k, sc.get(k));
		}
	}
}