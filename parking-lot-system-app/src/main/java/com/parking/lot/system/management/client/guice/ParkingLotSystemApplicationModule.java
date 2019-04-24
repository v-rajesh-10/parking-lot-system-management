package com.parking.lot.system.management.client.guice;

import com.google.inject.AbstractModule;
import com.parking.lot.system.management.ParkingLotSystemConfigurator;
import com.parking.lot.system.management.gojek.guice.ParkingLotSystemModule;

public class ParkingLotSystemApplicationModule extends AbstractModule {

    ParkingLotSystemConfigurator configurator;

    public ParkingLotSystemApplicationModule(ParkingLotSystemConfigurator configurator) {
        this.configurator = configurator;
    }

    /**
     * Configures a {@link Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        install(new ParkingLotSystemModule(this.configurator));
    }
}
