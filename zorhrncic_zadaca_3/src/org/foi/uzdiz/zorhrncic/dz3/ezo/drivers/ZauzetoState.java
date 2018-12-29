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
public class ZauzetoState extends IDriverState {

    @Override
    public void zauzmiVozilo(Driver driver) {
        //todo vidi malo uvjete
        this.builderDirectior.addTextLineInReport("Vozač je već zauzet!!", true);

    }

    @Override
    public void postaniRaspoloziv(Driver driver) {
        driver.setState(driver.getRaspoloziv());
        this.builderDirectior.addTextLineInReport("Vozač " + driver.getName() + " postaje raspoloživ!!", true);
    }

    @Override
    public void idiNaGO(Driver driver) {
        driver.setState(driver.getGodisnji());
        this.builderDirectior.addTextLineInReport("Vozač " + driver.getName() + " ide na godišnji!!", true);
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
        return TypeOfDriverState.VOZI_KAMION ;
    }

}
