/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.factory;

import org.foi.uzdiz.zorhrncic.dz2.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfSpremnik;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfUser;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz2.users.BigUser;
import org.foi.uzdiz.zorhrncic.dz2.users.MediumUser;
import org.foi.uzdiz.zorhrncic.dz2.users.SmallUser;
import org.foi.uzdiz.zorhrncic.dz2.users.User;
import org.foi.uzdiz.zorhrncic.dz2.waste.Waste;

/**
 *
 * @author Zoran
 */
public class UserFactory extends Factory {

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
    public Spremnik getSpremnik(TypesOfSpremnik spremnikType, TypesOfWaste waste, float capacity) {
        return null;
    }

    @Override
    public Waste getWaste(TypesOfWaste waste, float amount) {
        return null;
    }

    @Override
    public Vehicle getVehicle(TypesOfWaste waste, float capacity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
