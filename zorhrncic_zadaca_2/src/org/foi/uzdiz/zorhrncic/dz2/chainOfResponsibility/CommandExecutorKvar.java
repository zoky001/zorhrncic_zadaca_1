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
public class CommandExecutorKvar extends CommandExecutor {

    public CommandExecutorKvar() {
        this.typeOfCommand = typeOfCommand.KVAR;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.command = command;
        this.context = dispecerContext;
        System.out.println("JA SAM COMMAND EXECUTOR __ KVAR ___ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Dogodio se kvar na vozilima", false);
        moveVehicleInMalfunctionList();

        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addTextLineInReport("Završeno premještanje vozila u kvar - listu.", false);
        this.builderDirector.addDividerLineInReport(false);
        return this.context;
    }

    private void moveVehicleInMalfunctionList() {
        for (Vehicle vehicle : command.getVehiclesList()) {
            if (context.getAllVehiclesInProcess().contains(vehicle) && !context.getAllVehiclesInMalfunction().contains(vehicle)) {
                context.getAllVehiclesInProcess().remove(vehicle);
                context.getAllVehiclesInMalfunction().add(vehicle);
                this.builderDirector.addTextLineInReport("Vozilo u kvaru.. ID:" + vehicle.getId(), false);

            }

        }
    }
}
