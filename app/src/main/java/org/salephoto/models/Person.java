package org.salephoto.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Person implements Parcelable {
	private int id;
	private String fullName;

	public int getId() {
		return id;
	}

	public void setId(final int newId) {
		id = newId;
	}

	public String getName() {
		return fullName;
	}

	public void setName(final String newName) {
		fullName = newName;
	}

    @Override
    public int describeContents() {
        return 0;
    }

    protected Person(final Parcel parcel) {

        id = parcel.readInt();
        fullName = parcel.readString();
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeInt(id);
        parcel.writeString(fullName);
    }

    public static final Parcelable.Creator CREATOR = new Competition.Creator() {
        public Competition createFromParcel(Parcel in) {
            return new Competition(in);
        }

        public Competition[] newArray(int size) {
            return new Competition[size];
        }
    };

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
