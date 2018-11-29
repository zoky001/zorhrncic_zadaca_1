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

}
