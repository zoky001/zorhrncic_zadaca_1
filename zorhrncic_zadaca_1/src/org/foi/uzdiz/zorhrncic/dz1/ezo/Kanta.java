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
public class Kanta extends Spremnik {

    public Kanta() {

    }

    public Kanta(Kanta target) {

        super(target);
        if (target != null) {
           
        }

    }

    @Override
    public Spremnik clone() {
        return new Kanta(this);
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof Kanta)
                || !super.equals(object2)) {
            return false;
        }
        Kanta shape2 = (Kanta) object2;
        return Objects.equals(shape2.usersList, usersList);
    }


}
