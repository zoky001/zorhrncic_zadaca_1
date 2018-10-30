/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.ezo;

import java.util.List;
import org.foi.uzdiz.zorhrncic.dz1.users.User;

/**
 *
 * @author Zoran
 */
public abstract class Spremnik {

    private static int idIncrement = 0;
    int id;
    String kindOfWaste;
    int numberOfSmall;
    int numberOfMedium;
    int numberOfLarge;
    int capacity;

    public Spremnik() {
    }

    public Spremnik(Spremnik target) {
        if (target != null) {
            this.id = idIncrement++;
            this.kindOfWaste = target.kindOfWaste;
            this.numberOfSmall = target.numberOfSmall;
            this.numberOfMedium = target.numberOfMedium;
            this.numberOfLarge = target.numberOfLarge;
            this.capacity = target.capacity;
        }

    }

    public abstract Spremnik clone();

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof Spremnik)) {
            return false;
        }
        Spremnik spremnik = (Spremnik) object2;
        return spremnik.id == id && spremnik.kindOfWaste.equalsIgnoreCase(kindOfWaste) && spremnik.numberOfSmall == numberOfSmall && spremnik.numberOfMedium == numberOfMedium && spremnik.numberOfLarge == numberOfLarge;

    }

    public static void printArray(List<Spremnik> sviTipoviSpremnika) {
        sviTipoviSpremnika.forEach((k) -> {

            System.out.print("TIP: ");

            if (k instanceof Kanta) {
                System.out.println(" Kanta");
            } else if (k instanceof Kontejner) {
                System.out.println(" Kontejner");
            }
            System.out.println("Name: " + k.getKindOfWaste());
            System.out.println("Broj kila: " + k.getCapacity());
            System.out.println("Broj malih: " + k.getNumberOfSmall());
            System.out.println("Broj srednjih: " + k.getNumberOfMedium());
            System.out.println("Broj velikih: " + k.getNumberOfLarge());
            System.out.println("------------------------------------------------------------------------------------------------------------");

        });
    }

    public static int getIdIncrement() {
        return idIncrement;
    }

    public static void setIdIncrement(int idIncrement) {
        Spremnik.idIncrement = idIncrement;
    }

    public String getKindOfWaste() {
        return kindOfWaste;
    }

    public void setKindOfWaste(String kindOfWaste) {
        this.kindOfWaste = kindOfWaste;
    }

    public int getNumberOfSmall() {
        return numberOfSmall;
    }

    public void setNumberOfSmall(int numberOfSmall) {
        this.numberOfSmall = numberOfSmall;
    }

    public int getNumberOfMedium() {
        return numberOfMedium;
    }

    public void setNumberOfMedium(int numberOfMedium) {
        this.numberOfMedium = numberOfMedium;
    }

    public int getNumberOfLarge() {
        return numberOfLarge;
    }

    public void setNumberOfLarge(int numberOfLarge) {
        this.numberOfLarge = numberOfLarge;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
