/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.decorator;

/**
 *
 * @author Zoran
 */
public class AutoPilotDecorator extends VehicleEquipmentDecorator {

    public AutoPilotDecorator(IVehicleEquipment decoratedVehicleEquipment) {
        super(decoratedVehicleEquipment);
    }

    @Override
    public void turnOn() {
        turnOnAutopilot();
        decoratedVehicleEquipment.turnOn();
    }

    private void turnOnAutopilot() {
        this.builderDirector.addTextLineInReport("Uključen autopilot ", false);

    }

    @Override
    public String getDescription() {
        return decoratedVehicleEquipment.getDescription()
                + ", vozilo ima auto-pilot";
    }
}
