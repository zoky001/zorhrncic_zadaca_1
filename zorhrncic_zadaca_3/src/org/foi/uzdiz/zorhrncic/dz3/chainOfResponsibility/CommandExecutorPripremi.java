/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility;

import org.foi.uzdiz.zorhrncic.dz3.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state.TypeOfVehicleState;
import org.foi.uzdiz.zorhrncic.dz3.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz3.iterator.TypeOfCommand;

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
        // System.out.println("JA SAM COMMAND EXECUTOR __PRIPREMI__ : " + command.getTypeOfCommand().getCommand());
        this.builderDirector.addTitleInReport("Izvršavam komandu \"PRIPREMI\"..", true);
        prepareVehicles();
        this.builderDirector.addTitleInReport("Završena  komandu \"PRIPREMI\"..", true);
        this.builderDirector.addDividerLineInReport(true);
        return this.context;
    }

    private void prepareVehicles() {
        for (Vehicle vehicle : command.getVehiclesList()) {

            // vozila na parkingu
            if (vehicle.getState() == TypeOfVehicleState.PARKING) {
                vehicle.prepareVehicle();
                this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Palim vozilo: " + vehicle.getId(), true);
                //this.builderDirector.addDividerLineInReport(true);
                vehicle.getVehicleEquipment().turnOn();
                // this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Pripremljeno vozilo " + vehicle.getId(), true);
            }
            if (context.getAllVehiclesAtParking().contains(vehicle) && !context.getAllVehiclesInProcess().contains(vehicle)) {
                context.getAllVehiclesAtParking().remove(vehicle);
                context.getAllVehiclesInProcess().add(vehicle);

                this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Palim vozilo: " + vehicle.getId(), true);
                //this.builderDirector.addDividerLineInReport(true);
                vehicle.getVehicleEquipment().turnOn();
                // this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Pripremljeno vozilo " + vehicle.getId(), true);

            }

            // vozila na kontroli
            if (vehicle.getState() == TypeOfVehicleState.CONTROL) {
                vehicle.prepareVehicle();
                this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Palim vozilo: " + vehicle.getId(), true);
                //this.builderDirector.addDividerLineInReport(true);
                vehicle.getVehicleEquipment().turnOn();
                // this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Pripremljeno vozilo " + vehicle.getId(), true);
            }
            if (context.getAllVehiclesOnControll().contains(vehicle) && !context.getAllVehiclesInProcess().contains(vehicle)) {
                context.getAllVehiclesOnControll().remove(vehicle);
                context.getAllVehiclesInProcess().add(vehicle);

                this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Palim vozilo: " + vehicle.getId(), true);
                //   this.builderDirector.addDividerLineInReport(true);
                vehicle.getVehicleEquipment().turnOn();
                //   this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Pripremljeno vozilo " + vehicle.getId(), true);
            }

            // todo  vozila na smetlištu nakon kvara
//            if (vehicle.getState() == TypeOfVehicleState.CONTROL) {
//                vehicle.prepareVehicle();
//                this.builderDirector.addDividerLineInReport(true);
//                this.builderDirector.addTextLineInReport("Palim vozilo: " + vehicle.getId(), true);
//                //this.builderDirector.addDividerLineInReport(true);
//                vehicle.getVehicleEquipment().turnOn();
//                // this.builderDirector.addDividerLineInReport(true);
//                this.builderDirector.addTextLineInReport("Pripremljeno vozilo " + vehicle.getId(), true);
//            }
            if (context.getAllVehiclesInMalfunction().contains(vehicle) && context.getAllVehiclesAtLandfill().contains(vehicle) && !context.getAllVehiclesInProcess().contains(vehicle)) {
                context.getAllVehiclesInMalfunction().remove(vehicle);
                context.getAllVehiclesInProcess().add(vehicle);

                this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Palim vozilo: " + vehicle.getId(), true);
                //   this.builderDirector.addDividerLineInReport(true);
                vehicle.getVehicleEquipment().turnOn();
                //   this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Pripremljeno vozilo " + vehicle.getId(), true);
            }

        }
    }

}
