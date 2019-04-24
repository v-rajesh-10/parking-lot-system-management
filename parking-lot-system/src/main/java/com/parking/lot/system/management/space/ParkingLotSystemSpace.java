package com.parking.lot.system.management.space;

import com.parking.lot.system.management.floor.ParkingLotSystemFloor;
import com.parking.lot.system.management.vehicle.ParkedVehicle;

/***
 * Represents the Parking lot Space.
 * @author Rajesh Venkatesan
 */
public interface ParkingLotSystemSpace {

    /**
     *
     * @return The floor as represented by {@link ParkingLotSystemFloor}.
     */
    int getLevel();

    /**
     * @return The vehicle as represented by {@link ParkedVehicle}.
     */
    ParkedVehicle getVehicle();

    /**
     * @return The unit representing the offset distance from entrance.
     */
    int getOffsetFromEntrance();

    /**
     *
     * @param vehicle the instance of {@link ParkedVehicle} parked.
     */
    void Park(ParkedVehicle vehicle);

    /**
     * Leave the parking space.
     */
    void Leave();
}
