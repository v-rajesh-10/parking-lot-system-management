package com.parking.lot.system.management.client;

import com.parking.lot.system.management.ParkingLotSystemConfigurator;
import com.parking.lot.system.management.ParkingLotSystemManager;
import com.parking.lot.system.management.exception.ParkingException;

/***
 * The Parking Client representing the possible transactions from a given application.
 * @author Rajesh Venkatesan
 */
public interface ParkingClient {

    /**
     * Fetch the client configurations specifying the number of floors in the building, total
     * number of floors in the building and other customizable configuration.
     * @return the non-null instance of {@link ParkingLotSystemConfigurator}.
     */
    ParkingLotSystemConfigurator getSystemConfiguration();

    /**
     * Fetch the instance of the manager to perform the transactions.
     * @return the non-null instance of {@link ParkingLotSystemManager}.
     */
    ParkingLotSystemManager getSystemManager();

    /**
     * Executes the command based on the corresponding values.
     * @param command Command to be executed by the parking lot system.
     * @param values List of values associated for a given command. This parameter would be read based on the command.
     * @return The response to the displayed in the console.
     * @throws ParkingException
     */
    String ExecuteCommand(String command, String values[]) throws ParkingException;

}
