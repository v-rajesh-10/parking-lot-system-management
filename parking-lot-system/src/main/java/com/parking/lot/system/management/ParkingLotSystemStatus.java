package com.parking.lot.system.management;

/***
 * Manage Parking Lot System Status Display. All the individual entities need to implement this interface
 * for providing the corresponding status.
 * @author Rajesh Venkatesan
 */
public interface ParkingLotSystemStatus {

    /**
     *
     * @return the status of the parking entity.
     */
    String getStatusDisplay();
}
