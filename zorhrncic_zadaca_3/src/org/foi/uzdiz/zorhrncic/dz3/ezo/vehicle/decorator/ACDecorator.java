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
public class ACDecorator extends VehicleEquipmentDecorator {

    public ACDecorator(IVehicleEquipment decoratedVehicleEquipment) {
        super(decoratedVehicleEquipment);
    }

    @Override
    public void turnOn() {
        turnOnAC();
        decoratedVehicleEquipment.turnOn();
    }

    private void turnOnAC() {
       
        this.builderDirector.addTextLineInReport("Uključen klima uređaj ", false);

    }

    @Override
    public String getDescription() {
        return decoratedVehicleEquipment.getDescription()
                + ", vozilo ima klima uređaj";
    }
}
