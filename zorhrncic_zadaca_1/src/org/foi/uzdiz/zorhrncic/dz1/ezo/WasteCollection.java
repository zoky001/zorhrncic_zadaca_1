/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.ezo;

import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.parser.TokenType;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz1.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz1.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz1.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz1.streets.Street;
import org.foi.uzdiz.zorhrncic.dz1.waste.BioWaste;

/**
 *
 * @author Zoran
 */
public class WasteCollection {

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
    private final Landfill landfill;

    public WasteCollection(List<Vehicle> allVehicles, List<Street> streets) {
        this.allVehiclesInProcess = allVehicles;
        this.streets = streets;

//        this.allVehiclesAtLandfill = new ArrayList<Vehicle>();
        this.landfill = new Landfill();

        builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();
        try {
            this.numberOfCyclesAtLandfill = Integer.parseInt((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.brojRadnihCiklusaZaOdvoz));
        } catch (Exception e) {
            System.out.println("Greška prilikom učitavanja broja radnih ciklusa za odvoz!");
            System.exit(0);
        }
    }

    public void startCollecting() {

        this.builderDirector.addTitleInReport("Početak sakupljanja otpada po ulicama", false);

        if (((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.preuzimanje)).equalsIgnoreCase(Constants.VOZILA_NE_ODREDJUJE)) {
            randomStreetArray = CommonDataSingleton.getInstance().getRandomArray(streets.size());
            isVehicleSelectsStreet = false;
        } else if (((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.preuzimanje)).equalsIgnoreCase(Constants.VOZILO_ODREDJUJE)) {
            isVehicleSelectsStreet = true;
        } else {
            System.out.println("Nedostaje parametar \"preuzimanje\" !!");
            System.exit(0);
        }

        ArrayList<Integer> randomArray = CommonDataSingleton.getInstance().getRandomArray(allVehiclesInProcess.size());
        while (!isAllWasteCollected || landfill.getAllVehiclesAtLandfill().size() > 0) {
            isAllWasteCollected = true;
            for (int i = 0; i < randomArray.size(); i++) {

                vehicleInProcess = chooseVehicle(randomArray.get(i));
                if (checkIsVehicleAtLandfill(vehicleInProcess)) {
                    continue;
                }

                pickUpWaste(vehicleInProcess);

            }
            cycleNumber++;
        }

        driveAllVehiclesToTheLandfill();
        landfill.creteReport();
        streets.forEach((k) -> {

            //     k.print();
            Spremnik.printArray(k.getSpremnikList());

        });

    }

    private void driveAllVehiclesToTheLandfill() {
        try {
            for (int i = 0; i < allVehiclesInProcess.size(); i++) {
                if (!landfill.getAllVehiclesAtLandfill().contains(allVehiclesInProcess.get(i))) {
                    landfill.vehicleComesToLandfill(allVehiclesInProcess.get(i));
                }
            }

        } catch (Exception e) {

            System.out.println("driveAllVehiclesToTheLandfill error " + e);
        }

    }

    private boolean checkIsVehicleAtLandfill(Vehicle vehicle) {
        if (landfill.getAllVehiclesAtLandfill().contains(vehicle)) {

            if (vehicle.increaseAndCheckNumberOfCyclesAtLandfill(numberOfCyclesAtLandfill)) {
                landfill.vehicleLeavesLandfill(vehicle);//getAllVehiclesAtLandfill().remove(vehicle);
                int index = allVehiclesInProcess.indexOf(vehicle);
                allVehiclesInProcess.remove(vehicle);
                allVehiclesInProcess.add(vehicle);

                //   System.out.println("");
                // add at the end
                return false;
            } else {
                return true;
            }

        }

        return false;
    }

    private void pickUpWaste(Vehicle vehicle) {
        ArrayList<Integer> streetArray;//= getStreetRandomArray();

        if (vehicle.getRandomStreetArray() == null) {
            vehicle.setRandomStreetArray(getStreetRandomArray());
            streetArray = vehicle.getRandomStreetArray();
        } else {
            streetArray = vehicle.getRandomStreetArray();
        }

        for (int i = 0; i < streetArray.size(); i++) {
            selectedStreetIndex = i;
            selectedStreet = streets.get(streetArray.get(i));
            if (vehicle.getLastStreet() > -1 && vehicle.getLastStreet() > i) {
                continue;
            } else if (vehicle.getLastStreet() > -1 && vehicle.getLastStreet() == i) {
                vehicle.resetLastStreet();
            }
            if (pickUpWasteInStreet(vehicle, selectedStreet)) {
                return;
            }

        }

    }

    private boolean pickUpWasteInStreet(Vehicle vehicle, Street street) {
        TypesOfWaste typesOfWaste = null;

        if (vehicle instanceof VehicleBio) {
            typesOfWaste = TypesOfWaste.BIO;
        } else if (vehicle instanceof VehicleGlass) {
            typesOfWaste = TypesOfWaste.STAKLO;
        } else if (vehicle instanceof VehicleMetal) {
            typesOfWaste = TypesOfWaste.METAL;
        } else if (vehicle instanceof VehicleMixed) {
            typesOfWaste = TypesOfWaste.MJEŠANO;
        } else if (vehicle instanceof VehiclePaper) {
            typesOfWaste = TypesOfWaste.PAPIR;
        }

        for (int i = 0; i < street.getSpremnikList().size(); i++) {

            if (street.getSpremnikList().get(i).kindOfWaste.equals(typesOfWaste)) {

                if (isprazniSpremnik(street.getSpremnikList().get(i), vehicle)) {
                    return true;
                }

            }

        }
        return false;
    }

    private boolean isprazniSpremnik(Spremnik spremnik, Vehicle vehicle) {
        boolean success = false;

        if (spremnik.getFilled() > 0) {
            float mjestaUVozilu = vehicle.getCapacity() - vehicle.getFilled();

            if (spremnik.getFilled() <= mjestaUVozilu) {
                vehicle.addWaste(spremnik.getFilled());
                spremnik.empty(spremnik.getFilled());
                success = true;
                vehicle.increaseNumberOfProcessedContainers();
                vehicle.addProcessedContainers(spremnik);

                this.builderDirector.addDividerLineInReport();
                this.builderDirector.addTextLineInReport("Otpad preuzima vozilo:                        " + vehicle.getName(), false);
                this.builderDirector.addDividerLineInReport();

                this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), false);
                this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), false);
                this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), false);

                this.builderDirector.addDividerLineInReport();
                this.builderDirector.addEmptyLineInReport();

                isAllWasteCollected = false; //todo check if filled
                if (vehicle.getCapacity() == vehicle.getFilled()) {
                    driveToLandfill(vehicle);
                }
            } else {
                vehicle.addWaste(mjestaUVozilu);
                spremnik.empty(mjestaUVozilu);
                success = true;
                //vehicle.increaseNumberOfProcessedContainers();
                this.builderDirector.addDividerLineInReport();
                this.builderDirector.addTextLineInReport("Otpad preuzima vozilo:                        " + vehicle.getName(), false);
                this.builderDirector.addDividerLineInReport();

                this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), false);
                this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), false);
                this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), false);

                this.builderDirector.addDividerLineInReport();
                this.builderDirector.addEmptyLineInReport();

                driveToLandfill(vehicle);
                isAllWasteCollected = false;

            }

        } else {
            success = false;
        }

        return success;

    }

    private void driveToLandfill(Vehicle vehicle) {

        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addTitleInReport("Vožnja kamiona na odlagalište", false);

        this.builderDirector.addDividerLineInReport();
        this.builderDirector.addTextLineInReport("Na odlagalište ide vozilo:                    " + vehicle.getName(), false);
        this.builderDirector.addDividerLineInReport();

        this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), false);
        this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), false);
        this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), false);

        this.builderDirector.addDividerLineInReport();

        this.builderDirector.addTextLineInReport("Broj vozila na odlagalištu:   " + landfill.getAllVehiclesAtLandfill().size() + 1, false);

        this.builderDirector.addTitleInReport("Vožnja kamiona na odlagalište", false);
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();

        vehicle.setLastStreet(selectedStreetIndex);

        landfill.vehicleComesToLandfill(vehicle);//getAllVehiclesAtLandfill().add(vehicle);
    }

    private Vehicle chooseVehicle(int chosenIndex) {
        /* int from = 0;
        int to = allVehiclesInProcess.size() - 1;
        int chosenIndex = CommonDataSingleton.getInstance().getRandomInt(from, to);*/
        return allVehiclesInProcess.get(chosenIndex);
    }

    private ArrayList<Integer> getStreetRandomArray() {
        ArrayList<Integer> streetArray;

        if (isVehicleSelectsStreet) {
            streetArray = CommonDataSingleton.getInstance().getRandomArray(streets.size());
        } else {
            streetArray = randomStreetArray;
        }

        return streetArray;

    }

}
