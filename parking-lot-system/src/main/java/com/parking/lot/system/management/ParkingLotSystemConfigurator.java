package com.parking.lot.system.management;

/***
 * Manage Parking Lot System Configuration. It understands the parking system data model and can
 * perform complex operations on the data model.
 * @author Rajesh Venkatesan
 */
public interface ParkingLotSystemConfigurator {

    /**
     * @return The number of floors in the building.
     */
    int getNumberOfFloors();

    /**
     * @param floorId The current floor, e.g. 0 Represents Ground Floor, 1 Represents First Floor, etc.
     * @return The number of slots for the given floor.
     */
    int getNumberOfSlotsPerFloor(int floorId);
}
