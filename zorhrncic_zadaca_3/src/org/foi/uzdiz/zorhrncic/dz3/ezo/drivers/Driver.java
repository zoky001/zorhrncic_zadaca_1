/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo.drivers;

import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypeOfDriverState;

/**
 *
 * @author Zoran
 */
public class Driver {

    private IDriverState raspoloziv;
    private IDriverState zauzet;
    private IDriverState bolovanje;
    private IDriverState godisnji;
    private IDriverState otkaz;
    private IDriverState state;

    private String name;
    private Vehicle vehicle;

    public Driver(String name) {
        this.raspoloziv = new RaspolozivoState();
        this.zauzet = new ZauzetoState();
        this.bolovanje = new BolovanjeState();
        this.godisnji = new GodisnjiState();
        this.otkaz = new OtkazState();

        this.state = this.raspoloziv;

        this.name = name;
    }

    public void zauzmiVozilo(Vehicle vehicle) {
        //todo vidi malo uvjete
        this.vehicle = vehicle;
        this.state.zauzmiVozilo(this);
    }

    public void postaniRaspoloziv() {
        this.state.postaniRaspoloziv(this);
    }

    public void idiNaGO() {
        this.state.idiNaGO(this);
    }

    public void dajOtkaz() {
        this.state.dajOtkaz(this);
    }

    public void idiNaBolovanje() {
        this.state.idiNaBolovanje(this);
    }

    public TypeOfDriverState getState() {
        return state.getState();
    }

    public String getName() {
        return name;
    }

    public void setState(IDriverState state) {
        this.state = state;
    }

    public IDriverState getRaspoloziv() {
        return raspoloziv;
    }

    public IDriverState getZauzet() {
        return zauzet;
    }

    public IDriverState getBolovanje() {
        return bolovanje;
    }

    public IDriverState getGodisnji() {
        return godisnji;
    }

    public IDriverState getOtkaz() {
        return otkaz;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
