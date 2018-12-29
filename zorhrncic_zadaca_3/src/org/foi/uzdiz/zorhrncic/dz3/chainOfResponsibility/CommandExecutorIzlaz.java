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
public class CommandExecutorIzlaz extends CommandExecutor {

    public CommandExecutorIzlaz() {
        this.typeOfCommand = typeOfCommand.IZLAZ;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.command = command;
        this.context = dispecerContext;
        // System.out.println("JA SAM COMMAND EXECUTOR __PRIPREMI__ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Izvršavam komandu \"IZLAZ\"..", true);
        vehicleProccessing();
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addTitleInReport("Završena komanda \"IZLAZ\"..", true);
        this.builderDirector.addDividerLineInReport(true);
        return this.context;
    }

    private void vehicleProccessing() {
        this.builderDirector.addTextLineInReport("Komanda IZLAZ TODO", true);
// todo komanda Obradi
    }

}
