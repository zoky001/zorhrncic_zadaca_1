/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility;

import org.foi.uzdiz.zorhrncic.dz3.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.Driver;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz3.iterator.TypeOfCommand;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypeOfDriverState;

/**
 *
 * @author Zoran
 */
public class CommandExecutorGodisnjiOdmor extends CommandExecutor {

    public CommandExecutorGodisnjiOdmor() {
        this.typeOfCommand = typeOfCommand.GODISNJI_ODMOR;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.command = command;
        this.context = dispecerContext;
        // System.out.println("JA SAM COMMAND EXECUTOR __PRIPREMI__ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Izvršavam komandu \"GODIŠNJI ODMOR\"..", true);
        proccessing();
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addTitleInReport("Završena komanda \"GODIŠNJI ODMOR\"..", true);
        this.builderDirector.addDividerLineInReport(true);
        return this.context;
    }

    private void proccessing() {

        for (Driver driver : command.getDriversList()) {
            if (driver.getState() != TypeOfDriverState.OTKAZ) {
                driver.idiNaGO();
            }
        }

    }

}
