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
public class CommandExecutorPreuzmi extends CommandExecutor {

    public CommandExecutorPreuzmi() {
        this.typeOfCommand = typeOfCommand.PREUZMI;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.command = command;
        this.context = dispecerContext;
        // System.out.println("JA SAM COMMAND EXECUTOR __PRIPREMI__ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Izvršavam komandu \"PREUZMI\"..", true);
        vehicleProccessing();
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addTitleInReport("Završena komanda \"PREUZMI\"..", true);
        this.builderDirector.addDividerLineInReport(true);
        return this.context;
    }

    private void vehicleProccessing() {
        this.builderDirector.addTextLineInReport("Komanda PREUZMI TODO", true);
// todo komanda Obradi
    }

}
