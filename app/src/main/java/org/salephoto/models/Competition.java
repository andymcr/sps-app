package org.salephoto.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;


public class Competition extends EventCore {
	private int rank;
	private int maxScore;
	private Date handinDate;
	private Person secretary;
	private List<Person> judges;
	private List<Section> sections;

	public int getRank() {
		return rank;
	}

	public void setRank(final int newRank) {
		rank = newRank;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(final int newMaxScore) {
		maxScore = newMaxScore;
	}

	public Date getHandinDate() {
		return handinDate;
	}

	public void setHandinDate(final Date newHandinDate) {
		handinDate = newHandinDate;
	}

	public Person getSecretary() {
		return secretary;
	}

	public void setSecretary(final Person newSecretary) {
		secretary = newSecretary;
	}

	public List<Person> getJudges() {
		return judges;
	}

	public void setJudges(final List<Person> newJudges) {
		judges = newJudges;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(final List<Section> newSections) {
		sections = newSections;
	}

    @Override
    public int describeContents() {
        return 0;
    }

    protected Competition(final Parcel parcel) {
        super(parcel);

        rank = parcel.readInt();
        maxScore = parcel.readInt();
        handinDate = new Date(parcel.readLong());
        secretary = parcel.readParcelable(Person.class.getClassLoader());
        parcel.readTypedList(judges, Person.CREATOR);
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        super.writeToParcel(parcel, flags);

        parcel.writeInt(rank);
        parcel.writeInt(maxScore);
        parcel.writeLong(handinDate.getTime());
        parcel.writeParcelable(secretary, flags);
        parcel.writeTypedList(judges);
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
    public String toString() {
        return getTitle();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Competition)) {
            return false;
        }

        return getId() == ((Competition) obj).getId();
    }

}
