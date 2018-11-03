/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.factory;

import org.foi.uzdiz.zorhrncic.dz1.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfSpremnik;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfUser;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz1.users.BigUser;
import org.foi.uzdiz.zorhrncic.dz1.users.MediumUser;
import org.foi.uzdiz.zorhrncic.dz1.users.SmallUser;
import org.foi.uzdiz.zorhrncic.dz1.users.User;

/**
 *
 * @author Zoran
 */
public class UserFactory extends AbstarctFactory {

    @Override
    public User getUser(TypesOfUser type) {
        if (type == null) {
            return null;
        }

        switch (type) {
            case SMALL:
                return new SmallUser();
            case MEDIUM:
                return new MediumUser();
            case BIG:
                return new BigUser();
            default:
                return null;
        }
    }

    @Override
    public Spremnik getSpremnik(TypesOfSpremnik spremnikType, TypesOfWaste waste) {
        return null;
    }

}
