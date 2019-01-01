/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz3.bridge.Tank;
import org.foi.uzdiz.zorhrncic.dz3.composite.CompositePlace;
import org.foi.uzdiz.zorhrncic.dz3.composite.IPlace;
import org.foi.uzdiz.zorhrncic.dz3.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfVehicleEngine;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz3.composite.Street;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.BolovanjeState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.Driver;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.GodisnjiState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.IDriverState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.NedodjeljenState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.OtkazState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.RaspolozivoState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.ZauzetoState;
import org.foi.uzdiz.zorhrncic.dz3.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.decorator.IVehicleEquipment;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.ControlState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.CrashState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.GasStationState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.IVehicleState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.LandfillState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.ParkingState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.ReadyState;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.TypeOfVehicleState;
import org.foi.uzdiz.zorhrncic.dz3.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypeOfDriverState;

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
    private int numberOfPerformedCycles = 0;
    private int maxNumberOfCyclesTillTheFilling = 0;
    private int numberOfCyclesAtGasStation = 0;
    private int minNumberOfCyclesAtGasStation = 0;

    private List<Driver> drivers;
    private ArrayList<Integer> randomStreetArray;
    private int lastStreet;
    private int numberOfCyclesAtLandfill;
    private int numberOfProcessedContainers;
    private List<Spremnik> spremnikList;// = new ArrayList<Spremnik>();
    private float total;
    private int numberOfDepartures;

    private CompositePlace area;

    private IVehicleState parking;
    private IVehicleState ready;
    private IVehicleState crash;
    private IVehicleState landfill;
    private IVehicleState control;
    private IVehicleState gas_station;
    private IVehicleState state;

    public Vehicle() {
        //  filled = 0;
        // capacity = 0;
        numberOfCyclesAtLandfill = 0;
        lastStreet = -1;
        numberOfProcessedContainers = 0;
        spremnikList = new ArrayList<Spremnik>();
        total = 0;
        numberOfDepartures = 0;

        this.parking = new ParkingState();
        this.ready = new ReadyState();
        this.crash = new CrashState();
        this.landfill = new LandfillState();
        this.control = new ControlState();
        this.gas_station = new GasStationState();

        this.state = this.parking;
    }

    public void increaseNumberOfPerformedCycles() {
        try {
            numberOfPerformedCycles++;
            if (numberOfPerformedCycles >= maxNumberOfCyclesTillTheFilling) {
                this.gasStation();
            }
        } catch (Exception e) {
        }
    }

    public void gasStation() {
        try {
            this.state.gasStation(this);
            numberOfPerformedCycles = 0;
        } catch (Exception e) {
        }
    }

    public boolean checkIsFilledAtGasStation() {
        if (getState() == TypeOfVehicleState.GAS_STATION) {
            try {
                numberOfCyclesAtGasStation++;
                if (numberOfCyclesAtGasStation >= minNumberOfCyclesAtGasStation) {
                    this.prepareVehicle();
                    numberOfCyclesAtGasStation = 0;
                    changeDriver();
                    return false;
                }
                return true;
            } catch (Exception e) {
            }
        }

        return false;

    }

    private void changeDriver() {
        try {
            for (Driver driver : drivers) {
                if (driver.getState() == TypeOfDriverState.RASPOLOZIV) {
                    driver.zauzmiVozilo(this);
                }
            }
            Collections.shuffle(drivers);
        } catch (Exception e) {
        }
    }

    public void prepareVehicle() {
        this.state.prepareVehicle(this);
    }

    public void crashVehicle() {
        this.state.crashVehicle(this);
    }

    public void goToParking() {
        this.state.goToParking(this);
    }

    public void goToLandFill() {
        try {
            if (getState() == TypeOfVehicleState.CRASH) {
                this.state.goToParking(this);
            } else {
                this.state.goToLandFill(this);
            }
        } catch (Exception e) {
        }
    }

    public void goToControll() {
        this.state.goToControll(this);
    }

    public ArrayList<Street> getStreets() {
        return loadStreetsFromArea(area);
    }

    private ArrayList<Street> loadStreetsFromArea(CompositePlace area) {
        ArrayList<Street> streets = new ArrayList<Street>();
        if (area instanceof CompositePlace && area.getmChildPlaces().size() == 0) {
            return streets;
        }

        for (IPlace subArea : area.getmChildPlaces()) {
            if (subArea instanceof Street) {
                streets.add((Street) subArea);
            } else if (subArea instanceof CompositePlace) {
                streets.addAll(loadStreetsFromArea((CompositePlace) subArea));
            }
        }
        return streets;
    }

    public ArrayList<Spremnik> getOdgovarajuceSpremnike() {
        ArrayList<Spremnik> spremnikList = new ArrayList<Spremnik>();

        try {
            for (Spremnik spremnik : loadSpremnikeFromArea(area)) {
                if (spremnik.getKindOfWaste() == getTypeOfWaste(this)) {
                    spremnikList.add(spremnik);
                }
            }
        } catch (Exception e) {
        }

        return spremnikList;
    }

    private ArrayList<Spremnik> loadSpremnikeFromArea(CompositePlace area) {
        ArrayList<Spremnik> spremnikList = new ArrayList<Spremnik>();

        try {

            for (IPlace subArea : loadStreetsFromArea(area)) {
                if (subArea instanceof Street) {
                    spremnikList.addAll(((Street) subArea).getSpremnikList());
                }
            }

            return spremnikList;
        } catch (Exception e) {
            //

        }
        return spremnikList;

    }

    private TypesOfWaste getTypeOfWaste(Vehicle vehicle) {
        TypesOfWaste typesOfWaste = null;
        if (vehicle instanceof VehicleBio) {
            typesOfWaste = TypesOfWaste.BIO;
        } else if (vehicle instanceof VehicleGlass) {
            typesOfWaste = TypesOfWaste.STAKLO;
        } else if (vehicle instanceof VehicleMetal) {
            typesOfWaste = TypesOfWaste.METAL;
        } else if (vehicle instanceof VehicleMixed) {
            typesOfWaste = TypesOfWaste.MJESANO;
        } else if (vehicle instanceof VehiclePaper) {
            typesOfWaste = TypesOfWaste.PAPIR;
        }
        return typesOfWaste;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public TypesOfVehicleEngine getTypesOfVehicleEngine() {
        return typesOfVehicleEngine;
    }

    public void setTypesOfVehicleEngine(TypesOfVehicleEngine typesOfVehicleEngine) {
        this.typesOfVehicleEngine = typesOfVehicleEngine;

        try {
            if (typesOfVehicleEngine == TypesOfVehicleEngine.DIESEL) {
                this.maxNumberOfCyclesTillTheFilling = Integer.parseInt((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.kapacitetDizelVozila));
                this.minNumberOfCyclesAtGasStation = Integer.parseInt((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.punjenjeDizelVozila));
            } else {
                this.maxNumberOfCyclesTillTheFilling = Integer.parseInt((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.kapacitetElektroVozila));
                this.minNumberOfCyclesAtGasStation = Integer.parseInt((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.punjenjeElektroVozila));

            }
        } catch (Exception e) {
        }
    }

    public float getCapacity() {
        return this.tank.getCapacity();
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

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public CompositePlace getArea() {
        return area;
    }

    public void setArea(CompositePlace area) {
        this.area = area;
    }

    public IVehicleState getParking() {
        return parking;
    }

    public void setParking(IVehicleState parking) {
        this.parking = parking;
    }

    public IVehicleState getReady() {
        return ready;
    }

    public void setReady(IVehicleState ready) {
        this.ready = ready;
    }

    public IVehicleState getCrash() {
        return crash;
    }

    public void setCrash(IVehicleState crash) {
        this.crash = crash;
    }

    public IVehicleState getLandfill() {
        return landfill;
    }

    public void setLandfill(IVehicleState landfill) {
        this.landfill = landfill;
    }

    public IVehicleState getControl() {
        return control;
    }

    public void setControl(IVehicleState control) {
        this.control = control;
    }

    public IVehicleState getGas_station() {
        return gas_station;
    }

    public void setGas_station(IVehicleState gas_station) {
        this.gas_station = gas_station;
    }

    public TypeOfVehicleState getState() {
        return state.getState();
    }

    public void setState(IVehicleState state) {
        this.state = state;
    }

}
