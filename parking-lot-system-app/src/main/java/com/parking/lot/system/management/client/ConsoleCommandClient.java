package com.parking.lot.system.management.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.parking.lot.system.management.ParkingLotSystemConfigurator;
import com.parking.lot.system.management.ParkingLotSystemManager;
import com.parking.lot.system.management.client.config.SimpleClientConfiguration;
import com.parking.lot.system.management.client.guice.ParkingLotSystemApplicationModule;
import com.parking.lot.system.management.exception.ParkingException;
import com.parking.lot.system.management.gojek.vehicle.Vehicle;
import com.parking.lot.system.management.vehicle.Type;

import java.io.IOException;
import java.io.InputStream;

/**
 * The Console driven client.
 */
public final class ConsoleCommandClient implements ParkingClient {

    public static final String COMMAND_VALUE_DELIMITER = " ";

    private static final String STATUS_HEADER = "Slot No.  Registration No.    VehicleType      Colour\n";
    private static final String CREATED_STATUS_MESSAGE = "Created a parking lot with %s slots";
    private static final String PARK_STATUS_MESSAGE = "Allocated slot number: %d";
    private static final String LEAVE_STATUS_MESSAGE = "Slot number %s is free";

    private static final String QUERY_SLOT_NUMBER_NOT_FOUND = "Not Found";


    private static final ConsoleCommandClient INSTANCE = new ConsoleCommandClient();

    private ParkingLotSystemManager parkingLotSystemManager = null;

    private ParkingLotSystemConfigurator configurator = null;

    private ConsoleCommandClient() {
        configurator = createSystemConfigurator();
        parkingLotSystemManager = createSystemManager();
    }

    static ConsoleCommandClient INSTANCE() {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParkingLotSystemConfigurator getSystemConfiguration() {
        return this.configurator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParkingLotSystemManager getSystemManager() {
        return this.parkingLotSystemManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String ExecuteCommand(String command, String values[]) throws ParkingException {
        String defaultResponse = "Command Not Supported -> ";
        switch (command.toUpperCase())
        {
            case "CREATE_PARKING_LOT":
            {
                configurator = new SimpleClientConfiguration(SimpleClientConfiguration.GROUND_FLOOR_ONLY, values);
                parkingLotSystemManager = createSystemManager();
                return String.format(CREATED_STATUS_MESSAGE, values[0]);
            }

            case "PARK":
            {
                int allocatedSlot =  getSystemManager().ParkVehicle(Vehicle.create(values[0], Type.CAR, values[1]));
                return String.format(PARK_STATUS_MESSAGE, allocatedSlot);
            }

            case "LEAVE":
            {
                getSystemManager().Leave(Integer.valueOf(values[0]));
                return String.format(LEAVE_STATUS_MESSAGE, values[0]);
            }

            case "STATUS":
            {
                return STATUS_HEADER + getSystemManager().getStatusDisplay();
            }

            case "REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR":
            {
                return getSystemManager().getQuery().FindRegistrationNumberForVehicleWithColor(values[0]).toString();
            }

            case "SLOT_NUMBERS_FOR_CARS_WITH_COLOUR":
            {
                return getSystemManager().getQuery().FindParkingLotSlotNumbersForVehicleWithColor(values[0]).toString();
            }

            case "SLOT_NUMBER_FOR_REGISTRATION_NUMBER":
            {
                Integer slotNumber = getSystemManager().getQuery().FindSlotNumberByVehicleRegistrationNumber(values[0]);
                if (slotNumber > 0) {
                    return slotNumber.toString();
                }
                return QUERY_SLOT_NUMBER_NOT_FOUND;
            }
        }
        return defaultResponse + command;
    }

    /**
     *
     * @return the instance of {@link ParkingLotSystemConfigurator}.
     */
    private ParkingLotSystemConfigurator createSystemConfigurator() {
        InputStream configStream = null;
        try
        {
            configStream = PLSApplication.class.getResourceAsStream("/config.properties");
            return new SimpleClientConfiguration(configStream);
        }
        catch (IOException exception) { }
        finally {
            if (configStream != null) {
                try
                {
                    configStream.close();
                } catch (IOException exception){ }
            }
        }
        return null;
    }

    /**
     * The single entry point for creating instance.
     * @return the non-null instance of {@link ParkingLotSystemManager}.
     */
    private ParkingLotSystemManager createSystemManager() {
        Injector injector = Guice.createInjector(new ParkingLotSystemApplicationModule(getSystemConfiguration()));
        return injector.getInstance(ParkingLotSystemManager.class);
    }
}
