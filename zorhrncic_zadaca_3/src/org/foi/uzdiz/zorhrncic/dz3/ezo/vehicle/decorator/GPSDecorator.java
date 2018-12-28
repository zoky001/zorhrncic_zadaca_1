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
public class GPSDecorator extends VehicleEquipmentDecorator {

    public GPSDecorator(IVehicleEquipment decoratedVehicleEquipment) {
        super(decoratedVehicleEquipment);
    }

    @Override
    public void turnOn() {
        turnOnGPS();
        decoratedVehicleEquipment.turnOn();
    }

    private void turnOnGPS() {
        this.builderDirector.addTextLineInReport("Uključena navigacija vozila. ", false);

    }

    @Override
    public String getDescription() {
        return decoratedVehicleEquipment.getDescription()
                + ", vozilo ima navigacijski uređaj uređaj";
    }
}
