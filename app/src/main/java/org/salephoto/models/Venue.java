package org.salephoto.models;

import java.io.Serializable;


public class Venue implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private Location location;

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

	public Location getLocation() {
		return location;
	}

	public void setLocation(final Location newLocation) {
		location = newLocation;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Venue)) {
			return false;
		}

		return getId() == ((Venue) obj).getId();
	}

}
