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
import org.foi.uzdiz.zorhrncic.dz2.users.User;
import org.foi.uzdiz.zorhrncic.dz2.waste.Waste;

/**
 *
 * @author Zoran
 */
public abstract class AbstarctFactory {

    public abstract User getUser(TypesOfUser type);

    public abstract Spremnik getSpremnik(TypesOfSpremnik spremnikType, TypesOfWaste waste, float capacity);

    public abstract Waste getWaste(TypesOfWaste waste, float amount);

    public abstract Vehicle getVehicle(TypesOfWaste waste, float capacity);

}
