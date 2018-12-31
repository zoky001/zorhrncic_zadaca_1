/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo.strategy;

import org.foi.uzdiz.zorhrncic.dz3.composite.Street;
import org.foi.uzdiz.zorhrncic.dz3.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz3.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfWaste;

/**
 *
 * @author Zoran
 */
public class AscendingPickUpStrategy extends PickUpStrategy {

    public AscendingPickUpStrategy(Street street, Vehicle vehicle, DispecerContext context) {
        super(street, vehicle, context);
    }

    @Override
    public boolean pickUpInStreet(TypesOfWaste typesOfWaste) {

        try {
            for (int i = location; i < street.getSpremnikList().size(); i++) {
                street.setLastVehicleLocation(vehicle, i);
                if (street.getSpremnikList().get(i).kindOfWaste.equals(typesOfWaste)) {

                    if (isprazniSpremnik(street.getSpremnikList().get(i), vehicle, street)) {
                        return true;
                    }

                }

            }
        } catch (Exception e) {

        }
        return false;
    }

}
