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
import org.foi.uzdiz.zorhrncic.dz2.waste.BioWaste;
import org.foi.uzdiz.zorhrncic.dz2.waste.GlassWaste;
import org.foi.uzdiz.zorhrncic.dz2.waste.MetalWaste;
import org.foi.uzdiz.zorhrncic.dz2.waste.MixedWaste;
import org.foi.uzdiz.zorhrncic.dz2.waste.PaperWaste;
import org.foi.uzdiz.zorhrncic.dz2.waste.Waste;

/**
 *
 * @author Zoran
 */
public class WasteFactory extends AbstarctFactory {

    @Override
    public Waste getWaste(TypesOfWaste waste, float amount) {
        if (waste == null || amount == amount - 1) {
            return null;
        }

        switch (waste) {
            case BIO:
                return new BioWaste(amount);
            case METAL:
                return new MetalWaste(amount);
            case MJESANO:
                return new MixedWaste(amount);
            case PAPIR:
                return new PaperWaste(amount);
            case STAKLO:
                return new GlassWaste(amount);
            default:
                return null;

        }

    }

    @Override
    public User getUser(TypesOfUser type) {
        return null;
    }

    @Override
    public Spremnik getSpremnik(TypesOfSpremnik spremnikType, TypesOfWaste waste, float capacity) {
        return null;
    }

    @Override
    public Vehicle getVehicle(TypesOfWaste waste, float capacity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
