package com.parking.lot.system.management.building;

import com.parking.lot.system.management.ParkingLotSystemStatus;
import com.parking.lot.system.management.exception.ParkingException;
import com.parking.lot.system.management.floor.ParkingLotSystemFloor;
import com.parking.lot.system.management.space.ParkingLotSystemSpace;

import java.util.List;

/***
 * Represents the building of the Parking lot.
 * @author Rajesh Venkatesan
 */
public interface ParkingLotSystemBuilding extends ParkingLotSystemStatus {

    /**
     *
     * @return the list of {@link ParkingLotSystemFloor} in the given building.
     */
    List<ParkingLotSystemFloor> getFloors();

    /**
     *
     * @return the non null instance of {@link ParkingLotSystemSpace} available for parking.
     * @throws ParkingException
     */
    ParkingLotSystemSpace getAvailableParkingSpace() throws ParkingException;

    /**
     *
     * @param slotId the slot identifier of the {@link ParkingLotSystemSpace}.
     * @return the non null instance of {@link ParkingLotSystemSpace}.
     * @throws ParkingException
     */
    ParkingLotSystemSpace getParkingSpaceBySlotID(int slotId) throws ParkingException;
}
