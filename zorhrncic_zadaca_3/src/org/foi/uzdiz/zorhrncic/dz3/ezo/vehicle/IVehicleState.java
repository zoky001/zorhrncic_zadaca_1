/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle;

import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.*;
import org.foi.uzdiz.zorhrncic.dz3.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypeOfDriverState;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypeOfVehicleState;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;

/**
 *
 * @author Zoran
 */
public abstract class IVehicleState {

    protected final ReportBuilderDirector builderDirectior;

    public IVehicleState() {
        this.builderDirectior = CommonDataSingleton.getInstance().getReportBuilderDirector();
    }

    public abstract void prepareVehicle(Vehicle vehicle);

    public abstract void crashVehicle(Vehicle vehicle);

    public abstract void goToParking(Vehicle vehicle);

    public abstract void goToLandFill(Vehicle vehicle);

    public abstract void goToControll(Vehicle vehicle);

    public abstract void gasStation(Vehicle vehicle);

    public abstract TypeOfVehicleState getState();
}
