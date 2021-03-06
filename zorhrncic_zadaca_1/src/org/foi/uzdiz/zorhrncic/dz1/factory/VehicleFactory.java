/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.factory;

import org.foi.uzdiz.zorhrncic.dz1.bridge.BioTank;
import org.foi.uzdiz.zorhrncic.dz1.bridge.GlassTank;
import org.foi.uzdiz.zorhrncic.dz1.bridge.MetalTank;
import org.foi.uzdiz.zorhrncic.dz1.bridge.MixedTank;
import org.foi.uzdiz.zorhrncic.dz1.bridge.PaperTank;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfSpremnik;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfUser;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz1.users.User;
import org.foi.uzdiz.zorhrncic.dz1.waste.BioWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.GlassWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.MetalWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.MixedWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.PaperWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.Waste;

/**
 *
 * @author Zoran
 */
public class VehicleFactory extends AbstarctFactory {

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
