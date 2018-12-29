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
public class CommandExecutorObradi extends CommandExecutor {

    public CommandExecutorObradi() {
        this.typeOfCommand = typeOfCommand.OBRADI;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.command = command;
        this.context = dispecerContext;
        // System.out.println("JA SAM COMMAND EXECUTOR __PRIPREMI__ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Izvršavam komandu \"OBRADI\"..", true);
        proccessing();
        this.builderDirector.addTitleInReport("Završena komanda \"OBRADI\"..", true);
        this.builderDirector.addDividerLineInReport(true);
        return this.context;
    }

    private void proccessing() {
        try {
            for (Vehicle vehicle : command.getVehiclesList()) {
                vehicle.setArea(command.getPlace());
                this.builderDirector.addTextLineInReport("Vozilo \"" + vehicle.getName() + "\" se dodjeljuje područje \"" + command.getPlace().getName() + "\"", true);

            }

        } catch (Exception e) {

            //
        }
    }

}
