/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.composite;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Kontejner;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz2.users.BigUser;
import org.foi.uzdiz.zorhrncic.dz2.users.MediumUser;
import org.foi.uzdiz.zorhrncic.dz2.users.SmallUser;
import org.foi.uzdiz.zorhrncic.dz2.users.User;

/**
 *
 * @author Zoran
 */
public class AreaModel {
    
    
    private String id;
    private String name;
    private List<String> parts = new ArrayList<String>();

    public AreaModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParts() {
        return parts;
    }

    public void setParts(List<String> parts) {
        this.parts = parts;
    }
    
    


}
