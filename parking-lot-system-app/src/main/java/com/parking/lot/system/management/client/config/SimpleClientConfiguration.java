package com.parking.lot.system.management.client.config;

import com.parking.lot.system.management.ParkingLotSystemConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Implements {@link ParkingLotSystemConfigurator}.
 */
public class SimpleClientConfiguration implements ParkingLotSystemConfigurator {

    public static final String GROUND_FLOOR_ONLY = "1";

    private String numberOfFloors;

    private String numberOfSlotsPerFloor[];

    public SimpleClientConfiguration(InputStream configFileStream) throws IOException
    {
        Properties properties = new Properties();
        properties.load(configFileStream);
        numberOfFloors = properties.getProperty("NumberOfFloors");

        numberOfSlotsPerFloor = properties.getProperty("SlotsPerFloor").split(",");
    }

    public SimpleClientConfiguration(String numberOfFloors, String[] numberOfSlotsPerFloor)
    {
        this.numberOfFloors = numberOfFloors;
        this.numberOfSlotsPerFloor = numberOfSlotsPerFloor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfFloors() {
        return Integer.valueOf(numberOfFloors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfSlotsPerFloor(int floorId) {
        return Integer.valueOf(numberOfSlotsPerFloor[floorId]);
    }


}
