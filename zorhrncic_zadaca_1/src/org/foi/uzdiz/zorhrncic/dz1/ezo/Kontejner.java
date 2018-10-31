/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.ezo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.foi.uzdiz.zorhrncic.dz1.users.User;

/**
 *
 * @author Zoran
 */
public class Kontejner extends Spremnik {

    

    public Kontejner() {
    }

    public Kontejner(Kontejner target) {

        super(target);
        if (target != null) {
           
        }

    }

    @Override
    public Spremnik clone() {
        return new Kontejner(this);
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof Kontejner)
                || !super.equals(object2)) {
            return false;
        }
        Kontejner shape2 = (Kontejner) object2;
        return Objects.equals(shape2.usersList, usersList);
    }

    
    

}
