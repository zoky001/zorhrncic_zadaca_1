/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.composite;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zoran
 */
public class CompositePlace implements Place {

    //Collection of child places.
    private ArrayList<Place> mChildPlaces = new ArrayList<Place>();
    private Place parrent;

    private String id;// = new ArrayList<Place>();
    private String name;// = new ArrayList<Place>();
    private List<String> parts = new ArrayList<String>();

    public CompositePlace(String id, String name) {
        this.id = id;
        this.name = name;
    }

//Prints the place.
    @Override
    public void printDataAboutGeneratedWaste() {
        System.out.println("############################################################################################################");
        System.out.println("============================================================================================================");

        if (getParrent() != null) {
            System.out.println("############################################################################################################");
            System.out.println("|                               IME NAD-PODRUČJA: " + ((CompositePlace) getParrent()).getName() + "         ");

        }

        System.out.println("############################################################################################################");
        System.out.println("|                                   IME PODRUČJA: " + name + "                                              ");
        System.out.println("############################################################################################################");
        System.out.println("|                   Količina otpada staklo:     |  " + getTotalAmountOfGlassWaste());
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.println("|                   Količina otpada papir:      |  " + getTotalAmountOfPaperWaste());
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.println("|                   Količina otpada metal:      |  " + getTotalAmountOfMetalWaste());
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.println("|                   Količina otpada bio:        |  " + getTotalAmountOfBioWaste());
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.println("|                   Količina otpada mixed:      |  " + getTotalAmountOfMixedWaste());
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.println("############################################################################################################");

        for (Place graphic : mChildPlaces) {
            graphic.printDataAboutGeneratedWaste();
        }
    }

//Adds the palce to the composition.
    public void add(Place place) {
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

    public ArrayList<Place> getmChildPlaces() {
        return mChildPlaces;
    }

    public void setmChildPlaces(ArrayList<Place> mChildPlaces) {
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
    public Place getParrent() {
        return parrent;
    }

    @Override
    public void setParrent(Place parrent) {
        this.parrent = parrent;
    }

}
