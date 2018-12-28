/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.bridge;

/**
 *
 * @author Zoran
 */
public class BioTank implements Tank {

    private float capacity;
    private float filled;

    public BioTank(float capacity) {
        this.capacity = capacity;
        this.filled = 0;
    }

    public BioTank(BioTank tank) {
        this.capacity = tank.capacity;
        this.filled = tank.filled;
    }

    @Override
    public Tank clone() {
        return new BioTank(this);
    }

    @Override
    public float fill(float amount) {
        this.filled = this.filled + amount;
        return this.filled;
    }

    @Override
    public float empty(float amount) {
        this.filled = this.filled - amount;
        return this.filled;
    }

    @Override
    public float emptyToTheEnd() {
        this.filled = 0;
        return this.filled;
    }

    @Override
    public float getFilled() {
        return this.filled;
    }

    @Override
    public float getCapacity() {
        return this.capacity;
    }

}
