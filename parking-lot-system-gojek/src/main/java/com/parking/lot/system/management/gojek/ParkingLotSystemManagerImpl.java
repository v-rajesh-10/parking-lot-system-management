
package com.parking.lot.system.management.gojek;

import com.google.inject.Inject;
import com.parking.lot.system.management.ParkingLotSystemConfigurator;
import com.parking.lot.system.management.ParkingLotSystemManager;
import com.parking.lot.system.management.building.ParkingLotSystemBuilding;
import com.parking.lot.system.management.exception.ParkingException;
import com.parking.lot.system.management.gojek.building.ParkingLotSystemBuildingImpl;
import com.parking.lot.system.management.gojek.floor.ParkingLotSystemFloorImpl;
import com.parking.lot.system.management.gojek.query.ParkingLotQueryManagerImpl;
import com.parking.lot.system.management.gojek.space.ParkingLotSystemSpaceImpl;
import com.parking.lot.system.management.space.ParkingLotSystemSpace;
import com.parking.lot.system.management.vehicle.ParkedVehicle;

/**
 * Implementation of {@link ParkingLotSystemManager}.
 */
public class ParkingLotSystemManagerImpl implements ParkingLotSystemManager {

    private ParkingLotSystemBuilding parkingLotSystemBuilding = null;

    private ParkingLotQueryManagerImpl parkingLotManageQuery = null;


    @Inject
    public ParkingLotSystemManagerImpl(final ParkingLotSystemBuildingImpl.ParkingLotSystemBuildingFactory buildingFactory,
                                       final ParkingLotSystemFloorImpl.ParkingLotSystemFloorImplFactory floorFactory,
                                       final ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory spaceFactory,
                                       final ParkingLotSystemConfigurator configurator) {
        parkingLotSystemBuilding = buildingFactory.create(configurator, floorFactory, spaceFactory);
        parkingLotManageQuery = new ParkingLotQueryManagerImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int ParkVehicle(ParkedVehicle vehicle) throws ParkingException {
        ParkingLotSystemSpace parkingSpace = parkingLotSystemBuilding.getAvailableParkingSpace();
        parkingSpace.Park(vehicle);
        int offsetFromEntrance = parkingSpace.getOffsetFromEntrance();

        parkingLotManageQuery.AddVehicleInformation(parkingSpace);

        return offsetFromEntrance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void Leave(int slotId) throws ParkingException {
        ParkingLotSystemSpace parkingSpace = parkingLotSystemBuilding.getParkingSpaceBySlotID(slotId);
        parkingSpace.Leave();

        parkingLotManageQuery.RemoveVehicleInformation(slotId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query getQuery() {
        return parkingLotManageQuery;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStatusDisplay() {
        return parkingLotSystemBuilding.getStatusDisplay();
    }
}

