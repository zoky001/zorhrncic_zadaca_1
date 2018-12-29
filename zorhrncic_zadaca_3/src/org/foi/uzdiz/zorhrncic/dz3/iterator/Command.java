/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.iterator;

import java.util.List;
import java.util.Optional;
import org.foi.uzdiz.zorhrncic.dz3.composite.CompositePlace;
import org.foi.uzdiz.zorhrncic.dz3.composite.IPlace;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.Driver;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;

/**
 *
 * @author Zoran
 */
public class Command {

    private TypeOfCommand typeOfCommand;
    private List<Vehicle> vehiclesList;
    private CompositePlace place;
    private int value = -1;
    private final List<Driver> driversList;

    public Command(TypeOfCommand typeOfCommand, List<Vehicle> vehiclesList, int value, CompositePlace place, List<Driver> driversList) {
        this.typeOfCommand = typeOfCommand;
        this.vehiclesList = vehiclesList;
        this.value = value;
        this.place = place;
        this.driversList = driversList;
    }

    public boolean hasValue() {
        return value != -1;
    }

    public TypeOfCommand getTypeOfCommand() {
        return typeOfCommand;
    }

    public List<Vehicle> getVehiclesList() {
        return vehiclesList;
    }

    public int getValue() {
        return value;
    }

    public CompositePlace getPlace() {
        return place;
    }

    public List<Driver> getDriversList() {
        return driversList;
    }

}
