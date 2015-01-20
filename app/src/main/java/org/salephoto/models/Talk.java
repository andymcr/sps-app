package org.salephoto.models;

import android.os.Parcel;


public class Talk extends EventCore {
    @Override
    public int describeContents() {
        return 0;
    }

    protected Talk(final Parcel parcel) {
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
        if (!(obj instanceof Talk)) {
            return false;
        }

        return getId() == ((Talk) obj).getId();
    }

}
