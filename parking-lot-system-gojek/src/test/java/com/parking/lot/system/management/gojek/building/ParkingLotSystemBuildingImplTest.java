package com.parking.lot.system.management.gojek.building;

import com.parking.lot.system.management.ParkingLotSystemConfigurator;
import com.parking.lot.system.management.building.ParkingLotSystemBuilding;
import com.parking.lot.system.management.exception.ParkingException;
import com.parking.lot.system.management.floor.ParkingLotSystemFloor;
import com.parking.lot.system.management.gojek.floor.ParkingLotSystemFloorImpl;
import com.parking.lot.system.management.gojek.space.ParkingLotSystemSpaceImpl;
import com.parking.lot.system.management.space.ParkingLotSystemSpace;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

/**
 * Test {@link ParkingLotSystemSpaceImpl}.
 */
public class ParkingLotSystemBuildingImplTest {

    private ParkingLotSystemBuilding instance = null;
    private final ParkingLotSystemConfigurator mockConfigurator = Mockito.mock(ParkingLotSystemConfigurator.class);
    private final ParkingLotSystemFloorImpl.ParkingLotSystemFloorImplFactory mockFloorFactory
                                  = Mockito.mock(ParkingLotSystemFloorImpl.ParkingLotSystemFloorImplFactory.class);
    private final ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory mockSpaceFactory
                                       = Mockito.mock(ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory.class);

    private final ParkingLotSystemFloor mockFloor = Mockito.mock(ParkingLotSystemFloor.class);

    private final ParkingLotSystemFloor mockNextFloor = Mockito.mock(ParkingLotSystemFloor.class);

    private final ParkingLotSystemSpace mockSpace = Mockito.mock(ParkingLotSystemSpace.class);

    private static int UT_NUMBER_OF_FLOORS = 2;
    private static int UT_NUMBER_OF_SLOTS_PER_FLOOR = 5;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        Mockito.doReturn(UT_NUMBER_OF_FLOORS).when(mockConfigurator).getNumberOfFloors();
        Mockito.doReturn(UT_NUMBER_OF_SLOTS_PER_FLOOR).when(mockConfigurator).getNumberOfSlotsPerFloor(anyInt());

        List<ParkingLotSystemSpace> spaces = new ArrayList();
        spaces.add(mockSpace);
        Mockito.doReturn(spaces).when(mockFloor).getSpaces();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArguments()[1].equals(0) ? mockFloor : mockNextFloor;
            }
        }).when(mockFloorFactory).create(anyInt(), anyInt(), any(ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory.class), anyInt());

        instance = new ParkingLotSystemBuildingImpl(mockConfigurator, mockFloorFactory, mockSpaceFactory);
    }

    @After
    public void tearDown() {

    }

    /**
     * Test Building Construction with No floors, ensure that {@link ParkingLotSystemFloorImpl.ParkingLotSystemFloorImplFactory}
     * and {@link ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory} are not even consumed.
     */
    @Test
    public void testBuildingConstructionWithNoFloors_NoFloorsCreated() {
        // Arrange
        Mockito.doReturn(0).when(mockConfigurator).getNumberOfFloors();

        // Act
        instance = new ParkingLotSystemBuildingImpl(mockConfigurator, null, null);

        // Assert
        Assert.assertEquals(Collections.EMPTY_LIST, instance.getFloors());
    }

    /**
     * Test Building Construction with floors, creates non-null instance of
     * {@link ParkingLotSystemFloorImpl.ParkingLotSystemFloorImplFactory} and
     * {@link ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory}.
     */
    @Test
    public void testBuildingConstructionWithFloors_FloorsCreated() {
        // Act
        instance = new ParkingLotSystemBuildingImpl(mockConfigurator, mockFloorFactory, mockSpaceFactory);

        // Assert
        Assert.assertTrue(UT_NUMBER_OF_FLOORS == instance.getFloors().size());
    }

    /**
     * Test available parking space for a building with no floors throws {@link ParkingException}.
     * @throws ParkingException
     */
    @Test
    public void testAvailableParkingSpaceWithNoFloors_ThrowsException() throws ParkingException {
        // Arrange
        Mockito.doReturn(0).when(mockConfigurator).getNumberOfFloors();
        expectedException.expect(ParkingException.class);
        expectedException.expectMessage("No Floors in the Building");

        // Act
        instance = new ParkingLotSystemBuildingImpl(mockConfigurator, null, null);

        // Assert
        instance.getAvailableParkingSpace();
    }

    /**
     * Test available parking space for a building with floors and retrieval of space from ground floor,
     * returns {@link ParkingLotSystemSpace}.
     */
    @Test
    public void testAvailableParkingSpaceWithGroundFloorsAvailable_ReturnParkingSpace() throws ParkingException {
        // Arrange
        Mockito.doReturn(mockSpace).when(mockFloor).getAvailableSpace();

        // Act
        ParkingLotSystemSpace allocatedParkingSpace = instance.getAvailableParkingSpace();

        // Assert
        Assert.assertNotNull(allocatedParkingSpace);
    }

    /**
     * Test available parking space for a building with floors and retrieval of space when the Ground floor is full,
     * allocates floor from next floor {@link ParkingLotSystemSpace}.
     */
    @Test
    public void testAvailableParkingSpaceWithGroundFloorFull_ReturnSpaceInNextFloor() throws ParkingException {
        // Arrange
        Mockito.doThrow(ParkingException.class).when(mockFloor).getAvailableSpace();
        Mockito.doReturn(mockSpace).when(mockNextFloor).getAvailableSpace();

        // Act
        ParkingLotSystemSpace allocatedParkingSpace = instance.getAvailableParkingSpace();

        // Assert
        Assert.assertNotNull(allocatedParkingSpace);
    }

    /**
     * Test parking space by slot identifier with no floors, throws {@link ParkingException}.
     * @throws ParkingException
     */
    @Test
    public void testAvailableParkingSpaceBySlotIDWithNoFloors_ThrowsException() throws ParkingException {
        // Arrange
        Mockito.doReturn(0).when(mockConfigurator).getNumberOfFloors();
        instance = new ParkingLotSystemBuildingImpl(mockConfigurator, null, null);

        expectedException.expect(ParkingException.class);
        expectedException.expectMessage("No Floors in the Building");

        // Act
        instance.getParkingSpaceBySlotID(1);
    }

    /**
     * Test parking space by slot identifier not created, throws {@link ParkingException}.
     * @throws ParkingException
     */
    @Test
    public void testAvailableParkingSpaceBySlotIdNotExisting_ThrowsException() throws ParkingException {
        // Arrange
        expectedException.expect(ParkingException.class);
        expectedException.expectMessage("No value present");

        // Act
        instance.getParkingSpaceBySlotID(Integer.MIN_VALUE);
    }

    /**
     * Test p[arking space for an existing slof identifier, returns {@link ParkingLotSystemSpace}.
     * @throws ParkingException
     */
    @Test
    public void testParkingSpaceForAnExistingSlotId_ReturnsParkingSpace() throws ParkingException {
        // Arrange
        Mockito.doReturn(1).when(mockSpace).getOffsetFromEntrance();

        // Act
        ParkingLotSystemSpace allocatedSpace = instance.getParkingSpaceBySlotID(1);

        // Assert
        Assert.assertNotNull(allocatedSpace);
    }
}
