package org.salephoto.models;

import java.io.Serializable;
import java.util.List;

public class Organisation implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private List<Person> members;

	public int getId() {
		return id;
	}

	public void setId(final int newId) {
		id = newId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String newName) {
		name = newName;
	}

	public List<Person> getMembers() {
		return members;
	}

	public void setMembers(final List<Person> newMembers) {
		members = newMembers;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Organisation)) {
			return false;
		}

		return getId() == ((Organisation) obj).getId();
	}

}
