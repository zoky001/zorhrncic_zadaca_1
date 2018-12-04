/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.decorator;

/**
 *
 * @author Zoran
 */
public class LedLightsDecorator extends VehicleEquipmentDecorator {

    public LedLightsDecorator(IVehicleEquipment decoratedVehicleEquipment) {
        super(decoratedVehicleEquipment);
    }

    @Override
    public void turnOn() {
        turnOnLights();
        decoratedVehicleEquipment.turnOn();
    }

    private void turnOnLights() {
        this.builderDirector.addTextLineInReport("Uključena LED svijetla. ", false);

    }

    @Override
    public String getDescription() {
        return decoratedVehicleEquipment.getDescription()
                + ", vozilo ima LED svijetlaj";
    }
}
