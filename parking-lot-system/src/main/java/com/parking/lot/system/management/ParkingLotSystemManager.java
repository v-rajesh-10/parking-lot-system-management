package com.parking.lot.system.management;

import com.parking.lot.system.management.exception.ParkingException;
import com.parking.lot.system.management.vehicle.ParkedVehicle;

import java.util.List;

/***
 * Manage Parking Lot System Ticketing. It understands the parking system data model and can
 * perform complex operations on the data model.
 * @author Rajesh Venkatesan
 */
public interface ParkingLotSystemManager extends ParkingLotSystemStatus {

    /**
     * Park a vehicle on a parking slot closer to the entrance.
     * @param vehicle the instance of {@link ParkedVehicle} parked.
     * @return the positive integer representing the parked slot.
     * @throws ParkingException
     */
    int ParkVehicle(ParkedVehicle vehicle) throws ParkingException;


    /**
     * Deallocate the parking lot based on the slot identifier
     * @param slotId the identifier representing the distance form the entrance.
     * @throws ParkingException
     */
    void Leave(int slotId) throws ParkingException;

    /**
     * Fetch the query instance
     * @return the non null instance of {@link Query}.
     */
    Query getQuery();

    /**
     * Internal Query for managing simple queries.
     */
    interface Query
    {
        /**
         * Simple Query to find all the registered vehicles for a given color.
         * @param color The color of the vehicle.
         * @return The non-null list of registered numbers.
         */
        List<String> FindRegistrationNumberForVehicleWithColor(String color);

        /**
         * Simple Query to find all the slot numbers for a given color.
         * @param color The color of the vehicle.
         * @return The non-null list of slot numbers.
         */
        List<Integer> FindParkingLotSlotNumbersForVehicleWithColor(String color);

        /**
         * Simple Query to find the slot number for a vehicle by it's registration number.
         * @param registrationNumber The registration number of the vehicle.
         * @return The slot number of the parking space.
         */
        Integer FindSlotNumberByVehicleRegistrationNumber(String registrationNumber);
    }
}
