/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.users;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Spremnik;

/**
 *
 * @author Zoran
 */
public abstract class User {

    private static int idIncrement = 0;
    private int id;
    private List<Spremnik> spremnikList = new ArrayList<Spremnik>();

    public User() {
        this.id = idIncrement++;
    }

    public void addSpremnik(Spremnik spremnik) {
        this.spremnikList.add(spremnik);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Spremnik> getSpremnikList() {
        return spremnikList;
    }

    public void setSpremnikList(List<Spremnik> spremnikList) {
        this.spremnikList = spremnikList;
    }

}
