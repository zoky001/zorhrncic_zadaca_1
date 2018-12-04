/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.decorator;

import org.foi.uzdiz.zorhrncic.dz2.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz2.singleton.CommonDataSingleton;

/**
 *
 * @author Zoran
 */
// abstract decorator class - note that it implements VehicleEquipment
public abstract class VehicleEquipmentDecorator implements IVehicleEquipment {

    protected IVehicleEquipment decoratedVehicleEquipment; // the Window being decorated
    protected final ReportBuilderDirector builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();

    public VehicleEquipmentDecorator(IVehicleEquipment decoratedVehicleEquipment) {
        this.decoratedVehicleEquipment = decoratedVehicleEquipment;
    }
}
