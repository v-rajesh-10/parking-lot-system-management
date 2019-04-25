package com.parking.lot.system.management.gojek.vehicle;

import com.parking.lot.system.management.vehicle.ParkedVehicle;
import com.parking.lot.system.management.vehicle.Type;

public final class Vehicle implements ParkedVehicle{

    private String registeredNumber = null;
    private Type type = Type.NONE;
    private String color = null;

    public static Vehicle create(final String registeredNumber,
                                 final Type type,
                                 final String color)
    {
        return new Vehicle(registeredNumber, type, color);
    }

    private Vehicle(final String registeredNumber,
                    final Type type,
                    final String color)
    {
        this.registeredNumber = registeredNumber;
        this.type = type;
        this.color = color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRegisteredNumber() {
        return registeredNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getVehicleType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColor() {
        return color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return registeredNumber + "\t\t\t" +
               type + "\t\t\t" +
               color + "\t";
    }
}
