package com.parking.lot.system.management.vehicle;

/**
 * A Null Pattern Implementation of {@link ParkedVehicle}.
 * @author Rajesh Venkatesan
 */
public final class NoVehicle implements ParkedVehicle {

    private static final String NO_VEHICLE_REGISTERED_NUMBER = "";
    private static final Type  NO_VEHICLE_TYPE = Type.NONE;
    private static final String NO_VEHICLE_COLOR = null;

    /**
     *
     * @return the registered number of the vehicle.
     */
    @Override
    public String getRegisteredNumber() {
        return NO_VEHICLE_REGISTERED_NUMBER;
    }

    /**
     *
     * @return the type of the vehicle as represented by {@link Type}.
     */
    @Override
    public Type getVehicleType() {
        return NO_VEHICLE_TYPE;
    }

    /**
     *
     * @return the color of the vehicle.
     */
    @Override
    public String getColor() {
        return NO_VEHICLE_COLOR;
    }

    /**
     *
     * @return the display representation of this entity.
     */
    @Override
    public String toString() {
        return "NoVehicle";
    }
}
