/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz3.composite.CompositePlace;
import org.foi.uzdiz.zorhrncic.dz3.composite.Street;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.Driver;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;

/**
 *
 * @author Zoran
 */
public class DispecerContext {

    private List<Vehicle> allVehicles;// = new ArrayList<>();
    private List<Vehicle> allVehiclesAtParking;// = new ArrayList<>();
    private List<Vehicle> allVehiclesInProcess;// = new ArrayList<>();
    private List<Vehicle> allVehiclesInMalfunction;/// = new ArrayList<>();
    private List<Vehicle> allVehiclesAtLandfill;// = new ArrayList<>();
    private List<Vehicle> allVehiclesOnControll;// = new ArrayList<>();
    private List<Street> streets;
    private boolean isVehicleSelectsStreet = false;
    private boolean isAllWasteCollected = false;
    private Landfill landfill;
    private Vehicle vehicleInProcess;
    private int cycleNumber = 1;
    private int numberOfCyclesAtLandfill;
    private int selectedStreetIndex;
    private Street selectedStreet;
    private ArrayList<Integer> randomStreetArray;
    private final List<CompositePlace> areaRootElement;
    private final List<Driver> driversList;

    public DispecerContext(List<Vehicle> allVehicles, List<Street> allStreets, List<CompositePlace> areaRootElement) {
        this.allVehicles = new ArrayList<Vehicle>(allVehicles);
        this.allVehiclesAtParking = allVehicles;
        this.allVehiclesInProcess = new ArrayList<Vehicle>();
        this.allVehiclesInMalfunction = new ArrayList<Vehicle>();
        this.allVehiclesAtLandfill = new ArrayList<Vehicle>();
        this.allVehiclesOnControll = new ArrayList<Vehicle>();
        this.landfill = new Landfill();

        this.streets = allStreets;
        this.areaRootElement = areaRootElement;
        this.driversList = getAllDriversFromVehicles(allVehicles);
    }

    public List<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    public void setAllVehicles(List<Vehicle> allVehicles) {
        this.allVehicles = allVehicles;
    }

    public List<Vehicle> getAllVehiclesAtParking() {
        return allVehiclesAtParking;
    }

    public void setAllVehiclesAtParking(List<Vehicle> allVehiclesAtParking) {
        this.allVehiclesAtParking = allVehiclesAtParking;
    }

    public List<Vehicle> getAllVehiclesInProcess() {
        return allVehiclesInProcess;
    }

    public void setAllVehiclesInProcess(List<Vehicle> allVehiclesInProcess) {
        this.allVehiclesInProcess = allVehiclesInProcess;
    }

    public List<Vehicle> getAllVehiclesInMalfunction() {
        return allVehiclesInMalfunction;
    }

    public void setAllVehiclesInMalfunction(List<Vehicle> allVehiclesInMalfunction) {
        this.allVehiclesInMalfunction = allVehiclesInMalfunction;
    }

    public List<Vehicle> getAllVehiclesAtLandfill() {
        this.allVehiclesAtLandfill = landfill.getAllVehiclesAtLandfill();
        return allVehiclesAtLandfill;
    }

    public void setAllVehiclesAtLandfill(List<Vehicle> allVehiclesAtLandfill) {
        this.allVehiclesAtLandfill = allVehiclesAtLandfill;
    }

    public boolean isIsVehicleSelectsStreet() {
        return isVehicleSelectsStreet;
    }

    public void setIsVehicleSelectsStreet(boolean isVehicleSelectsStreet) {
        this.isVehicleSelectsStreet = isVehicleSelectsStreet;
    }

    public boolean isIsAllWasteCollected() {
        return isAllWasteCollected;
    }

    public void setIsAllWasteCollected(boolean isAllWasteCollected) {
        this.isAllWasteCollected = isAllWasteCollected;
    }

    public Landfill getLandfill() {
        return landfill;
    }

    public void setLandfill(Landfill isAllWasteCollected) {
        this.landfill = isAllWasteCollected;
    }

    public Vehicle getVehicleInProcess() {
        return vehicleInProcess;
    }

    public void setVehicleInProcess(Vehicle vehicleInProcess) {
        this.vehicleInProcess = vehicleInProcess;
    }

    public int getCycleNumber() {
        return cycleNumber;
    }

    public void setCycleNumber(int cycleNumber) {
        this.cycleNumber = cycleNumber;
    }

    public int getNumberOfCyclesAtLandfill() {
        return numberOfCyclesAtLandfill;
    }

    public void setNumberOfCyclesAtLandfill(int numberOfCyclesAtLandfill) {
        this.numberOfCyclesAtLandfill = numberOfCyclesAtLandfill;
    }

    public int getSelectedStreetIndex() {
        return selectedStreetIndex;
    }

    public void setSelectedStreetIndex(int selectedStreetIndex) {
        this.selectedStreetIndex = selectedStreetIndex;
    }

    public Street getSelectedStreet() {
        return selectedStreet;
    }

    public void setSelectedStreet(Street selectedStreet) {
        this.selectedStreet = selectedStreet;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public ArrayList<Integer> getRandomStreetArray() {
        return randomStreetArray;
    }

    public void setRandomStreetArray(ArrayList<Integer> randomStreetArray) {
        this.randomStreetArray = randomStreetArray;
    }

    public List<Vehicle> getAllVehiclesOnControll() {
        return allVehiclesOnControll;
    }

    public List<CompositePlace> getAreaRootElement() {
        return areaRootElement;
    }

    public List<Driver> getDriversList() {
        return driversList;
    }

    private List<Driver> getAllDriversFromVehicles(List<Vehicle> allVehicles) {
        List<Driver> driverList = new ArrayList<>();

        try {
            for (Vehicle allVehicle : allVehicles) {
                driverList.addAll(allVehicle.getDrivers());
            }
        } catch (Exception e) {
//
        }

        return driverList;
    }
}
