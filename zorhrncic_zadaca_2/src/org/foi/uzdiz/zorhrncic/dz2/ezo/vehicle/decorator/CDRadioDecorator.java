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
public class CDRadioDecorator extends VehicleEquipmentDecorator {

    public CDRadioDecorator(IVehicleEquipment decoratedVehicleEquipment) {
        super(decoratedVehicleEquipment);
    }

    @Override
    public void turnOn() {
        turnOnRadio();
        decoratedVehicleEquipment.turnOn();
    }

    private void turnOnRadio() {
        this.builderDirector.addTextLineInReport("Uključen CD radio. ", false);
    }

    @Override
    public String getDescription() {
        return decoratedVehicleEquipment.getDescription()
                + ", vozilo ima CD radio";
    }
}
