/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.composite;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz2.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz2.singleton.CommonDataSingleton;

/**
 *
 * @author Zoran
 */
public class CompositePlace implements IPlace {

    //Collection of child places.
    private ArrayList<IPlace> mChildPlaces = new ArrayList<IPlace>();
    private IPlace parrent;

    private String id;// = new ArrayList<Place>();
    private String name;// = new ArrayList<Place>();
    private List<String> parts = new ArrayList<String>();
    protected final ReportBuilderDirector builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();

    public CompositePlace(String id, String name) {
        this.id = id;
        this.name = name;
    }

//Prints the place.
    @Override
    public void printDataAboutGeneratedWaste() {

        this.builderDirector.addTextLineInReport("############################################################################################################", false);
        this.builderDirector.addTextLineInReport("============================================================================================================", false);

        if (getParrent() != null) {
            this.builderDirector.addTextLineInReport("############################################################################################################", false);
            this.builderDirector.addTextLineInReport("|                               IME NAD-PODRUČJA: " + ((CompositePlace) getParrent()).getName() + "         ", false);

        }

        this.builderDirector.addTextLineInReport("############################################################################################################", false);
        this.builderDirector.addTextLineInReport("|                                   IME PODRUČJA: " + name + "                                              ", false);
        this.builderDirector.addTextLineInReport("############################################################################################################", false);
        this.builderDirector.addTextLineInReport("|                   Količina otpada staklo:     |  " + getTotalAmountOfGlassWaste(), false);
        this.builderDirector.addTextLineInReport("------------------------------------------------------------------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("|                   Količina otpada papir:      |  " + getTotalAmountOfPaperWaste(), false);
        this.builderDirector.addTextLineInReport("------------------------------------------------------------------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("|                   Količina otpada metal:      |  " + getTotalAmountOfMetalWaste(), false);
        this.builderDirector.addTextLineInReport("------------------------------------------------------------------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("|                   Količina otpada bio:        |  " + getTotalAmountOfBioWaste(), false);
        this.builderDirector.addTextLineInReport("------------------------------------------------------------------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("|                   Količina otpada mixed:      |  " + getTotalAmountOfMixedWaste(), false);
        this.builderDirector.addTextLineInReport("------------------------------------------------------------------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("############################################################################################################", false);

        for (IPlace child : mChildPlaces) {
            child.printDataAboutGeneratedWaste();
        }
    }

//Adds the palce to the composition.
    public void add(IPlace place) {
        mChildPlaces.add(place);
    }

    public List<String> getParts() {
        return parts;
    }

    public void setParts(List<String> parts) {
        this.parts = parts;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<IPlace> getmChildPlaces() {
        return mChildPlaces;
    }

    public void setmChildPlaces(ArrayList<IPlace> mChildPlaces) {
        this.mChildPlaces = mChildPlaces;
    }

    @Override
    public float getTotalAmountOfGlassWaste() {
        float sum = 0;

        for (int i = 0; i < mChildPlaces.size(); i++) {

            sum = sum + mChildPlaces.get(i).getTotalAmountOfGlassWaste();

        }

        return sum;
    }

    @Override
    public float getTotalAmountOfMetalWaste() {
        float sum = 0;

        for (int i = 0; i < mChildPlaces.size(); i++) {

            sum = sum + mChildPlaces.get(i).getTotalAmountOfMetalWaste();

        }

        return sum;
    }

    @Override
    public float getTotalAmountOfPaperWaste() {
        float sum = 0;

        for (int i = 0; i < mChildPlaces.size(); i++) {

            sum = sum + mChildPlaces.get(i).getTotalAmountOfPaperWaste();

        }

        return sum;
    }

    @Override
    public float getTotalAmountOfBioWaste() {
        float sum = 0;

        for (int i = 0; i < mChildPlaces.size(); i++) {

            sum = sum + mChildPlaces.get(i).getTotalAmountOfBioWaste();

        }

        return sum;
    }

    @Override
    public float getTotalAmountOfMixedWaste() {
        float sum = 0;

        for (int i = 0; i < mChildPlaces.size(); i++) {

            sum = sum + mChildPlaces.get(i).getTotalAmountOfMixedWaste();

        }

        return sum;
    }

    @Override
    public IPlace getParrent() {
        return parrent;
    }

    @Override
    public void setParrent(IPlace parrent) {
        this.parrent = parrent;
    }

}
