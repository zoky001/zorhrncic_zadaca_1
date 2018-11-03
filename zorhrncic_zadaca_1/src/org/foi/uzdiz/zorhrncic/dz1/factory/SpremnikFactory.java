/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.factory;

import org.foi.uzdiz.zorhrncic.dz1.ezo.Kanta;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Kontejner;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfSpremnik;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfUser;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz1.users.User;

/**
 *
 * @author Zoran
 */
public class SpremnikFactory extends AbstarctFactory {

    @Override
    public User getUser(TypesOfUser type) {
        return null;
    }

    @Override
   public Spremnik getSpremnik(TypesOfSpremnik spremnikType, TypesOfWaste waste) {
        if (spremnikType == null || waste == null) {
            return null;
        }

        switch (spremnikType) {
            case KANTA:
                return new Kanta(waste);
            case KONTEJNER:
                return new Kontejner(waste);
            default:
                return null;
        }

    }

}