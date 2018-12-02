/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.chainOfResponsibility;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.uzdiz.zorhrncic.dz2.composite.Street;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Dispecer;
import org.foi.uzdiz.zorhrncic.dz2.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz2.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz2.iterator.Iterator;
import org.foi.uzdiz.zorhrncic.dz2.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz2.singleton.CommonDataSingleton;

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
        System.out.println("JA SAM COMMAND EXECUTOR __KRENI S PARAMETRIMA__ : " + command.getTypeOfCommand().getCommand() + " - " + command.getValue());
        this.builderDirector.addTitleInReport("Kreće preuzimanje otpada u " + command.getValue() + " ciklusa.", false);
        startCollecting();
        return context;
    }

    public void startCollecting() {

        try {
            context.setNumberOfCyclesAtLandfill(Integer.parseInt((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.brojRadnihCiklusaZaOdvoz)));

        } catch (Exception e) {
            builderDirector.addErrorInReport("Greška prilikom učitavanja broja radnih ciklusa za odvoz!", false);
            System.exit(0);
        }

        this.builderDirector.addTitleInReport("Početak sakupljanja otpada po ulicama", false);

        if (((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.preuzimanje)).equalsIgnoreCase(Constants.VOZILA_NE_ODREDJUJE)) {

            context.setRandomStreetArray(CommonDataSingleton.getInstance().getRandomArray(context.getStreets().size()));
            context.setIsVehicleSelectsStreet(false);
        } else if (((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.preuzimanje)).equalsIgnoreCase(Constants.VOZILO_ODREDJUJE)) {
            context.setIsVehicleSelectsStreet(true);
        } else {
            builderDirector.addErrorInReport("Nedostaje parametar \"preuzimanje\" !!", false);
            System.exit(0);
        }

        // ArrayList<Integer> randomArray = CommonDataSingleton.getInstance().getRandomArray(allVehiclesInProcess.size());
        int numberOfRequiredCycle = command.getValue();
        while ((!context.isIsAllWasteCollected() || context.getAllVehiclesAtLandfill().size() > 0) && numberOfRequiredCycle > 0) {
            context.setIsAllWasteCollected(true);
            for (int i = 0; i < context.getAllVehiclesInProcess().size(); i++) {

                context.setVehicleInProcess(context.getAllVehiclesInProcess().get(i));
                if (checkIsVehicleAtLandfill(context.getVehicleInProcess())) {
                    continue;
                }

                pickUpWaste(context.getVehicleInProcess());
                context.setCycleNumber(context.getCycleNumber() + 1);
                numberOfRequiredCycle--;
               
                if (numberOfRequiredCycle <= 0) {
                    break;
                }
            }

        }

      //todo  driveAllVehiclesToTheLandfill();

  /*      this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addTitleInReport("Završeno sakupljanje otpad!!!", false);
        this.builderDirector.addTitleInReport("Završeno sakupljanje otpad!!!", false);
        this.builderDirector.addTitleInReport("Završeno sakupljanje otpad!!!", false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);

        context.getLandfill().creteReport();
*/
    }

    private void driveAllVehiclesToTheLandfill() {
        try {
            for (int i = 0; i < context.getAllVehiclesInProcess().size(); i++) {
                if (!context.getAllVehiclesAtLandfill().contains(context.getAllVehiclesInProcess().get(i))) {
                    //landfill.vehicleComesToLandfill(allVehiclesInProcess.get(i));
                    driveToLandfill(context.getAllVehiclesInProcess().get(i));
                }
            }

        } catch (Exception e) {

            builderDirector.addErrorInReport("driveAllVehiclesToTheLandfill error " + e, false);

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
            context.setSelectedStreet(context.getStreets().get(streetArray.get(i)));
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

    private boolean pickUpWasteInStreet(Vehicle vehicle, Street street) {
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

                this.builderDirector.addDividerLineInReport(false);
                this.builderDirector.addTextLineInReport("Otpad preuzima vozilo:                " + vehicle.getName() + "                     Ciklus: " + context.getCycleNumber() + ". ", false);
                this.builderDirector.addDividerLineInReport(false);

                this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), false);
                this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), false);
                this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), false);

                this.builderDirector.addDividerLineInReport(false);
                this.builderDirector.addEmptyLineInReport(false);

                context.setIsAllWasteCollected(false);//isAllWasteCollected = false; //todo check if filled
                if (vehicle.getCapacity() == vehicle.getFilled()) {
                    driveToLandfill(vehicle);
                }
            } else {
                vehicle.addWaste(mjestaUVozilu);
                spremnik.empty(mjestaUVozilu);
                success = true;
                //vehicle.increaseNumberOfProcessedContainers();
                this.builderDirector.addDividerLineInReport(false);
                this.builderDirector.addTextLineInReport("Otpad preuzima vozilo:                " + vehicle.getName() + "                     Ciklus: " + context.getCycleNumber() + ". ", false);
                this.builderDirector.addDividerLineInReport(false);

                this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), false);
                this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), false);
                this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), false);

                this.builderDirector.addDividerLineInReport(false);
                this.builderDirector.addEmptyLineInReport(false);

                driveToLandfill(vehicle);
                //  isAllWasteCollected = false;
                context.setIsAllWasteCollected(false);//isAllWasteCollected = false; //todo check if filled

            }

        } else {
            success = false;
        }

        return success;

    }

    private void driveToLandfill(Vehicle vehicle) {

        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addTitleInReport("Vožnja kamiona na odlagalište", false);

        this.builderDirector.addDividerLineInReport(false);
        this.builderDirector.addTextLineInReport("Na odlagalište ide vozilo:                    " + vehicle.getName(), false);
        this.builderDirector.addDividerLineInReport(false);

        this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), false);
        this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), false);
        this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), false);

        this.builderDirector.addDividerLineInReport(false);

        this.builderDirector.addTextLineInReport("Broj vozila na odlagalištu:   " + (context.getAllVehiclesAtLandfill().size() + 1), false);

        this.builderDirector.addTitleInReport("Vožnja kamiona na odlagalište", false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);

        vehicle.setLastStreet(context.getSelectedStreetIndex());

        context.getLandfill().vehicleComesToLandfill(vehicle);//getAllVehiclesAtLandfill().add(vehicle);
    }

    private Vehicle chooseVehicle(int chosenIndex) {
        /* int from = 0;
        int to = allVehiclesInProcess.size() - 1;
        int chosenIndex = CommonDataSingleton.getInstance().getRandomInt(from, to);*/
        return context.getAllVehiclesInProcess().get(chosenIndex);
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
}
