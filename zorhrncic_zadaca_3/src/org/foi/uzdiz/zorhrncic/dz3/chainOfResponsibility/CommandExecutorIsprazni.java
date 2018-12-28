/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility;

import org.foi.uzdiz.zorhrncic.dz3.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz3.iterator.TypeOfCommand;

/**
 *
 * @author Zoran
 */
public class CommandExecutorIsprazni extends CommandExecutor {

    public CommandExecutorIsprazni() {
        this.typeOfCommand = typeOfCommand.ISPRAZNI;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.command = command;
        this.context = dispecerContext;
      //  System.out.println("JA SAM COMMAND EXECUTOR __ ISPRAZNI ___ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Premještanje vozila na odlagalište.. (Pražnjenje)", true);
        emptyVehicles();
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addTextLineInReport("Završeno pražnjenje vozila.", true);
        this.builderDirector.addDividerLineInReport(true);
        return this.context;
    }

    private void emptyVehicles() {
        for (Vehicle vehicle : command.getVehiclesList()) {
            if (context.getAllVehiclesInProcess().contains(vehicle) && !context.getAllVehiclesAtLandfill().contains(vehicle)) {
                driveToLandfill(vehicle);
                this.builderDirector.addTextLineInReport("Vozilo u istovaruje.. ID:" + vehicle.getId(), true);

            }
            if (context.getAllVehiclesInMalfunction().contains(vehicle) && !context.getAllVehiclesAtLandfill().contains(vehicle)) {
                driveToLandfill(vehicle);
                this.builderDirector.addTextLineInReport("Vozilo u istovaruje.. ID:" + vehicle.getId(), true);

            }
        }
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

}
