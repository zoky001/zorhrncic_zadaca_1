/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.bridge;

/**
 *
 * @author Zoran
 */
public interface Tank {
    public  float fill(float amount);
    public  float empty(float amount);
    public  float getCapacity();
    public  float getFilled();
    public  float emptyToTheEnd();
    public  Tank clone();
}
