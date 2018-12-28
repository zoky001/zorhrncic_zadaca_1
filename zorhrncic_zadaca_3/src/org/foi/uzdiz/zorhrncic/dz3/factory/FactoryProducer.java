/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.factory;

import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfFactories;

/**
 *
 * @author Zoran
 */
public class FactoryProducer {

    public static Factory getFactory(TypesOfFactories choice) {

        switch (choice) {
            case USER_FACTORY:
                return new UserFactory();
            case SPREMNIK_FACTORY:
                return new SpremnikFactory();
            case WASTE_FACTORY:
                return new WasteFactory();
            case VEHICLE_FACTORY:
                return new VehicleFactory();

            default:
                return null;
        }

    }

}
