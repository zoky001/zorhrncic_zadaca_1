/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz3.composite.Street;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;

/**
 *
 * @author Zoran
 */
public abstract class ChoosePickupDirection {

    private Vehicle ascendingVehicle;
    private int ascendingVehicleLastLocation;

    private Vehicle descendingVehicle;
    private int descendingVehicleLastLocation;

    private List<Vehicle> waitingWehicleList;

    protected abstract boolean isExistAscendingVehicle();

    protected abstract boolean isExistDescendingVehicle();

    protected abstract boolean isThisVehicleInStreet(Vehicle vehicle);

    protected abstract PickUpDirection getVehicleDirection(Vehicle vehicle);

    protected abstract boolean deleteFromWaitingList(Vehicle vehicle);

    public ChoosePickupDirection() {
        this.waitingWehicleList = new ArrayList<>();
    }


    /* A template method : */
    public final PickUpDirection chooseDirection(Vehicle vehicle, Street street) {
        try {
            if (isThisVehicleInStreet(vehicle)) {
                return getVehicleDirection(vehicle);
            }

            if (!isExistAscendingVehicle() && !isExistDescendingVehicle()) {
                deleteFromWaitingList(vehicle);
                this.ascendingVehicle = vehicle;
                this.ascendingVehicleLastLocation = 0;
                return PickUpDirection.ASCENDING;
            } else if (isExistAscendingVehicle() && !isExistDescendingVehicle()) {
                deleteFromWaitingList(vehicle);
                this.descendingVehicle = vehicle;
                this.descendingVehicleLastLocation = street.getSpremnikList().size() - 1;
                return PickUpDirection.DESCENDING;
            } else {
                this.waitingWehicleList.add(vehicle);
                return PickUpDirection.WAIT;

            }
        } catch (Exception e) {
            return null;
        }
    }

    /* A template method : */
    public final int getVehicleLocation(Vehicle vehicle) {
        try {
            if (isThisVehicleInStreet(vehicle)) {
                if (ascendingVehicle == vehicle) {
                    return ascendingVehicleLastLocation;
                } else {
                    return descendingVehicleLastLocation;
                }
            }
            return -1;

        } catch (Exception e) {
            return -1;
        }
    }

    /* A template method : */
    public final void vehicleLeaveTheStreet(Vehicle vehicle) {
        try {
            if (isThisVehicleInStreet(vehicle)) {
                if (ascendingVehicle == vehicle) {
                    ascendingVehicle = null;
                    ascendingVehicleLastLocation = 0;
                } else {
                    descendingVehicle = null;
                    descendingVehicleLastLocation = 0;
                }
            }

        } catch (Exception e) {
            //
        }
    }

    /* A template method : */
    public final int setLastVehicleLocation(Vehicle vehicle, int i) {
        try {
            if (isThisVehicleInStreet(vehicle)) {
                if (ascendingVehicle == vehicle) {
                    ascendingVehicleLastLocation = i;
                    return ascendingVehicleLastLocation;
                } else {
                    descendingVehicleLastLocation = i;
                    return descendingVehicleLastLocation;
                }
            }
            return -1;

        } catch (Exception e) {
            return -1;
        }
    }

    public Vehicle getAscendingVehicle() {
        return ascendingVehicle;
    }

    public int getAscendingVehicleLastLocation() {
        return ascendingVehicleLastLocation;
    }

    public Vehicle getDescendingVehicle() {
        return descendingVehicle;
    }

    public int getDescendingVehicleLastLocation() {
        return descendingVehicleLastLocation;
    }

    public List<Vehicle> getWaitingWehicleList() {
        return waitingWehicleList;
    }
}
