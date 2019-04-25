package com.parking.lot.system.management.vehicle;

/**
 * This contract represents a Parking Vehicle entity.
 * @author Rajesh Venkatesan
 */
public interface ParkedVehicle {

    /**
     *
     * @return the registered number of the vehicle.
     */
    String getRegisteredNumber();

    /**
     *
     * @return the type of the vehicle as represented by {@link Type}.
     */
    Type getVehicleType();

    /**
     *
     * @return the color of the vehicle.
     */
    String getColor();
}
