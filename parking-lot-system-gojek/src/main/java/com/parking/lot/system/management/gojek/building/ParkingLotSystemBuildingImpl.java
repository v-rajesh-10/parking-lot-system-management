package com.parking.lot.system.management.gojek.building;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.parking.lot.system.management.ParkingLotSystemConfigurator;
import com.parking.lot.system.management.building.ParkingLotSystemBuilding;
import com.parking.lot.system.management.exception.ParkingException;
import com.parking.lot.system.management.floor.ParkingLotSystemFloor;
import com.parking.lot.system.management.gojek.floor.ParkingLotSystemFloorImpl;
import com.parking.lot.system.management.gojek.space.ParkingLotSystemSpaceImpl;
import com.parking.lot.system.management.space.ParkingLotSystemSpace;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Implements {@link ParkingLotSystemBuilding}.
 */
public final class ParkingLotSystemBuildingImpl implements ParkingLotSystemBuilding {

    private List<ParkingLotSystemFloor> parkingLotSystemFloors = null;

    public interface ParkingLotSystemBuildingFactory
    {
        ParkingLotSystemBuilding create(final @Assisted ParkingLotSystemConfigurator configurator,
                                        final @Assisted ParkingLotSystemFloorImpl.ParkingLotSystemFloorImplFactory floorFactory,
                                        final @Assisted ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory spaceFactory);
    }

    @AssistedInject
    ParkingLotSystemBuildingImpl(final @Assisted ParkingLotSystemConfigurator configurator,
                                 final @Assisted ParkingLotSystemFloorImpl.ParkingLotSystemFloorImplFactory floorFactory,
                                 final @Assisted ParkingLotSystemSpaceImpl.ParkingLotSystemSpaceFactory spaceFactory)
    {
        parkingLotSystemFloors = new ArrayList<ParkingLotSystemFloor>(configurator.getNumberOfFloors());

        int offSetFromEntranceInFloor = 0;
        for (int currentFloorIdx = 0; currentFloorIdx < configurator.getNumberOfFloors(); currentFloorIdx++)
        {
            ParkingLotSystemFloor floor = floorFactory.create(configurator.getNumberOfSlotsPerFloor(currentFloorIdx),
                                                              currentFloorIdx,
                                                              spaceFactory,
                                                              offSetFromEntranceInFloor);

            parkingLotSystemFloors.add(floor);

            offSetFromEntranceInFloor += floor.getSpaces().size();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ParkingLotSystemFloor> getFloors() {
        return parkingLotSystemFloors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParkingLotSystemSpace getAvailableParkingSpace() throws ParkingException {

        if (getFloors().size() == 0) {
            throw new ParkingException(ParkingException.ReasonEnum.SLOT_NOT_CREATED, "No Floors in the Building");
        }

        for (ParkingLotSystemFloor currentFloor : getFloors())
        {
            try {
                return currentFloor.getAvailableSpace();
            } catch (ParkingException exception) {
                // Do Nothing...since one of the floors might be full.
            }
        }

        throw new ParkingException(ParkingException.ReasonEnum.SLOT_NOT_AVAILABLE, "No Slots Available in the Building");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParkingLotSystemSpace getParkingSpaceBySlotID(int slotId) throws ParkingException {

        if (getFloors().size() == 0) {
            throw new ParkingException(ParkingException.ReasonEnum.SLOT_NOT_CREATED, "No Floors in the Building");
        }

        for (ParkingLotSystemFloor currentFloor : getFloors())
        {
            try
            {
                return currentFloor.getSpaces().stream()
                        .filter(parkingLotSystemSpace -> parkingLotSystemSpace.getOffsetFromEntrance() == slotId)
                        .findFirst().get();
            }
            catch (NoSuchElementException exception) { /* Make sure its searched across floors*/ }
        }
        throw new ParkingException(ParkingException.ReasonEnum.SLOT_NOT_CREATED, "No value present");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStatusDisplay() {
        return getFloors().stream().map(parkingLotSystemFloor -> parkingLotSystemFloor.getStatusDisplay())
                                              .collect(Collectors.joining("\n"));
    }
}
