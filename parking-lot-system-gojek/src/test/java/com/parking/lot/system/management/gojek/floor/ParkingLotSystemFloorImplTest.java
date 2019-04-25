package com.parking.lot.system.management.gojek.floor;

import com.parking.lot.system.management.exception.ParkingException;
import com.parking.lot.system.management.floor.ParkingLotSystemFloor;
import com.parking.lot.system.management.gojek.space.ParkingLotSystemSpaceImpl;
import com.parking.lot.system.management.space.ParkingLotSystemSpace;
import com.parking.lot.system.management.vehicle.NoVehicle;
import com.parking.lot.system.management.vehicle.ParkedVehicle;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

/**
 * Test {@link ParkingLotSystemFloorImpl}.
 */
public class ParkingLotSystemFloorImplTest {

    private ParkingLotSystemFloor instance = null;
    private final ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory mockSpaceFactory
            = Mockito.mock(ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory.class);

    private final ParkingLotSystemSpace mockSpace = Mockito.mock(ParkingLotSystemSpace.class);

    private final ParkingLotSystemSpace mockNextSpace = Mockito.mock(ParkingLotSystemSpace.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArguments()[0].equals(0) ? mockSpace : mockNextSpace;
            }
        }).when(mockSpaceFactory).create(anyInt(), any(ParkedVehicle.class), anyInt());

        Mockito.doReturn(Mockito.mock(ParkedVehicle.class)).when(mockSpace).getVehicle();
        Mockito.doReturn(Mockito.mock(ParkedVehicle.class)).when(mockNextSpace).getVehicle();
    }

    @After
    public void tearDown() {

    }

    /**
     * Test Floor Construction with No Spaces, ensure that
     * {@link ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory} are not even consumed.
     */
    @Test
    public void testFloorConstructionWithNoSpaces_NoSpacesCreated() {
        // Act
        instance = new ParkingLotSystemFloorImpl(0, 0, null,0);

        // Assert
        Assert.assertEquals(Collections.EMPTY_LIST, instance.getSpaces());
    }

    /**
     * Test Floor Construction with Spaces, ensure that
     * {@link ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory} is consumed.
     */
    @Test
    public void testFloorConstructionWithSpaces_SpacesCreated() {
        // Act
        instance = new ParkingLotSystemFloorImpl(1, 0, mockSpaceFactory,0);

        // Assert
        Assert.assertEquals(1, instance.getSpaces().size());
    }

    /**
     * Test available parking space with no spaces created throws {@link ParkingException}.
     * @throws ParkingException
     */
    @Test
    public void testAvailableSpaceInFloorWithNoSpacesCreated_ThrowsParkingException() throws ParkingException {
        // Arrange
        instance = new ParkingLotSystemFloorImpl(0, 0, null,0);
        expectedException.expect(ParkingException.class);

        // Act
        instance.getAvailableSpace();
    }

    /**
     * Test available parking space when no slots created throws {@link ParkingException}.
     * @throws ParkingException
     */
    @Test
    public void testAvailableSpaceInFloorWhenNoSlotsCreated_ThrowsParkingException() throws ParkingException {
        // Arrange
        instance = new ParkingLotSystemFloorImpl(0, 0, mockSpaceFactory,0);
        expectedException.expect(ParkingException.class);

        // Act
        instance.getAvailableSpace();
    }

    /**
     * Test available parking space when slots are full, throws {@link ParkingException}.
     * @throws ParkingException
     */
    @Test
    public void testAvailableSpaceInFloorWhenSlotsAreFull_ReturnParkingSpace() throws ParkingException {
        // Arrange
        instance = new ParkingLotSystemFloorImpl(2, 0, mockSpaceFactory,0);

        expectedException.expect(ParkingException.class);
        expectedException.expectMessage("No value present");

        // Act
        instance.getAvailableSpace();
    }

    /**
     * Test available parking space when slot is available, returns {@link ParkingLotSystemSpace}.
     * @throws ParkingException
     */
    @Test
    public void testAvailableSpaceInFloorWhenSlotsAvailable_ReturnParkingSpace() throws ParkingException {
        // Arrange
        instance = new ParkingLotSystemFloorImpl(1, 0, mockSpaceFactory,0);
        Mockito.doReturn(new NoVehicle()).when(mockSpace).getVehicle();

        // Act
        ParkingLotSystemSpace allocatedSpace = instance.getAvailableSpace();

        // Assert
        Assert.assertEquals(allocatedSpace.getVehicle().getClass(), NoVehicle.class);
    }
}
