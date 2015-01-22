package org.salephoto.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Person {
	private int id;
	private String name;

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
		this.name = newName;
	}

    @Override
    public String toString() {
        return getName();
    }

    @Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Person)) {
			return false;
		}

		return getId() == ((Person) obj).getId();
	}

}
