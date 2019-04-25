package com.parking.lot.system.management.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A Simple Integration Test for Console Command Client.
 */
public class ConsoleCommandClientTest {

    private static final String BATCH_RUNNER_SOURCE = "src/test/resources/SimpleBatchCommands.txt";

    private static final String EXPECTED_CONSOLE_MESSAGES = "Created a parking lot with 6 slots\n" +
            "Allocated slot number: 1\n" +
            "Allocated slot number: 2\n" +
            "Allocated slot number: 3\n" +
            "Allocated slot number: 4\n" +
            "Allocated slot number: 5\n" +
            "Allocated slot number: 6\n" +
            "Slot number 4 is free\n" +
            "Slot No.  Registration No.    VehicleType      Colour\n" +
            "1\t\t\tKA-01-HH-1234\t\t\tCAR\t\t\tWhite\t\n" +
            "2\t\t\tKA-01-HH-9999\t\t\tCAR\t\t\tWhite\t\n" +
            "3\t\t\tKA-01-BB-0001\t\t\tCAR\t\t\tBlack\t\n" +
            "5\t\t\tKA-01-HH-2701\t\t\tCAR\t\t\tBlue\t\n" +
            "6\t\t\tKA-01-HH-3141\t\t\tCAR\t\t\tBlack\t\n" +
            "Allocated slot number: 4\n" +
            "Sorry, parking lot is full\n" +
            "[KA-01-HH-1234, KA-01-HH-9999, KA-01-P-333]\n" +
            "[1, 2, 4]\n" +
            "6\n" +
            "Not Found\n";

    private PrintStream existingStream = null;

    @Before
    public void Setup() {
        existingStream = System.out;
    }

    @After
    public void tearDown() {
        System.out.flush();
        System.setOut(existingStream);
    }

    @Test
    public void ExecuteSimpleBatchRunner() throws Exception {

        ByteArrayOutputStream consoleStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleStream));

        PLSApplication.main(new String[]{BATCH_RUNNER_SOURCE});

        Assert.assertEquals(EXPECTED_CONSOLE_MESSAGES, consoleStream.toString());
    }
}
