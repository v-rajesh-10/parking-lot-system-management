package com.parking.lot.system.management.floor;

import com.parking.lot.system.management.ParkingLotSystemStatus;
import com.parking.lot.system.management.exception.ParkingException;
import com.parking.lot.system.management.space.ParkingLotSystemSpace;

import java.util.List;

/**
 *
 */
public interface ParkingLotSystemFloor extends ParkingLotSystemStatus {

    /**
     *
     * @return the list of {@link ParkingLotSystemSpace} for a given floor.
     */
    List<ParkingLotSystemSpace> getSpaces();

    /**
     *
     * @return the capacity of slots available in a given floor.
     */
    int getSlotsCapacity();

    /**
     *
     * @return the level of the floor.
     */
    int getLevel();

    /**
     * Attempt to fetch an available parking space.
     * @return the non-null instance of {@link ParkingLotSystemSpace} representing the available parking space.
     * @throws ParkingException
     */
    ParkingLotSystemSpace getAvailableSpace() throws ParkingException;
}
