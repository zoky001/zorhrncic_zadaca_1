/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.users;

/**
 *
 * @author Zoran
 */
public abstract class User {
    private static int idIncrement = 0;
    private int id;

    public User() {
        this.id = idIncrement++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    
   
    
}
