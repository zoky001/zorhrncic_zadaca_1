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
public class CommandExecutorVozaci extends CommandExecutor {

    public CommandExecutorVozaci() {
        this.typeOfCommand = typeOfCommand.VOZACI;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        this.command = command;
        this.context = dispecerContext;
        // System.out.println("JA SAM COMMAND EXECUTOR __PRIPREMI__ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Izvršavam komandu \"VOZACI\"..", true);
        proccessing();
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addTitleInReport("Završena komanda \"VOZACI\"..", true);

        this.builderDirector.addDividerLineInReport(true);
        return this.context;
    }

    private void proccessing() {
        for (Driver driver : context.getDriversList()) {
            if (!context.getAllVehiclesInMalfunction().contains(driver)) {

                this.builderDirector.addEmptyLineInReport(true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|          IME VOZAČA: " + driver.getName() + "                 ", true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                this.builderDirector.addTextLineInReport("|          STATUS VOZAČA: " + driver.getState().toString() + "                 ", true);
                this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                if (driver.getState() == TypeOfDriverState.VOZI_KAMION) {
                    this.builderDirector.addTextLineInReport("|          VOZI KAMION: " + driver.getVehicle().getName() + "                 ", true);
                    this.builderDirector.addTextLineInReport("------------------------------------------------", true);
                }
                this.builderDirector.addEmptyLineInReport(true);

            }

        }
    }

    private String getStatus(Driver driver) {

        if (context.getAllVehiclesAtParking().contains(driver) && !context.getAllVehiclesInMalfunction().contains(driver)) {
            return "Na parkiralištu";
        } else if (context.getAllVehiclesInProcess().contains(driver) && !context.getAllVehiclesAtLandfill().contains(driver) && !context.getAllVehiclesInMalfunction().contains(driver)) {
            return "U procesu sakupljanja";
        } else if (context.getAllVehiclesOnControll().contains(driver) && !context.getAllVehiclesAtLandfill().contains(driver) && !context.getAllVehiclesInMalfunction().contains(driver)) {
            return "U kontroli";
        } else if (context.getAllVehiclesAtLandfill().contains(driver)) {
            return "Na odlagalištu";
        } else {
            return "-";
        }

    }

}
