package org.salephoto.models;

import android.os.Parcel;


public class Talk extends EventCore {
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
