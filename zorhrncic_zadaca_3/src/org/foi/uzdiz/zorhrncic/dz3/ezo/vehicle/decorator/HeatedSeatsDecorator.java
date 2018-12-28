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
public class HeatedSeatsDecorator extends VehicleEquipmentDecorator {

    public HeatedSeatsDecorator(IVehicleEquipment decoratedVehicleEquipment) {
        super(decoratedVehicleEquipment);
    }

    @Override
    public void turnOn() {
        turnOnHeating();
        decoratedVehicleEquipment.turnOn();
    }

    private void turnOnHeating() {
        this.builderDirector.addTextLineInReport("Uključeno grijanje sjedala. ", false);

    }

    @Override
    public String getDescription() {
        return decoratedVehicleEquipment.getDescription()
                + ", vozilo ima grijana sjedala";
    }
}
