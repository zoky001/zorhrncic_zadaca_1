/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.chainOfResponsibility;

import org.foi.uzdiz.zorhrncic.dz2.composite.CompositePlace;
import org.foi.uzdiz.zorhrncic.dz2.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz2.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz2.iterator.TypeOfCommand;

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
        this.builderDirector.addTitleInReport("Ispis statusa", false);
        printStatus();

        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addTextLineInReport("Završio ispis statusa.", false);
        this.builderDirector.addDividerLineInReport(false);
        return this.context;
    }

    private void printStatus() {
        for (Vehicle vehicle : context.getAllVehicles()) {
            if (!context.getAllVehiclesInMalfunction().contains(vehicle)) {

                this.builderDirector.addDividerLineInReport(false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);

                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|          IME VOZILA: " + vehicle.getName() + "                 ", false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|          ID VOZILA: " + vehicle.getId() + "                 ", false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|          Status vozila: " + getStatus(vehicle) + "                 ", false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|   Količina u vozilu:            |  " + vehicle.getFilled(), false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|   Količina do popunjavanja:     |  " + (vehicle.getCapacity() - vehicle.getFilled()), false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|   Nosivost:                     |  " + vehicle.getCapacity(), false);
                this.builderDirector.addTextLineInReport("  ------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|   Broj odlazaka na pražnjenje:  |  " + vehicle.getNumberOfDepartures(), false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|   Broj ispražnejnih spremnika:  |  " + vehicle.getNumberOfProcessedContainers(), false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|   Količina preveženog otpada:   |  " + vehicle.getTotal(), false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|   Tip motora:                   |  " + vehicle.getTypesOfVehicleEngine().name(), false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addTextLineInReport("|   Vozači:                       |  " + vehicle.getDrivers().toString(), false);
                this.builderDirector.addTextLineInReport("------------------------------------------------", false);
                this.builderDirector.addDividerLineInReport(false);

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

    private String getStatus(Vehicle vehicle) {

        if (context.getAllVehiclesAtParking().contains(vehicle) && !context.getAllVehiclesInMalfunction().contains(vehicle)) {
            return "Na parkiralištu";
        } else if (context.getAllVehiclesInProcess().contains(vehicle) && !context.getAllVehiclesAtLandfill().contains(vehicle) && !context.getAllVehiclesInMalfunction().contains(vehicle)) {
            return "U procesu sakupljanja";
        } else if (context.getAllVehiclesOnControll().contains(vehicle) && !context.getAllVehiclesAtLandfill().contains(vehicle) && !context.getAllVehiclesInMalfunction().contains(vehicle)) {
            return "U kontroli";
        } else if (context.getAllVehiclesAtLandfill().contains(vehicle)) {
            return "Na odlagalištu";
        } else {
            return "-";
        }

    }

}
