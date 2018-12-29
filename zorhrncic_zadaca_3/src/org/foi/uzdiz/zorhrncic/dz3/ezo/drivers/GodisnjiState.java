/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo.drivers;

import org.foi.uzdiz.zorhrncic.dz3.shared.TypeOfDriverState;

/**
 *
 * @author Zoran
 */
public class GodisnjiState extends IDriverState {

    @Override
    public void zauzmiVozilo(Driver driver) {
        //todo vidi malo uvjete
        driver.setState(driver.getZauzet());
        this.builderDirectior.addTextLineInReport("Vozač " + driver.getName() + " zauzima vozilo!!", true);
    }

    @Override
    public void postaniRaspoloziv(Driver driver) {
        driver.setState(driver.getRaspoloziv());
        this.builderDirectior.addTextLineInReport("Vozač " + driver.getName() + " postaje raspoloživ!!", true);
    }

    @Override
    public void idiNaGO(Driver driver) {
        this.builderDirectior.addTextLineInReport("Vozač je već na godišnjem!!", true);

    }

    @Override
    public void dajOtkaz(Driver driver) {
        driver.setState(driver.getOtkaz());
        this.builderDirectior.addTextLineInReport("Vozač " + driver.getName() + " daje otkaz!!", true);

    }

    @Override
    public void idiNaBolovanje(Driver driver) {
        driver.setState(driver.getBolovanje());
        this.builderDirectior.addTextLineInReport("Vozač " + driver.getName() + " ide na bolovanje!!", true);
    }

    @Override
    public TypeOfDriverState getState() {
        return TypeOfDriverState.GO;
    }

}