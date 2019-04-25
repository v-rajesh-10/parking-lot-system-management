package com.parking.lot.system.management.gojek.space;

import com.parking.lot.system.management.space.ParkingLotSystemSpace;
import com.parking.lot.system.management.vehicle.ParkedVehicle;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test {@link ParkingLotSystemSpace}.
 */
public class ParkingLotSystemSpaceImplTest {

    private ParkingLotSystemSpace instance = null;

    /**
     * Test the space construction, returns {@link ParkingLotSystemSpace}.
     */
    @Test
    public void testParkingSpaceConstruction_ReturnsInstance() {
        // Act
        instance = new ParkingLotSystemSpaceImpl(0, Mockito.mock(ParkedVehicle.class), 0);

        // Assert
        Assert.assertEquals(0, instance.getLevel());
        Assert.assertEquals(0, instance.getOffsetFromEntrance());
        Assert.assertNotNull(instance.getVehicle());
    }
}
