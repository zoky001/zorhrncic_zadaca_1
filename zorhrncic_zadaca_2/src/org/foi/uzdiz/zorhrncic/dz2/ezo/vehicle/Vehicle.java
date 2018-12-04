/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz2.bridge.Tank;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfVehicleEngine;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz2.composite.Street;
import org.foi.uzdiz.zorhrncic.dz2.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz2.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.decorator.IVehicleEquipment;

/**
 *
 * @author Zoran
 */
public abstract class Vehicle implements IVehicleEquipment {

    private IVehicleEquipment vehicleEquipment;
    protected final ReportBuilderDirector builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();

    private Tank tank;

    private String name;
    private String id;
    private TypesOfVehicleEngine typesOfVehicleEngine;
    private List<String> drivers;
    private ArrayList<Integer> randomStreetArray;
    private int lastStreet;
    private int numberOfCyclesAtLandfill;
    private int numberOfProcessedContainers;
    private List<Spremnik> spremnikList;// = new ArrayList<Spremnik>();
    private float total;
    private int numberOfDepartures;

    public Vehicle() {
        //  filled = 0;
        // capacity = 0;
        numberOfCyclesAtLandfill = 0;
        lastStreet = -1;
        numberOfProcessedContainers = 0;
        spremnikList = new ArrayList<Spremnik>();
        total = 0;
        numberOfDepartures = 0;
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
        return this.tank.getCapacity();
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
        // return filled;
        return this.tank.getFilled();
    }

    public void emptyVehicle() {
        // this.filled = filled;
        this.total = this.total + this.tank.getFilled();
        this.numberOfDepartures = this.numberOfDepartures + 1;
        this.tank.emptyToTheEnd();
    }

    public void addWaste(float filled) {
        //  this.filled = this.filled + filled;

        this.tank.fill(filled);
    }

    public int getNumberOfCyclesAtLandfill() {
        return numberOfCyclesAtLandfill;
    }

    public void setNumberOfCyclesAtLandfill(int numberOfCyclesAtLandfill) {
        this.numberOfCyclesAtLandfill = numberOfCyclesAtLandfill;
    }

    public void resetNumberOfCyclesAtLandfill() {
        this.numberOfCyclesAtLandfill = 0;
        // this.filled = 0;
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

    public int getNumberOfProcessedContainers() {
        return numberOfProcessedContainers;
    }

    public void increaseNumberOfProcessedContainers() {
        this.numberOfProcessedContainers = this.numberOfProcessedContainers + 1;
    }

    public void addProcessedContainers(Spremnik spremnik) {
        this.spremnikList.add(spremnik);
    }

    public List<Spremnik> getSpremnikList() {
        return spremnikList;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public float getTotal() {
        return total;
    }

    public int getNumberOfDepartures() {
        return numberOfDepartures;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IVehicleEquipment getVehicleEquipment() {
        return vehicleEquipment;
    }

    public void setVehicleEquipment(IVehicleEquipment vehicleEquipment) {
        this.vehicleEquipment = vehicleEquipment;
    }

}
