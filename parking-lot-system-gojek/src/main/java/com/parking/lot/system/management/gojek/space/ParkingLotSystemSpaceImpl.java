package com.parking.lot.system.management.gojek.space;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.parking.lot.system.management.space.ParkingLotSystemSpace;
import com.parking.lot.system.management.vehicle.NoVehicle;
import com.parking.lot.system.management.vehicle.ParkedVehicle;

/**
 * Implements {@link ParkingLotSystemSpace}.
 */
public class ParkingLotSystemSpaceImpl implements ParkingLotSystemSpace {

    private int level = Integer.MIN_VALUE;

    private ParkedVehicle vehicle = new NoVehicle();

    private int offsetFromEntrance = Integer.MIN_VALUE;

    /**
     *
     */
    public interface ParkingLotSystemSpaceFactory
    {
        ParkingLotSystemSpace create(final @Assisted("level") int level,
                                     final @Assisted("vehicle") ParkedVehicle vehicle,
                                     final @Assisted("offset") int offsetFromEntrance);
    }

    @AssistedInject
    ParkingLotSystemSpaceImpl(final @Assisted("level") int level,
                              final @Assisted("vehicle") ParkedVehicle vehicle,
                              final @Assisted("offset") int offsetFromEntrance)
    {
        this.level = level;
        this.vehicle = vehicle;
        this.offsetFromEntrance = offsetFromEntrance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParkedVehicle getVehicle() {
        return this.vehicle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOffsetFromEntrance() {
        return this.offsetFromEntrance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void Park(ParkedVehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void Leave() {
        this.vehicle = new NoVehicle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return  getOffsetFromEntrance() + "\t\t\t" +
                getVehicle().toString();
    }
}
