/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.uzdiz.zorhrncic.dz3.composite.Street;
import org.foi.uzdiz.zorhrncic.dz3.ezo.Dispecer;
import org.foi.uzdiz.zorhrncic.dz3.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz3.ezo.PickUpDirection;
import org.foi.uzdiz.zorhrncic.dz3.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz3.ezo.strategy.AscendingPickUpStrategy;
import org.foi.uzdiz.zorhrncic.dz3.ezo.strategy.DescendingPickUpStrategy;
import org.foi.uzdiz.zorhrncic.dz3.ezo.strategy.PickUpStrategy;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.TypeOfVehicleState;
import org.foi.uzdiz.zorhrncic.dz3.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz3.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz3.iterator.IIterator;

/**
 *
 * @author Zoran
 */
public class CommandExecutorKreniWithParameters extends CommandExecutor {

    public CommandExecutorKreniWithParameters() {
        this.typeOfCommand = typeOfCommand.KRENI_S_PARAMETRIMA;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.context = dispecerContext;
        this.command = command;
        //  System.out.println("JA SAM COMMAND EXECUTOR __KRENI S PARAMETRIMA__ : " + command.getTypeOfCommand().getCommand() + " - " + command.getValue());
        this.builderDirector.addTitleInReport("Izvršavam komandu \"KRENI_S_PARAMETRIMA\"..", true);

        this.builderDirector.addTitleInReport("Kreće preuzimanje otpada u " + command.getValue() + " ciklusa.", true);
        startCollecting();
        this.builderDirector.addTitleInReport("Završena komanda \"KRENI_S_PARAMETRIMA\"..", true);

        return context;
    }

    public void startCollecting() {

        try {
            context.setNumberOfCyclesAtLandfill(Integer.parseInt((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.brojRadnihCiklusaZaOdvoz)));

        } catch (Exception e) {
            builderDirector.addErrorInReport("Greška prilikom učitavanja broja radnih ciklusa za odvoz!", true);
            System.exit(0);
        }

        this.builderDirector.addTitleInReport("Početak sakupljanja otpada po ulicama", true);

        if (((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.preuzimanje)).equalsIgnoreCase(Constants.VOZILA_NE_ODREDJUJE)) {

            context.setRandomStreetArray(CommonDataSingleton.getInstance().getRandomArray(context.getStreets().size()));
            context.setIsVehicleSelectsStreet(false);
        } else if (((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.preuzimanje)).equalsIgnoreCase(Constants.VOZILO_ODREDJUJE)) {
            context.setIsVehicleSelectsStreet(true);
        } else {
            builderDirector.addErrorInReport("Nedostaje parametar \"preuzimanje\" !!", true);
            System.exit(0);
        }

        // ArrayList<Integer> randomArray = CommonDataSingleton.getInstance().getRandomArray(allVehiclesInProcess.size());
        int numberOfRequiredCycle = command.getValue();
        while (checkIfThereIsAdequateWaste() && numberOfRequiredCycle > 0) {
            context.setIsAllWasteCollected(true);
            for (int i = 0; i < context.getAllVehicles().size(); i++) {
                Vehicle v = context.getAllVehicles().get(i);
                if (v.getState() == TypeOfVehicleState.READY || v.getState() == TypeOfVehicleState.LANDFILL || v.getState() == TypeOfVehicleState.GAS_STATION) {
                    context.setVehicleInProcess(v);
                    if (checkIsVehicleAtLandfill(context.getVehicleInProcess())) {
                        continue;
                    }
                    if (context.getVehicleInProcess().checkIsFilledAtGasStation()) {
                        continue;
                    }

                    pickUpWaste(context.getVehicleInProcess());
                    context.getVehicleInProcess().increaseNumberOfPerformedCycles();

                    //context.setCycleNumber(context.getCycleNumber() + 1);
                    // numberOfRequiredCycle--;
                    if (numberOfRequiredCycle <= 0) {
                        break;
                    }
                }

            }
            context.setCycleNumber(context.getCycleNumber() + 1);
            numberOfRequiredCycle--;

        }

        // driveAllVehiclesToTheLandfill();
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addTitleInReport("Završeno sakupljanje otpad u " + command.getValue() + " ciklusa!!!", true);
//        this.builderDirector.addTitleInReport("Završeno sakupljanje otpad u  " + numberOfRequiredCycle + " ciklusa!!!", true);
//        this.builderDirector.addTitleInReport("Završeno sakupljanje otpad u  " + numberOfRequiredCycle + " ciklusa!!!", true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
// todoo ne ispiše stat
        //     context.getLandfill().creteReport();
    }

    private void driveAllVehiclesToTheLandfill() {
        try {
            for (int i = 0; i < context.getAllVehicles().size(); i++) {
                Vehicle v = context.getAllVehicles().get(i);
                if (v.getState() == TypeOfVehicleState.READY || v.getState() == TypeOfVehicleState.GAS_STATION) {
                    driveToLandfill(context.getAllVehicles().get(i));
                }

            }

        } catch (Exception e) {

            builderDirector.addErrorInReport("driveAllVehiclesToTheLandfill error " + e, true);

        }

    }

    private boolean checkIsVehicleAtLandfill(Vehicle vehicle) {
        if (context.getAllVehiclesAtLandfill().contains(vehicle)) {

            if (vehicle.increaseAndCheckNumberOfCyclesAtLandfill(context.getNumberOfCyclesAtLandfill())) {
                context.getLandfill().vehicleLeavesLandfill(vehicle);//getAllVehiclesAtLandfill().remove(vehicle);
                int index = context.getAllVehiclesInProcess().indexOf(vehicle);
                context.getAllVehiclesInProcess().remove(vehicle);
                context.getAllVehiclesInProcess().add(vehicle);

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
            context.setSelectedStreetIndex(i);
            Street street = context.getStreets().get(streetArray.get(i));
            context.setSelectedStreet(street);
            if (getStreetByVahicle(vehicle).contains(street)) {
                if (vehicle.getLastStreet() > -1 && vehicle.getLastStreet() > i) {
                    continue;
                } else if (vehicle.getLastStreet() > -1 && vehicle.getLastStreet() == i) {
                    vehicle.resetLastStreet();
                }
                if (pickUpWasteInStreet(vehicle, context.getSelectedStreet())) {
                    return;
                }
            }
        }

    }

    private ArrayList<Street> getStreetByVahicle(Vehicle vehicle) {
        try {
            return vehicle.getStreets();
        } catch (Exception e) {
        }
        return vehicle.getStreets();

    }

    private boolean pickUpWasteInStreet(Vehicle vehicle, Street street) {

        PickUpDirection direction = street.chooseDirection(vehicle, street);

        if (direction == PickUpDirection.ASCENDING) {
            if (pickUpWasteInStreet(new AscendingPickUpStrategy(street, vehicle, context))) {
                return true;
            }
        } else if (direction == PickUpDirection.DESCENDING) {
            if (pickUpWasteInStreet(new DescendingPickUpStrategy(street, vehicle, context))) {
                return true;
            }
        } else {
            this.builderDirector.addTextLineInReport("Vozilo " + vehicle.getName() + " čeka u ulici " + street.getName() + "!!", true);
            context.setIsAllWasteCollected(false);
            return true;
        }
        street.vehicleLeaveTheStreet(vehicle);
        // this.builderDirector.addTextLineInReport("Vozilo " + vehicle.getName() + " napušta ulicu " + street.getName() + "!!", true);

        return false;
    }

    public boolean pickUpWasteInStreet(PickUpStrategy pickUpMethod) {
        TypesOfWaste typesOfWaste = getTypeOfWaste(context.getVehicleInProcess());
        return pickUpMethod.pickUpInStreet(typesOfWaste);
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

    private void driveToLandfill(Vehicle vehicle) {

        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addTitleInReport("Vožnja kamiona na odlagalište", true);

        this.builderDirector.addDividerLineInReport(true);
        this.builderDirector.addTextLineInReport("Na odlagalište ide vozilo:                    " + vehicle.getName(), true);
        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), true);
        this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), true);
        this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), true);

        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addTextLineInReport("Broj vozila na odlagalištu:   " + (context.getAllVehiclesAtLandfill().size() + 1), true);

        this.builderDirector.addTitleInReport("Vožnja kamiona na odlagalište", true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);

        vehicle.setLastStreet(context.getSelectedStreetIndex());

        context.getLandfill().vehicleComesToLandfill(vehicle);//getAllVehiclesAtLandfill().add(vehicle);
    }

    private ArrayList<Integer> getStreetRandomArray() {
        ArrayList<Integer> streetArray;

        if (context.isIsVehicleSelectsStreet()) {
            streetArray = CommonDataSingleton.getInstance().getRandomArray(context.getStreets().size());
        } else {
            streetArray = context.getRandomStreetArray();
        }

        return streetArray;

    }

    private boolean checkIfThereIsAdequateWaste() {
        try {
            //  printSpremnikaZadnjeStanje();
            for (int i = 0; i < context.getAllVehicles().size(); i++) {
                Vehicle v = context.getAllVehicles().get(i);
                if (v.getState() == TypeOfVehicleState.READY || v.getState() == TypeOfVehicleState.LANDFILL || v.getState() == TypeOfVehicleState.GAS_STATION) {
                    for (Spremnik spremnik : v.getOdgovarajuceSpremnike()) {
                        if (spremnik.getFilled() > 0) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private boolean printSpremnikaZadnjeStanje() {
        try {
            for (int i = 0; i < context.getAllVehicles().size(); i++) {
                Vehicle v = context.getAllVehicles().get(i);
                if (v.getState() == TypeOfVehicleState.READY || v.getState() == TypeOfVehicleState.LANDFILL || v.getState() == TypeOfVehicleState.GAS_STATION) {
                    for (Spremnik spremnik : v.getOdgovarajuceSpremnike()) {
                        this.builderDirector.addTextLineInReport("Spremnik " + spremnik.getId() + " | " + spremnik.getTypesOfUser().name() + " | " + spremnik.getFilled(), true);

                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }
}
