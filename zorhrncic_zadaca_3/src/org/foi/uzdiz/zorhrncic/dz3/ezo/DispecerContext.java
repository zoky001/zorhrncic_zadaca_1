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
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.TypeOfVehicleState;

/**
 *
 * @author Zoran
 */
public class DispecerContext {

    private List<Vehicle> allVehicles;// = new ArrayList<>();
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

        this.landfill = new Landfill(this);

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
        List<Vehicle> allVehiclesInProcess = new ArrayList<>();
        try {
            for (Vehicle vehicle : allVehicles) {
                if (vehicle.getState() == TypeOfVehicleState.PARKING) {
                    allVehiclesInProcess.add(vehicle);
                }
            }
        } catch (Exception e) {
        }

        return allVehiclesInProcess;
    }

    public List<Vehicle> getAllVehiclesInProcess() {

        List<Vehicle> allVehiclesInProcess = new ArrayList<>();
        try {
            for (Vehicle vehicle : allVehicles) {
                if (vehicle.getState() == TypeOfVehicleState.READY) {
                    allVehiclesInProcess.add(vehicle);
                }
            }
        } catch (Exception e) {
        }

        return allVehiclesInProcess;
    }

    public List<Vehicle> getAllVehiclesInMalfunction() {
        List<Vehicle> allVehiclesInProcess = new ArrayList<>();
        try {
            for (Vehicle vehicle : allVehicles) {
                if (vehicle.getState() == TypeOfVehicleState.CRASH) {
                    allVehiclesInProcess.add(vehicle);
                }
            }
        } catch (Exception e) {
        }

        return allVehiclesInProcess;
    }

    public List<Vehicle> getAllVehiclesAtLandfill() {
        List<Vehicle> allVehiclesAtLandfill = new ArrayList<>();
        try {
            for (Vehicle vehicle : allVehicles) {
                if (vehicle.getState() == TypeOfVehicleState.LANDFILL) {
                    allVehiclesAtLandfill.add(vehicle);
                }
            }
        } catch (Exception e) {
        }

        return allVehiclesAtLandfill;
    }
    
    public List<Vehicle> getAllVehiclesAtGasStation() {
        List<Vehicle> allVehiclesAtLandfill = new ArrayList<>();
        try {
            for (Vehicle vehicle : allVehicles) {
                if (vehicle.getState() == TypeOfVehicleState.GAS_STATION) {
                    allVehiclesAtLandfill.add(vehicle);
                }
            }
        } catch (Exception e) {
        }

        return allVehiclesAtLandfill;
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
        List<Vehicle> allVehiclesInProcess = new ArrayList<>();
        try {
            for (Vehicle vehicle : allVehicles) {
                if (vehicle.getState() == TypeOfVehicleState.CONTROL) {
                    allVehiclesInProcess.add(vehicle);
                }
            }
        } catch (Exception e) {
        }

        return allVehiclesInProcess;
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
