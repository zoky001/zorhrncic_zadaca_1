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
        proccessing();
        this.builderDirector.addTitleInReport("Završena komanda \"PREUZMI\"..", true);
        this.builderDirector.addDividerLineInReport(true);
        return this.context;
    }

    private void proccessing() {
        Driver oldDriver = command.getDriversList().get(0);
        Vehicle vehicle = command.getVehiclesList().get(0);
        try {
            if (command.getDriversList().size() > 0 && command.getVehiclesList().size() > 0) {
                if (oldDriver.getState() == TypeOfDriverState.VOZI_KAMION) {
//                    Driver driver = findFreeDriver(oldDriver.getVehicle());
//                    if (driver != null) {
//                        driver.zauzmiVozilo(oldDriver.getVehicle());
//                        this.builderDirector.addTextLineInReport("Vozilo \"" + oldDriver.getVehicle().getName() + "\" "
//                                + "preuzima vozač \"" + driver.getName() + "\"", true);
//
//                    }

                }

                if (oldDriver.getState() == TypeOfDriverState.VOZI_KAMION || oldDriver.getState() == TypeOfDriverState.RASPOLOZIV) {
                    oldDriver.postaniNedodjeljen();
                }
                this.builderDirector.addTextLineInReport("Vozač \"" + oldDriver.getName() + "\" preuzima vozilo \"" + vehicle.getName() + "\"", true);
                oldDriver.zauzmiVozilo(vehicle);

            }
        } catch (Exception e) {
            //
        }
    }



}
