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
public class CommandExecutorPripremi extends CommandExecutor {

    public CommandExecutorPripremi() {
        this.typeOfCommand = typeOfCommand.PRIPREMI;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.command = command;
        this.context = dispecerContext;
        System.out.println("JA SAM COMMAND EXECUTOR __PRIPREMI__ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Pripremam vozila..", false);
        prepareVehicles();
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addTextLineInReport("Završeno pripremanje vozila.", false);
        this.builderDirector.addDividerLineInReport(false);
        return this.context;
    }

    private void prepareVehicles() {
        for (Vehicle vehicle : command.getVehiclesList()) {
            if (context.getAllVehiclesAtParking().contains(vehicle) && !context.getAllVehiclesInProcess().contains(vehicle)) {
                context.getAllVehiclesAtParking().remove(vehicle);
                context.getAllVehiclesInProcess().add(vehicle);
                this.builderDirector.addTextLineInReport("Pripremljeno vozilo " + vehicle.getId(), false);

            }
            if (context.getAllVehiclesOnControll().contains(vehicle) && !context.getAllVehiclesInProcess().contains(vehicle)) {
                context.getAllVehiclesOnControll().remove(vehicle);
                context.getAllVehiclesInProcess().add(vehicle);
                this.builderDirector.addTextLineInReport("Pripremljeno vozilo " + vehicle.getId(), false);
            }
            if (context.getAllVehiclesInMalfunction().contains(vehicle) && context.getAllVehiclesAtLandfill().contains(vehicle) && !context.getAllVehiclesInProcess().contains(vehicle)) {
                context.getAllVehiclesInMalfunction().remove(vehicle);
                context.getAllVehiclesInProcess().add(vehicle);
                this.builderDirector.addTextLineInReport("Pripremljeno vozilo " + vehicle.getId(), false);
            }

        }
    }

}
