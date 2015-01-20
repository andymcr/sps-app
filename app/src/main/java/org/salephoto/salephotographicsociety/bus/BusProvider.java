package org.salephoto.salephotographicsociety.bus;

import com.squareup.otto.Bus;

public class BusProvider {
    private static Bus bus;

    public static Bus getBus() {
        if (bus == null) {
            bus = new Bus();
        }

        return bus;
    }

}
