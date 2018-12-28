/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.composite;

/**
 *
 * @author Zoran
 */
public interface IPlace {

    //Prints the graphic.
    public IPlace getParrent();

    public void setParrent(IPlace place);

    public void printDataAboutGeneratedWaste();

    public float getTotalAmountOfGlassWaste();

    public float getTotalAmountOfMetalWaste();

    public float getTotalAmountOfPaperWaste();

    public float getTotalAmountOfBioWaste();

    public float getTotalAmountOfMixedWaste();

}
