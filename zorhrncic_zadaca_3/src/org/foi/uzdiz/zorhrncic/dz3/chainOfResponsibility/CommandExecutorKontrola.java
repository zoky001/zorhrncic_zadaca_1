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
public class CommandExecutorKontrola extends CommandExecutor {

    public CommandExecutorKontrola() {
        this.typeOfCommand = typeOfCommand.KONTROLA;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.command = command;
        this.context = dispecerContext;
      //  System.out.println("JA SAM COMMAND EXECUTOR __ KONTROLA ___ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Izvršavam komandu \"KONTROLA\"..", true);
        moveVehicleToControllList();
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addTitleInReport("Završena komanda \"KONTROLA\"..", true);
        this.builderDirector.addDividerLineInReport(true);
        return this.context;
    }

    private void moveVehicleToControllList() {
        for (Vehicle vehicle : command.getVehiclesList()) {
            if (context.getAllVehiclesInProcess().contains(vehicle) && !context.getAllVehiclesOnControll().contains(vehicle)) {
                context.getAllVehiclesInProcess().remove(vehicle);
                context.getAllVehiclesOnControll().add(vehicle);
                this.builderDirector.addTextLineInReport("Vozilo u kontroli.. ID:" + vehicle.getId(), true);

            }

        }
    }

}