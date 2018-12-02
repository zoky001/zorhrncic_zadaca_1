/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.iterator;

import java.util.List;
import java.util.Optional;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.Vehicle;

/**
 *
 * @author Zoran
 */
public class Command {

    private TypeOfCommand typeOfCommand;
    private List<Vehicle> vehiclesList;
    private int value = -1;

    public Command(TypeOfCommand typeOfCommand, List<Vehicle> vehiclesList, int value) {
        this.typeOfCommand = typeOfCommand;
        this.vehiclesList = vehiclesList;
        this.value = value;
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

}
