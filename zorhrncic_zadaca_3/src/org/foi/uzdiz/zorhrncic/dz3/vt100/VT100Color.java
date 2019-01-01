/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.vt100;

import org.foi.uzdiz.zorhrncic.dz3.iterator.*;

/**
 *
 * @author Zoran
 */
public enum VT100Color {

    DEFAULT(37),
    RED(91),
    YELLOW(93),
    MAGENTA(95),
    LIGHT_GREEN(92),
    BLUE(96);

    // declaring private variable for getting values 
    private int color;

    // getter method 
    public int getColor() {
        return this.color;
    }

    // enum constructor - cannot be public or protected 
    private VT100Color(int command) {
        this.color = command;
    }
}
