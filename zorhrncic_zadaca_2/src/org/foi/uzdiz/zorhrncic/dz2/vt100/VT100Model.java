/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.vt100;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zoran
 */
public class VT100Model {

    private List<String> upperTerminaOutput;
    private List<String> lowerTerminaOutput;

    public VT100Model() {
        this.upperTerminaOutput = new ArrayList<>();
        this.lowerTerminaOutput = new ArrayList<>();
    }

    public List<String> getUpperTerminaOutput() {
        return upperTerminaOutput;
    }

    public void setUpperTerminaOutput(List<String> upperTerminaOutput) {
        this.upperTerminaOutput = upperTerminaOutput;
    }

    public List<String> getLowerTerminaOutput() {
        return lowerTerminaOutput;
    }

    public void setLowerTerminaOutput(List<String> lowerTerminaOutput) {
        this.lowerTerminaOutput = lowerTerminaOutput;
    }

    public void addUpperTerminaOutputLine(String line) {
        this.upperTerminaOutput.add(line);
    }

    public void addLowerTerminaOutputLine(String line) {
        this.lowerTerminaOutput.add(line);
    }

}
