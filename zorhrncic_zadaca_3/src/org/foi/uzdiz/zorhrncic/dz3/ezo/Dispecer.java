/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.parser.TokenType;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorPripremi;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutor;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorBolovanje;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorGodisnjiOdmor;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorIsprazni;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorIzlaz;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorKontrola;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorKreniWithParameters;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorKreniWithoutParameters;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorKvar;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorNovi;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorObradi;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorOtkaz;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorPreuzmi;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorStatus;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorVozaci;
import org.foi.uzdiz.zorhrncic.dz3.composite.CompositePlace;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz3.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz3.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz3.composite.Street;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.Driver;
import org.foi.uzdiz.zorhrncic.dz3.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz3.iterator.CommandRepository;
import org.foi.uzdiz.zorhrncic.dz3.iterator.TypeOfCommand;
import org.foi.uzdiz.zorhrncic.dz3.waste.BioWaste;
import org.foi.uzdiz.zorhrncic.dz3.iterator.IIterator;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypeOfDriverState;

/**
 *
 * @author Zoran
 */
public class Dispecer {

    DispecerContext dispecerContext;

    private List<Vehicle> allVehiclesInProcess;
    ///private List<Vehicle> allVehiclesAtLandfill;
    private List<Street> streets;
    private Vehicle vehicleInProcess;
    private int cycleNumber = 0;
    private boolean isAllWasteCollected = false;
    private boolean isVehicleSelectsStreet = false;
    private ArrayList<Integer> randomStreetArray;
    private int numberOfCyclesAtLandfill;
    private Street selectedStreet;
    private int selectedStreetIndex;
    private final ReportBuilderDirector builderDirector;
    private final CommandRepository commandRepository;
    private final CommandExecutor commandExecutor;

    public Dispecer(List<Vehicle> allVehicles, List<Street> streets, List<CompositePlace> areaRoot) {
        this.dispecerContext = new DispecerContext(allVehicles, streets, areaRoot);

        this.commandRepository = new CommandRepository(this.dispecerContext);

        this.commandExecutor = new CommandExecutorPripremi()
                .setNext(new CommandExecutorKreniWithParameters()
                        .setNext(new CommandExecutorBolovanje()
                                .setNext(new CommandExecutorOtkaz()
                                        .setNext(new CommandExecutorGodisnjiOdmor()
                                                .setNext(new CommandExecutorIzlaz()
                                                        .setNext(new CommandExecutorNovi()
                                                                .setNext(new CommandExecutorObradi()
                                                                        .setNext(new CommandExecutorPreuzmi()
                                                                                .setNext(new CommandExecutorVozaci()
                                                                                        .setNext(new CommandExecutorKvar()
                                                                                                .setNext(new CommandExecutorStatus()
                                                                                                        .setNext(new CommandExecutorIsprazni()
                                                                                                                .setNext(new CommandExecutorKreniWithoutParameters()
                                                                                                                        .setNext(new CommandExecutorKontrola()))))))))))))));

        this.allVehiclesInProcess = allVehicles;
        this.streets = streets;

        builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();
        try {
            this.numberOfCyclesAtLandfill = Integer.parseInt((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.brojRadnihCiklusaZaOdvoz));

        } catch (Exception e) {
            builderDirector.addErrorInReport("Greška prilikom učitavanja broja radnih ciklusa za odvoz!", false);
            System.exit(0);
        }
    }

    public void startCollecting() {

        for (IIterator iterator = commandRepository.getIterator(); iterator.hasNext();) {
            try {
                Command command = iterator.next();
                //System.out.println(command.getTypeOfCommand());
                dispecerContext = commandExecutor.executeCommand(command, dispecerContext);
                assignFreeDriversToVehicles();
            } catch (Exception ex) {
                Logger.getLogger(Dispecer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void assignFreeDriversToVehicles() {
        try {
            for (Vehicle vehicle : dispecerContext.getAllVehicles()) {
                if (!isExistMainDriver(vehicle)) {
                    Driver d = findFreeDriver();
                    if (d != null) {
                        d.zauzmiVozilo(vehicle);
                    } else {
                        builderDirector.addTextLineInReport("DISPEČER: Nema nedodjenjenih vozača za vozilo " + vehicle.getName() + "!", true);

                    }
                }

            }
        } catch (Exception e) {
        }
    }

    private Driver findFreeDriver() {
        try {
            for (Driver driver : dispecerContext.getDriversList()) {
                if (driver.getState() == TypeOfDriverState.NEDODJELJEN) {
                    return driver;
                }
            }

        } catch (Exception e) {

        }
        return null;
    }

    private boolean isExistMainDriver(Vehicle vehicle) {
        try {
            for (Driver driver : vehicle.getDrivers()) {
                if (driver.getState() == TypeOfDriverState.VOZI_KAMION) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

//    private void driveAllVehiclesToTheLandfill() {
//        try {
//            for (int i = 0; i < allVehiclesInProcess.size(); i++) {
//                if (!landfill.getAllVehiclesAtLandfill().contains(allVehiclesInProcess.get(i))) {
//                    //landfill.vehicleComesToLandfill(allVehiclesInProcess.get(i));
//                    driveToLandfill(allVehiclesInProcess.get(i));
//                }
//            }
//
//        } catch (Exception e) {
//
//            builderDirector.addErrorInReport("driveAllVehiclesToTheLandfill error " + e, false);
//
//        }
//
//    }
//
//    private boolean checkIsVehicleAtLandfill(Vehicle vehicle) {
//        if (landfill.getAllVehiclesAtLandfill().contains(vehicle)) {
//
//            if (vehicle.increaseAndCheckNumberOfCyclesAtLandfill(numberOfCyclesAtLandfill)) {
//                landfill.vehicleLeavesLandfill(vehicle);//getAllVehiclesAtLandfill().remove(vehicle);
//                int index = allVehiclesInProcess.indexOf(vehicle);
//                allVehiclesInProcess.remove(vehicle);
//                allVehiclesInProcess.add(vehicle);
//
//                return false;
//            } else {
//                return true;
//            }
//
//        }
//
//        return false;
//    }
//    private void pickUpWaste(Vehicle vehicle) {
//        ArrayList<Integer> streetArray;//= getStreetRandomArray();
//
//        if (vehicle.getRandomStreetArray() == null) {
//            vehicle.setRandomStreetArray(getStreetRandomArray());
//            streetArray = vehicle.getRandomStreetArray();
//        } else {
//            streetArray = vehicle.getRandomStreetArray();
//        }
//
//        for (int i = 0; i < streetArray.size(); i++) {
//            selectedStreetIndex = i;
//            selectedStreet = streets.get(streetArray.get(i));
//            if (vehicle.getLastStreet() > -1 && vehicle.getLastStreet() > i) {
//                continue;
//            } else if (vehicle.getLastStreet() > -1 && vehicle.getLastStreet() == i) {
//                vehicle.resetLastStreet();
//            }
//            if (pickUpWasteInStreet(vehicle, selectedStreet)) {
//                return;
//            }
//
//        }
//
//    }
//
//    private boolean pickUpWasteInStreet(Vehicle vehicle, Street street) {
//        TypesOfWaste typesOfWaste = null;
//
//        if (vehicle instanceof VehicleBio) {
//            typesOfWaste = TypesOfWaste.BIO;
//        } else if (vehicle instanceof VehicleGlass) {
//            typesOfWaste = TypesOfWaste.STAKLO;
//        } else if (vehicle instanceof VehicleMetal) {
//            typesOfWaste = TypesOfWaste.METAL;
//        } else if (vehicle instanceof VehicleMixed) {
//            typesOfWaste = TypesOfWaste.MJESANO;
//        } else if (vehicle instanceof VehiclePaper) {
//            typesOfWaste = TypesOfWaste.PAPIR;
//        }
//
//        for (int i = 0; i < street.getSpremnikList().size(); i++) {
//
//            if (street.getSpremnikList().get(i).kindOfWaste.equals(typesOfWaste)) {
//
//                if (isprazniSpremnik(street.getSpremnikList().get(i), vehicle)) {
//                    return true;
//                }
//
//            }
//
//        }
//        return false;
//    }
//
//    private boolean isprazniSpremnik(Spremnik spremnik, Vehicle vehicle) {
//        boolean success = false;
//
//        if (spremnik.getFilled() > 0) {
//            float mjestaUVozilu = vehicle.getCapacity() - vehicle.getFilled();
//
//            if (spremnik.getFilled() <= mjestaUVozilu) {
//                vehicle.addWaste(spremnik.getFilled());
//                spremnik.empty(spremnik.getFilled());
//                success = true;
//                vehicle.increaseNumberOfProcessedContainers();
//                vehicle.addProcessedContainers(spremnik);
//
//                this.builderDirector.addDividerLineInReport(false);
//                this.builderDirector.addTextLineInReport("Otpad preuzima vozilo:                " + vehicle.getName() + "                     Ciklus: " + cycleNumber + ". ", false);
//                this.builderDirector.addDividerLineInReport(false);
//
//                this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), false);
//                this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), false);
//                this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), false);
//
//                this.builderDirector.addDividerLineInReport(false);
//                this.builderDirector.addEmptyLineInReport(false);
//
//                isAllWasteCollected = false; //todo check if filled
//                if (vehicle.getCapacity() == vehicle.getFilled()) {
//                    driveToLandfill(vehicle);
//                }
//            } else {
//                vehicle.addWaste(mjestaUVozilu);
//                spremnik.empty(mjestaUVozilu);
//                success = true;
//                //vehicle.increaseNumberOfProcessedContainers();
//                this.builderDirector.addDividerLineInReport(false);
//                this.builderDirector.addTextLineInReport("Otpad preuzima vozilo:                " + vehicle.getName() + "                     Ciklus: " + cycleNumber + ". ", false);
//                this.builderDirector.addDividerLineInReport(false);
//
//                this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), false);
//                this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), false);
//                this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), false);
//
//                this.builderDirector.addDividerLineInReport(false);
//                this.builderDirector.addEmptyLineInReport(false);
//
//                driveToLandfill(vehicle);
//                isAllWasteCollected = false;
//
//            }
//
//        } else {
//            success = false;
//        }
//
//        return success;
//
//    }
//
//    private void driveToLandfill(Vehicle vehicle) {
//
//        this.builderDirector.addEmptyLineInReport(false);
//        this.builderDirector.addEmptyLineInReport(false);
//        this.builderDirector.addEmptyLineInReport(false);
//        this.builderDirector.addTitleInReport("Vožnja kamiona na odlagalište", false);
//
//        this.builderDirector.addDividerLineInReport(false);
//        this.builderDirector.addTextLineInReport("Na odlagalište ide vozilo:                    " + vehicle.getName(), false);
//        this.builderDirector.addDividerLineInReport(false);
//
//        this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), false);
//        this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), false);
//        this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), false);
//
//        this.builderDirector.addDividerLineInReport(false);
//
//        this.builderDirector.addTextLineInReport("Broj vozila na odlagalištu:   " + (landfill.getAllVehiclesAtLandfill().size() + 1), false);
//
//        this.builderDirector.addTitleInReport("Vožnja kamiona na odlagalište", false);
//        this.builderDirector.addEmptyLineInReport(false);
//        this.builderDirector.addEmptyLineInReport(false);
//        this.builderDirector.addEmptyLineInReport(false);
//
//        vehicle.setLastStreet(selectedStreetIndex);
//
//        landfill.vehicleComesToLandfill(vehicle);//getAllVehiclesAtLandfill().add(vehicle);
//    }
//
//    private Vehicle chooseVehicle(int chosenIndex) {
//        /* int from = 0;
//        int to = allVehiclesInProcess.size() - 1;
//        int chosenIndex = CommonDataSingleton.getInstance().getRandomInt(from, to);*/
//        return allVehiclesInProcess.get(chosenIndex);
//    }
//
//    private ArrayList<Integer> getStreetRandomArray() {
//        ArrayList<Integer> streetArray;
//
//        if (isVehicleSelectsStreet) {
//            streetArray = CommonDataSingleton.getInstance().getRandomArray(streets.size());
//        } else {
//            streetArray = randomStreetArray;
//        }
//
//        return streetArray;
//
//    }
}
