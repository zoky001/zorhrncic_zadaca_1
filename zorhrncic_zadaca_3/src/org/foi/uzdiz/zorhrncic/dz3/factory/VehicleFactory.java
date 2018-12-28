/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.factory;

import org.foi.uzdiz.zorhrncic.dz3.bridge.BioTank;
import org.foi.uzdiz.zorhrncic.dz3.bridge.GlassTank;
import org.foi.uzdiz.zorhrncic.dz3.bridge.MetalTank;
import org.foi.uzdiz.zorhrncic.dz3.bridge.MixedTank;
import org.foi.uzdiz.zorhrncic.dz3.bridge.PaperTank;
import org.foi.uzdiz.zorhrncic.dz3.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfSpremnik;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfUser;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz3.users.User;
import org.foi.uzdiz.zorhrncic.dz3.waste.BioWaste;
import org.foi.uzdiz.zorhrncic.dz3.waste.GlassWaste;
import org.foi.uzdiz.zorhrncic.dz3.waste.MetalWaste;
import org.foi.uzdiz.zorhrncic.dz3.waste.MixedWaste;
import org.foi.uzdiz.zorhrncic.dz3.waste.PaperWaste;
import org.foi.uzdiz.zorhrncic.dz3.waste.Waste;

/**
 *
 * @author Zoran
 */
public class VehicleFactory extends Factory {

    @Override
    public Vehicle getVehicle(TypesOfWaste waste, float capacity) {
        if (waste == null) {
            return null;
        }

        switch (waste) {
            case BIO:
                return new VehicleBio(new BioTank(capacity));
            case METAL:
                return new VehicleMetal(new MetalTank(capacity));
            case MJESANO:
                return new VehicleMixed(new MixedTank(capacity));
            case PAPIR:
                return new VehiclePaper(new PaperTank(capacity));
            case STAKLO:
                return new VehicleGlass(new GlassTank(capacity));
            default:
                return null;

        }
    }

    @Override
    public User getUser(TypesOfUser type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Spremnik getSpremnik(TypesOfSpremnik spremnikType, TypesOfWaste waste, float capacity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Waste getWaste(TypesOfWaste waste, float amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
