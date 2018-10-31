/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle;

import java.util.List;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfVehicleEngine;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfWaste;

/**
 *
 * @author Zoran
 */
public abstract class Vehicle {

    private String name;
    private TypesOfVehicleEngine typesOfVehicleEngine;
    private int capacity;
    private List<String> drivers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypesOfVehicleEngine getTypesOfVehicleEngine() {
        return typesOfVehicleEngine;
    }

    public void setTypesOfVehicleEngine(TypesOfVehicleEngine typesOfVehicleEngine) {
        this.typesOfVehicleEngine = typesOfVehicleEngine;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<String> drivers) {
        this.drivers = drivers;
    }
    
}
