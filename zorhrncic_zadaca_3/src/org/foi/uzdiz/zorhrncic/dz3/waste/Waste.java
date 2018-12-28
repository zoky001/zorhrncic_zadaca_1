/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.waste;

/**
 *
 * @author Zoran
 */
public abstract class Waste {
    
    private float amount;

    public Waste(float amount) {
        this.amount = amount;
    }
    
    

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
    
    
    
}
