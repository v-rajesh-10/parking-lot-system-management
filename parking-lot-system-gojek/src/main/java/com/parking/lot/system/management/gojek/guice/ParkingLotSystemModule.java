package com.parking.lot.system.management.gojek.guice;


import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.parking.lot.system.management.ParkingLotSystemConfigurator;
import com.parking.lot.system.management.ParkingLotSystemManager;
import com.parking.lot.system.management.building.ParkingLotSystemBuilding;
import com.parking.lot.system.management.floor.ParkingLotSystemFloor;
import com.parking.lot.system.management.gojek.ParkingLotSystemManagerImpl;
import com.google.inject.PrivateModule;
import com.parking.lot.system.management.gojek.building.ParkingLotSystemBuildingImpl;
import com.parking.lot.system.management.gojek.floor.ParkingLotSystemFloorImpl;
import com.parking.lot.system.management.gojek.space.ParkingLotSystemSpaceImpl;
import com.parking.lot.system.management.space.ParkingLotSystemSpace;

/**
 * Guice module for the ParkingLotSystemManager Implementation.
 * @author Rajesh Venkatesan
 */
public class ParkingLotSystemModule extends PrivateModule
{
    private ParkingLotSystemConfigurator configurator;

    public ParkingLotSystemModule(ParkingLotSystemConfigurator configurator)
    {
        this.configurator = configurator;
    }

    @Override
    protected void configure()
    {
        bind(ParkingLotSystemConfigurator.class).toInstance(configurator);

        install(new FactoryModuleBuilder().implement(ParkingLotSystemBuilding.class, ParkingLotSystemBuildingImpl.class)
                .build(ParkingLotSystemBuildingImpl.ParkingLotSystemBuildingFactory.class));

        install(new FactoryModuleBuilder().implement(ParkingLotSystemFloor.class, ParkingLotSystemFloorImpl.class)
                                          .build(ParkingLotSystemFloorImpl.ParkingLotSystemFloorImplFactory.class));

        install(new FactoryModuleBuilder().implement(ParkingLotSystemSpace.class, ParkingLotSystemSpaceImpl.class)
                .build(ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory.class));

        bind(ParkingLotSystemManager.class).to(ParkingLotSystemManagerImpl.class);


        expose(ParkingLotSystemManager.class);
    }
}
