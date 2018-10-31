/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.users;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz1.waste.Waste;

/**
 *
 * @author Zoran
 */
public abstract class User {

    private static int idIncrement = 0;
    private int id;
    private List<Spremnik> spremnikList = new ArrayList<Spremnik>();
    
    private Waste bioWaste;
    private Waste glassWaste;
    private Waste metalWaste;
    private Waste mixedWaste;
    private Waste paperWaste;

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

    public Waste getBioWaste() {
        return bioWaste;
    }

    public void setBioWaste(Waste bioWaste) {
        this.bioWaste = bioWaste;
    }

    public Waste getGlassWaste() {
        return glassWaste;
    }

    public void setGlassWaste(Waste glassWaste) {
        this.glassWaste = glassWaste;
    }

    public Waste getMetalWaste() {
        return metalWaste;
    }

    public void setMetalWaste(Waste metalWaste) {
        this.metalWaste = metalWaste;
    }

    public Waste getMixedWaste() {
        return mixedWaste;
    }

    public void setMixedWaste(Waste mixedWaste) {
        this.mixedWaste = mixedWaste;
    }

    public Waste getPaperWaste() {
        return paperWaste;
    }

    public void setPaperWaste(Waste paperWaste) {
        this.paperWaste = paperWaste;
    }

}
