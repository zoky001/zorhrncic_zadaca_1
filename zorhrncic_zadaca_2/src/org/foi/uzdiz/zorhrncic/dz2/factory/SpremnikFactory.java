/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.factory;

import org.foi.uzdiz.zorhrncic.dz2.bridge.BioTank;
import org.foi.uzdiz.zorhrncic.dz2.bridge.GlassTank;
import org.foi.uzdiz.zorhrncic.dz2.bridge.MetalTank;
import org.foi.uzdiz.zorhrncic.dz2.bridge.MixedTank;
import org.foi.uzdiz.zorhrncic.dz2.bridge.PaperTank;
import org.foi.uzdiz.zorhrncic.dz2.bridge.Tank;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Kanta;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Kontejner;
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
public class SpremnikFactory extends Factory {

    @Override
    public Spremnik getSpremnik(TypesOfSpremnik spremnikType, TypesOfWaste waste, float capacity) {
        if (spremnikType == null || waste == null) {
            return null;
        }

        Tank tank = null;

        switch (waste) {
            case BIO:
                tank = new BioTank(capacity);
            case METAL:
                tank = new MetalTank(capacity);
            case MJESANO:
                tank = new MixedTank(capacity);
            case PAPIR:
                tank = new PaperTank(capacity);
            case STAKLO:
                tank = new GlassTank(capacity);

        }

        switch (spremnikType) {
            case KANTA:
                return new Kanta(tank, waste);
            case KONTEJNER:
                return new Kontejner(tank, waste);
            default:
                return null;
        }

    }

    @Override
    public Waste getWaste(TypesOfWaste waste, float amount) {
        return null;
    }

    @Override
    public User getUser(TypesOfUser type) {
        return null;
    }

    @Override
    public Vehicle getVehicle(TypesOfWaste waste, float capacity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
