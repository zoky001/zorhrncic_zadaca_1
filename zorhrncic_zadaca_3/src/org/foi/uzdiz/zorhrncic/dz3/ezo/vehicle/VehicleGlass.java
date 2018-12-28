/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle;

import org.foi.uzdiz.zorhrncic.dz3.bridge.Tank;

/**
 *
 * @author Zoran
 */
// implementation of a vehice without any equipment
public class VehicleGlass extends Vehicle {

    public VehicleGlass(Tank tank) {
        this.setTank(tank);
    }

    @Override
    public void turnOn() {
        this.builderDirector.addTextLineInReport("Vozilo za stakleni otpad  je uključeno.", false);

    }

    @Override
    public String getDescription() {
        return "Vozilo za stakleni otpad.";
    }

}
