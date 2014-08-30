package com.v4creations.tmd.system.event;

import com.squareup.otto.Bus;

public class TMDEventBus {
    private static TMDEventBus tmdEventBus;
    private MainThreadBus mBus;

    private TMDEventBus() {
        mBus = new MainThreadBus();
    }

    public static TMDEventBus getInstance() {
        if (tmdEventBus == null)
            tmdEventBus = new TMDEventBus();
        return tmdEventBus;
    }

    public static Bus getBus(){
        return getInstance().getEventBus();
    }

    private MainThreadBus getEventBus() {
        return mBus;
    }
}
