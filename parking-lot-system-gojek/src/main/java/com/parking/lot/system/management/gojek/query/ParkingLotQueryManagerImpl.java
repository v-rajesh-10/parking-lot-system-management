package com.parking.lot.system.management.gojek.query;

import com.parking.lot.system.management.ParkingLotSystemManager;
import com.parking.lot.system.management.space.ParkingLotSystemSpace;
import com.parking.lot.system.management.vehicle.ParkedVehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements {@link ParkingLotSystemManager.Query}.
 */
public class ParkingLotQueryManagerImpl implements ParkingLotSystemManager.Query {

    private Map<String, ParkedVehicle> vehiclesMap = new HashMap<>();

    private Map<Integer, ParkedVehicle> slotVehicleMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> FindRegistrationNumberForVehicleWithColor(String color) {
        List<String> registeredNumbersList = new ArrayList<>();
        for(Map.Entry<String, ParkedVehicle> entry : vehiclesMap.entrySet()) {
            ParkedVehicle vehicle = entry.getValue();
            if (vehicle.getColor().equalsIgnoreCase(color)) {
                registeredNumbersList.add(vehicle.getRegisteredNumber());
            }
        }
        return registeredNumbersList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> FindParkingLotSlotNumbersForVehicleWithColor(String color) {
        List<Integer> slotNumberList = new ArrayList<>();
        for(Map.Entry<Integer, ParkedVehicle> entry : slotVehicleMap.entrySet()) {
            ParkedVehicle vehicle = entry.getValue();
            if (vehicle.getColor().equalsIgnoreCase(color)) {
                slotNumberList.add(entry.getKey());
            }
        }
        return slotNumberList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer FindSlotNumberByVehicleRegistrationNumber(String registrationNumber) {
        for(Map.Entry<Integer, ParkedVehicle> entry : slotVehicleMap.entrySet()) {
            ParkedVehicle vehicle = entry.getValue();
            if (vehicle.getRegisteredNumber().equalsIgnoreCase(registrationNumber)) {
                return entry.getKey();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * Add Vehicle Information to the internal DS.
     * @param parkingLotSystemSpace The added {@link ParkingLotSystemSpace} instance.
     */
    public void AddVehicleInformation(ParkingLotSystemSpace parkingLotSystemSpace)
    {
        ParkedVehicle vehicle = parkingLotSystemSpace.getVehicle();
        vehiclesMap.put(vehicle.getRegisteredNumber(), vehicle);

        slotVehicleMap.put(parkingLotSystemSpace.getOffsetFromEntrance(), vehicle);
    }

    /**
     * Remove Vehicle Information to the internal DS.
     * @param slotId The slot number of the parking space.
     */
    public void RemoveVehicleInformation(int slotId)
    {
        vehiclesMap.remove(slotVehicleMap.get(slotId).getRegisteredNumber());

        slotVehicleMap.remove(slotId);
    }
}
