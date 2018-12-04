/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle;

import org.foi.uzdiz.zorhrncic.dz2.bridge.Tank;

/**
 *
 * @author Zoran
 */
// implementation of a vehice without any equipment
public class VehicleMetal extends Vehicle {

    public VehicleMetal(Tank tank) {
        this.setTank(tank);
    }

    @Override
    public void turnOn() {
        this.builderDirector.addTextLineInReport("Vozilo za metalni otpad  je uključeno.", false);

    }

    @Override
    public String getDescription() {
        return "Vozilo za metalni otpad.";
    }

}
