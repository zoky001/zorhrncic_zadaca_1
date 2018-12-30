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
    private IDriverState nedodjeljen;
    private IDriverState state;

    private String name;
    private Vehicle vehicle;

    public Driver(String name) {
        this.raspoloziv = new RaspolozivoState();
        this.zauzet = new ZauzetoState();
        this.bolovanje = new BolovanjeState();
        this.godisnji = new GodisnjiState();
        this.otkaz = new OtkazState();
        this.nedodjeljen = new NedodjeljenState();

        this.state = this.raspoloziv;

        this.name = name;
    }

    public void zauzmiVozilo(Vehicle vehicle) {
        //todo vidi malo uvjete
        this.vehicle = vehicle;
        postaviStarogVozacaRaspolozivim(vehicle);
        dodajNovogVozacaAkoNePostoji(vehicle);
        this.state.zauzmiVozilo(this);
    }

    private void dodajNovogVozacaAkoNePostoji(Vehicle vehicle) {
        try {
            if (!vehicle.getDrivers().contains(this)) {
                vehicle.getDrivers().add(this);
            }
        } catch (Exception e) {
        }
    }

    private void postaviStarogVozacaRaspolozivim(Vehicle vehicle) {
        try {
            for (Driver driver : vehicle.getDrivers()) {
                if (driver.getState() == TypeOfDriverState.VOZI_KAMION && driver != this) {
                    driver.postaniRaspoloziv();
                }
            }
        } catch (Exception e) {
        }

    }

    public void postaniNedodjeljen() {
        obrisiOvogVozacaSListeVozaca();
        this.state.postaniNedodjeljen(this);
        if (this.vehicle != null) {
            Driver d = findFreeDriver(this.vehicle);
            if (d != null) {
                d.zauzmiVozilo(this.vehicle);
            }
        }
        this.vehicle = null;
    }

    private void obrisiOvogVozacaSListeVozaca() {
        try {
            if (this.vehicle != null && this.vehicle.getDrivers().contains(this)) {
                this.vehicle.getDrivers().remove(this);
               // this.vehicle = null;
            }
        } catch (Exception e) {
        }
    }

    public void postaniRaspoloziv() {
        this.state.postaniRaspoloziv(this);
    }

    public void idiNaGO() {
        obrisiOvogVozacaSListeVozaca();
        this.state.idiNaGO(this);
        if (this.vehicle != null) {
            Driver d = findFreeDriver(this.vehicle);
            if (d != null) {
                d.zauzmiVozilo(this.vehicle);
            }
        }
        this.vehicle = null;
    }

    public void dajOtkaz() {
        obrisiOvogVozacaSListeVozaca();
        this.state.dajOtkaz(this);
        if (this.vehicle != null) {
            Driver d = findFreeDriver(this.vehicle);
            if (d != null) {
                d.zauzmiVozilo(this.vehicle);
            }
        }
        this.vehicle = null;

    }

    public void idiNaBolovanje() {
        obrisiOvogVozacaSListeVozaca();
        this.state.idiNaBolovanje(this);
        if (this.vehicle != null) {
            Driver d = findFreeDriver(this.vehicle);
            if (d != null) {
                d.zauzmiVozilo(this.vehicle);
            }
        }
        this.vehicle = null;

    }

    private Driver findFreeDriver(Vehicle v) {
        try {
            for (Driver driver : vehicle.getDrivers()) {
                if (driver.getState() == TypeOfDriverState.RASPOLOZIV && driver.getVehicle() == v) {
                    return driver;
                }
            }
        } catch (Exception e) {

        }
        return null;
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

    public IDriverState getNedodjeljen() {
        return nedodjeljen;
    }

}
