/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo.drivers;

import org.foi.uzdiz.zorhrncic.dz3.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypeOfDriverState;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;

/**
 *
 * @author Zoran
 */
public abstract class IDriverState {

    protected final ReportBuilderDirector builderDirectior;

    public IDriverState() {
        this.builderDirectior = CommonDataSingleton.getInstance().getReportBuilderDirector();
    }

    
    
    public abstract void zauzmiVozilo(Driver driver);

    public abstract void postaniRaspoloziv(Driver driver);

    public abstract void idiNaGO(Driver driver);

    public abstract void dajOtkaz(Driver driver);

    public abstract void idiNaBolovanje(Driver driver);

    public abstract TypeOfDriverState getState();
}
