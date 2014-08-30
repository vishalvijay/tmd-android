package com.v4creations.tmd.system.event;

import com.squareup.otto.Bus;

public class TMDEventBus {
    private static TMDEventBus tmdEventBus;
    private Bus mBus;

    private TMDEventBus() {
        mBus = new Bus();
    }

    public static TMDEventBus getInstance() {
        if (tmdEventBus == null)
            tmdEventBus = new TMDEventBus();
        return tmdEventBus;
    }

    public static Bus getBus(){
        return getInstance().getEventBus();
    }

    private Bus getEventBus() {
        return mBus;
    }
}
