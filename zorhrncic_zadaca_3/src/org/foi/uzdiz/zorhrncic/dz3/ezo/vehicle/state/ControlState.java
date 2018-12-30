/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.state;

import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;

/**
 *
 * @author Zoran
 */
public class ControlState extends IVehicleState {

    @Override
    public void prepareVehicle(Vehicle vehicle) {
        vehicle.setState(vehicle.getReady());
        this.builderDirectior.addTextLineInReport("Vozilo " + vehicle.getName() + " je spremno za sakupljanje otpada!!", true);
    }

    @Override
    public void crashVehicle(Vehicle vehicle) {
        vehicle.setState(vehicle.getCrash());
        this.builderDirectior.addTextLineInReport("Vozilo " + vehicle.getName() + " je u kvaru!!", true);
    }

    @Override
    public void goToParking(Vehicle vehicle) {
        vehicle.setState(vehicle.getParking());
        this.builderDirectior.addTextLineInReport("Vozilo " + vehicle.getName() + " je na parkiralištu!!", true);
    }

    @Override
    public void goToLandFill(Vehicle vehicle) {
        vehicle.setState(vehicle.getLandfill());
        this.builderDirectior.addTextLineInReport("Vozilo " + vehicle.getName() + " je na odlagalištu!!", true);
    }

    @Override
    public void goToControll(Vehicle vehicle) {
        this.builderDirectior.addTextLineInReport("Vozilo je već na kontroli!!", true);

    }

    @Override
    public void gasStation(Vehicle vehicle) {
        vehicle.setState(vehicle.getGas_station());
        this.builderDirectior.addTextLineInReport("Vozilo " + vehicle.getName() + " je na punjenu goriva!!", true);
    }

    @Override
    public TypeOfVehicleState getState() {
        return TypeOfVehicleState.CONTROL;
    }

}
