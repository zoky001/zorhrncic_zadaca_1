/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility;

import java.util.List;
import org.foi.uzdiz.zorhrncic.dz3.composite.CompositePlace;
import org.foi.uzdiz.zorhrncic.dz3.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.Driver;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.TypeOfVehicleState;
import org.foi.uzdiz.zorhrncic.dz3.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz3.iterator.TypeOfCommand;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypeOfDriverState;

/**
 *
 * @author Zoran
 */
public class CommandExecutorStatus extends CommandExecutor {

    public CommandExecutorStatus() {
        this.typeOfCommand = typeOfCommand.STATUS;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.command = command;
        this.context = dispecerContext;
        //  System.out.println("JA SAM COMMAND EXECUTOR __ STATUS ___ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Izvršavam komandu \"STATUS\"..", true);
        printStatus();
        this.builderDirector.addTitleInReport("Završena komanda \"STATUS\"..", true);
        this.builderDirector.addDividerLineInReport(true);
        return this.context;
    }

    private void printStatus() {
        String line;
        for (Vehicle vehicle : context.getAllVehicles()) {
            // if (!context.getAllVehiclesInMalfunction().contains(vehicle)) {
            if (vehicle.getState() != TypeOfVehicleState.CRASH) {

                this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                line = "   ID VOZILA: " + vehicle.getId() + "  |  IME VOZILA: " + vehicle.getName() + " |  SATUS: " + getStatus(vehicle);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|" + line, true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                //  this.builderDirector.addTextLineInReport("|          ID VOZILA: " + vehicle.getId() + "                 ", true);
                //  this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                //  this.builderDirector.addTextLineInReport("|          Status vozila: " + getStatus(vehicle) + "                 ", true);
                //  this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|   Količina u vozilu:            |  " + vehicle.getFilled(), true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|   Količina do popunjavanja:     |  " + (vehicle.getCapacity() - vehicle.getFilled()), true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|   Nosivost:                     |  " + vehicle.getCapacity(), true);
                this.builderDirector.addTextLineInReport("  ------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|   Broj odlazaka na pražnjenje:  |  " + vehicle.getNumberOfDepartures(), true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|   Broj ispražnejnih spremnika:  |  " + vehicle.getNumberOfProcessedContainers(), true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|   Količina preveženog otpada:   |  " + vehicle.getTotal(), true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|   Tip motora:                   |  " + vehicle.getTypesOfVehicleEngine().name(), true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|   Vozači:                       |  " + getDriversList(vehicle.getDrivers()), true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addDividerLineInReport(true);

                /*
                System.out.println("------------------------------------------------",false);

                System.out.println("------------------------------------------------",false);
                System.out.println("|          IME VOZILA: " + vehicle.getName() + "                 ");
                System.out.println("------------------------------------------------");
                System.out.println("|          ID VOZILA: " + vehicle.getId() + "                 ");
                System.out.println("------------------------------------------------");
                System.out.println("|          Status vozila: " + getStatus(vehicle) + "                 ");
                System.out.println("------------------------------------------------");
                System.out.println("|   Količina u vozilu:            |  " + vehicle.getFilled());
                System.out.println("------------------------------------------------");
                System.out.println("|   Količina do popunjavanja:     |  " + (vehicle.getCapacity() - vehicle.getFilled()));
                System.out.println("------------------------------------------------");
                System.out.println("|   Nosivost:                     |  " + vehicle.getCapacity());
                System.out.println("  ------------------------------------------------");
                System.out.println("|   Broj odlazaka na pražnjenje:  |  " + vehicle.getNumberOfDepartures());
                System.out.println("------------------------------------------------");
                System.out.println("|   Broj ispražnejnih spremnika:  |  " + vehicle.getNumberOfProcessedContainers());
                System.out.println("------------------------------------------------");
                System.out.println("|   Količina preveženog otpada:   |  " + vehicle.getTotal());
                System.out.println("------------------------------------------------");
                System.out.println("|   Tip motora:                   |  " + vehicle.getTypesOfVehicleEngine().name());
                System.out.println("------------------------------------------------");
                System.out.println("|   Vozači:                       |  " + vehicle.getDrivers().toString());
                System.out.println("------------------------------------------------");
                System.out.println("------------------------------------------------------------------------------------------------------------");
                 */
            }

        }
    }

    private String getDriversList(List<Driver> drivers) {
        String line = "";
        try {
            for (Driver driver : drivers) {
                if (driver.getState() == TypeOfDriverState.VOZI_KAMION) {
                    line = line + " " + driver.getName().toUpperCase() + ", ";
                } else {
                    line = line + " " + driver.getName() + ", ";
                }
            }
        } catch (Exception e) {
        }
        return line;
    }

    private String getStatus(Vehicle vehicle) {
        switch (vehicle.getState()) {
            case PARKING:
                return "Na parkiralištu";
            case READY:
                return "U procesu sakupljanja";
            case CONTROL:
                return "U kontroli";
            case CRASH:
                return "-";
            case GAS_STATION:
                return "Na benzinskoj";
            case LANDFILL:
                return "Na odlagalištu";
            default:
                return "-";
        }

//        if (context.getAllVehiclesAtParking().contains(vehicle) && !context.getAllVehiclesInMalfunction().contains(vehicle)) {
//            return "Na parkiralištu";
//        } else if (context.getAllVehiclesInProcess().contains(vehicle) && !context.getAllVehiclesAtLandfill().contains(vehicle) && !context.getAllVehiclesInMalfunction().contains(vehicle)) {
//            return "U procesu sakupljanja";
//        } else if (context.getAllVehiclesOnControll().contains(vehicle) && !context.getAllVehiclesAtLandfill().contains(vehicle) && !context.getAllVehiclesInMalfunction().contains(vehicle)) {
//            return "U kontroli";
//        } else if (context.getAllVehiclesAtLandfill().contains(vehicle)) {
//            return "Na odlagalištu";
//        } else {
//            return "-";
//        }
    }

}
