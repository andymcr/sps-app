package org.salephoto.models;

import java.io.Serializable;
import java.util.List;


public class Image {
	private int id;
	private String title;
	private Person author;
	private boolean hasAward;
	private List<Entry> entries;

	public int getId() {
		return id;
	}

	public void setId(final int newId) {
		id = newId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String newTitle) {
		title = newTitle;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(final Person newAuthor) {
		author = newAuthor;
	}

	public boolean hasAward() {
		return hasAward;
	}

	public void setHasAward(final boolean newHasAward) {
		hasAward = newHasAward;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(final List<Entry> newEntries) {
		entries = newEntries;
	}

    @Override
    public String toString() {
        return getTitle();
    }

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Image)) {
			return false;
		}

		return getId() == ((Image) obj).getId();
	}

}
