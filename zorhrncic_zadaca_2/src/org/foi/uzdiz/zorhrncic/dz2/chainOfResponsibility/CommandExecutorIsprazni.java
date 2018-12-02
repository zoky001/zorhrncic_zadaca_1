/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.chainOfResponsibility;

import org.foi.uzdiz.zorhrncic.dz2.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz2.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz2.iterator.TypeOfCommand;

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
        System.out.println("JA SAM COMMAND EXECUTOR __ ISPRAZNI ___ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Premještanje vozila na odlagalište.. (Pražnjenje)", false);
        emptyVehicles();
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addTextLineInReport("Završeno pražnjenje vozila.", false);
        this.builderDirector.addDividerLineInReport(false);
        return this.context;
    }

    private void emptyVehicles() {
        for (Vehicle vehicle : command.getVehiclesList()) {
            if (context.getAllVehiclesInProcess().contains(vehicle) && !context.getAllVehiclesAtLandfill().contains(vehicle)) {
                driveToLandfill(vehicle);
                this.builderDirector.addTextLineInReport("Vozilo u istovaruje.. ID:" + vehicle.getId(), false);

            }
            if (context.getAllVehiclesInMalfunction().contains(vehicle) && !context.getAllVehiclesAtLandfill().contains(vehicle)) {
                driveToLandfill(vehicle);
                this.builderDirector.addTextLineInReport("Vozilo u istovaruje.. ID:" + vehicle.getId(), false);

            }
        }
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

}
