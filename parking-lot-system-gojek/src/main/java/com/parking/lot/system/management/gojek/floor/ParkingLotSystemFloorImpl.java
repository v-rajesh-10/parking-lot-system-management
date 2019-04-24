package com.parking.lot.system.management.gojek.floor;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.parking.lot.system.management.exception.ParkingException;
import com.parking.lot.system.management.floor.ParkingLotSystemFloor;
import com.parking.lot.system.management.gojek.space.ParkingLotSystemSpaceImpl;
import com.parking.lot.system.management.gojek.vehicle.Vehicle;
import com.parking.lot.system.management.space.ParkingLotSystemSpace;
import com.parking.lot.system.management.vehicle.NoVehicle;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implements {@link ParkingLotSystemFloor}.
 */
public final class ParkingLotSystemFloorImpl implements ParkingLotSystemFloor {

    private int numberOfSlotsPerFloor = 0;

    private int level = 0;

    private List<ParkingLotSystemSpace> parkingLotSystemSpaces = new ArrayList<>();

    public interface ParkingLotSystemFloorImplFactory
    {
        ParkingLotSystemFloor create(final @Assisted("numberOfSlotsPerFloor") int numberOfSlotsPerFloor,
                                     final @Assisted("level") int level,
                                     final @Assisted("spaceFactory") ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory spaceFactory,
                                     final @Assisted("offSetFromEntranceInFloor") int offSetFromEntranceInFloor);
    }

    @AssistedInject
    ParkingLotSystemFloorImpl(final @Assisted("numberOfSlotsPerFloor") int numberOfSlotsPerFloor,
                              final @Assisted("level") int level,
                              final @Assisted("spaceFactory") ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory spaceFactory,
                              final @Assisted("offSetFromEntranceInFloor") int offSetFromEntranceInFloor)
    {
        this.numberOfSlotsPerFloor = numberOfSlotsPerFloor;
        this.level = level;

        for(int currentSlot = 1; currentSlot <= numberOfSlotsPerFloor; currentSlot++)
        {
            int currentOffsetFromEntrance = offSetFromEntranceInFloor + currentSlot;
            ParkingLotSystemSpace parkingLotSystemSpace = spaceFactory.create(getLevel(), new NoVehicle(), currentOffsetFromEntrance);

            parkingLotSystemSpaces.add(parkingLotSystemSpace);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ParkingLotSystemSpace> getSpaces() {
        return parkingLotSystemSpaces;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getSlotsCapacity() {
        return numberOfSlotsPerFloor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getLevel() {
        return level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParkingLotSystemSpace getAvailableSpace() throws ParkingException {
        if (getSpaces().size() == 0)
        {
            throw new ParkingException(ParkingException.ReasonEnum.SLOT_NOT_CREATED);
        }

        try
        {
            return getSpaces().stream().filter(space -> space.getVehicle() instanceof NoVehicle)
                    .findFirst().get();
        }
        catch (NoSuchElementException exception)
        {
            throw new ParkingException(ParkingException.ReasonEnum.SLOT_NOT_AVAILABLE, exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getStatusDisplay() {
        return getSpaces().stream().filter(parkingLotSystemSpace -> parkingLotSystemSpace.getVehicle() instanceof Vehicle).map(parkingLotSystemSpace -> parkingLotSystemSpace.toString())
                .map(parkingLotSystemSpace -> parkingLotSystemSpace.toString()).collect(Collectors.joining("\n"));
    }
}
