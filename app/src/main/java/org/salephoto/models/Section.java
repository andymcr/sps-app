package org.salephoto.models;

import java.io.Serializable;
import java.util.List;


public class Section implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int order;
	private String title;
	private int maxEntries;
	private List<Entry> entries;

	public int getId() {
		return id;
	}

	public void setId(final int newId) {
		id = newId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(final int newOrder) {
		order = newOrder;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String newTitle) {
		title = newTitle;
	}

	public int getMaxEntries() {
		return maxEntries;
	}

	public void setMaxEntries(final int newMaxEntries) {
		maxEntries = newMaxEntries;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(final List<Entry> newEntries) {
		entries = newEntries;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Section)) {
			return false;
		}

		return getId() == ((Section) obj).getId();
	}

}
