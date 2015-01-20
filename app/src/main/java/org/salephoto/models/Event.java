package org.salephoto.models;

import android.os.Parcel;


public class Event extends EventCore {
	private static final long serialVersionUID = 1L;

	private int category;
	private int competitionId;
	private int talkId;

	public int getCategory() {
		return category;
	}

	public void setCategory(final int newCategory) {
		category = newCategory;
	}

	public int getCompetitionId() {
		return competitionId;
	}

	public void setCompetitionId(final int newId) {
		competitionId = newId;
	}

	public int getTalkId() {
		return talkId;
	}

	public void setTalkId(final int newId) {
		talkId = newId;
	}

    @Override
    public int describeContents() {
        return 0;
    }

    protected Event(final Parcel parcel) {
        super(parcel);
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        super.writeToParcel(parcel, flags);
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Event)) {
            return false;
        }

        return getId() == ((Event) obj).getId();
    }

}
