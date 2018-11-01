/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfVehicleEngine;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz1.streets.Street;

/**
 *
 * @author Zoran
 */
public abstract class Vehicle {

    private String name;
    private TypesOfVehicleEngine typesOfVehicleEngine;
    private float capacity;
    private List<String> drivers;

    private ArrayList<Integer> randomStreetArray;
    private float filled;

    private int lastStreet;

    private int numberOfCyclesAtLandfill;

    public Vehicle() {
        filled = 0;
        capacity = 0;
        numberOfCyclesAtLandfill = 0;
        lastStreet = -1;
    }

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

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public List<String> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<String> drivers) {
        this.drivers = drivers;
    }

    public ArrayList<Integer> getRandomStreetArray() {
        return randomStreetArray;
    }

    public void setRandomStreetArray(ArrayList<Integer> randomStreetArray) {
        this.randomStreetArray = randomStreetArray;
    }

    public float getFilled() {
        return filled;
    }

    public void setFilled(float filled) {
        this.filled = filled;
    }

    public void addWaste(float filled) {
        this.filled = this.filled + filled;
    }

    public int getNumberOfCyclesAtLandfill() {
        return numberOfCyclesAtLandfill;
    }

    public void setNumberOfCyclesAtLandfill(int numberOfCyclesAtLandfill) {
        this.numberOfCyclesAtLandfill = numberOfCyclesAtLandfill;
    }

    public void resetNumberOfCyclesAtLandfill() {
        this.numberOfCyclesAtLandfill = 0;
        this.filled = 0;
    }

    public boolean increaseAndCheckNumberOfCyclesAtLandfill(int needs) {
        this.numberOfCyclesAtLandfill = this.numberOfCyclesAtLandfill + 1;
        if (this.numberOfCyclesAtLandfill > needs) {
            return true;
        }
        return false;
    }

    public int getLastStreet() {
        return lastStreet;
    }

    public void setLastStreet(int lastStreet) {
        this.lastStreet = lastStreet;
    }

    public void resetLastStreet() {
        this.lastStreet = -1;
    }

}
