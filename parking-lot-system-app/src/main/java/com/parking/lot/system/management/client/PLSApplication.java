package com.parking.lot.system.management.client;

import com.parking.lot.system.management.exception.ParkingException;

import java.io.*;

/**
 * Main Application for Parking Lot System Application.
 */
public class PLSApplication {

    public static void main(String args[]) {
        switch (args.length)
        {
            case 0:
            {
                ExecuteCommandsInteractively(ConsoleCommandClient.INSTANCE());
                break;
            }
            case 1:
            {
                try
                {
                    BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
                    ExecuteCommandsFromExternalSource(ConsoleCommandClient.INSTANCE(), reader);
                    reader.close();
                }
                catch (FileNotFoundException exception)
                {
                    System.out.println("File not found in the path specified.");
                    exception.printStackTrace();
                }
                catch (IOException exception)
                {
                    System.out.println("Unable to Close the file.");
                    exception.printStackTrace();
                }
                break;
            }
            default:
            {
                System.out.println("Usage: java -jar parking-lot-system-app-jar <command_file_path>");
                System.out.println("       java -jar parking-lot-system-app-jar <interactive_commands>");
                System.out.println("Refer ReadMe for the Supported Commands...");
                break;
            }
        }
    }

    private static void ExecuteCommandsFromExternalSource(final ParkingClient client, final BufferedReader reader) {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                executeConsoleCommand(client, line);
            }
        } catch (IOException ex) {
            System.out.println("Error in reading the input file.");
            ex.printStackTrace();
        }
    }

    private static void ExecuteCommandsInteractively(final ParkingClient client) {
        System.out.println("Please enter 'bye' to stop the client");
        System.out.println("Interactive mode of Operations...");

        for (;;) {
            try {
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String argLine = bufferRead.readLine();
                if (argLine != null || !argLine.trim().isEmpty()) {
                    if (argLine.equalsIgnoreCase("bye")) {
                        System.out.println("Bye...");
                        break;
                    }
                    executeConsoleCommand(client, argLine);
                }
            } catch(IOException e) {
                System.out.println("Error in reading the input from console.");
                e.printStackTrace();
            }
        }
    }

    private static void executeConsoleCommand(final ParkingClient client, final String argLine) {
        String consoleMessage = "";
        try
        {
            final String command = getCommand(argLine);
            consoleMessage = client.ExecuteCommand(command, getValues(command, argLine));
        } catch (ParkingException exception) {
            consoleMessage = getConsoleStatusDisplay(exception.getReasonCode());
        } finally {
            System.out.println(consoleMessage);
        }
    }

    private static String getCommand(final String argline) {
        return argline.split(ConsoleCommandClient.COMMAND_VALUE_DELIMITER)[0];
    }

    private static String[] getValues(final String command, final String argline) {
        if (command == argline) {
            return new String[]{};
        }
        return argline.substring(command.length() + ConsoleCommandClient.COMMAND_VALUE_DELIMITER.length()).split(ConsoleCommandClient.COMMAND_VALUE_DELIMITER);
    }

    private static String getConsoleStatusDisplay(final ParkingException.ReasonEnum errorCode) {
        final String defaultConsoleStatusMessage = "";
        switch (errorCode)
        {
            case UNKNOWN:
                return "UnKnown Error from ParkingLotSystem Management";
            case SLOT_NOT_CREATED:
                return "No Parking Lots are Created";
            case SLOT_NOT_AVAILABLE:
                return "Sorry, parking lot is full";
            default:
                return defaultConsoleStatusMessage;
        }
    }
}
