/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.streets;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz1.users.LargeUser;
import org.foi.uzdiz.zorhrncic.dz1.users.MediumUser;
import org.foi.uzdiz.zorhrncic.dz1.users.SmallUser;
import org.foi.uzdiz.zorhrncic.dz1.users.User;

/**
 *
 * @author Zoran
 */
public class Street {

    private String name;
    private int numberOfPlaces;
    private int shareOfSmall;
    private int shareOfMedium;
    private int shareOfLarge;

    private int numberOfSmall;
    private int numberOfMedium;
    private int numberOfLarge;

    private List<User> usersList = new ArrayList<User>();
    private List<Spremnik> spremnikList = new ArrayList<Spremnik>();

    public Street(String name, int numberOfPlaces, int shareOfSmall, int shareOfMedium, int shareOfLarge) {
        this.name = name;
        this.numberOfPlaces = numberOfPlaces;
        this.shareOfSmall = shareOfSmall;
        this.shareOfMedium = shareOfMedium;
        this.shareOfLarge = shareOfLarge;
    }

    public void print() {

        System.out.println("Name: " + name);
        System.out.println("Broj mjesta: " + numberOfPlaces);
        System.out.println("Broj malih: " + numberOfSmall);
        System.out.println("Broj srednjih: " + numberOfMedium);
        System.out.println("Broj velikih: " + numberOfLarge);

        System.out.println("Korisnici broj: " + usersList.size());

        usersList.forEach((u) -> {

            System.out.print("ID: " + u.getId());

            if (u instanceof SmallUser) {
                System.out.println("      Mali korisnik");
            } else if (u instanceof MediumUser) {
                System.out.println("      Srednji korisnik");
            } else if (u instanceof LargeUser) {
                System.out.println("      Veliki korisnik");
            }

        });
        System.out.println("------------------------------------------------------------------------------------------------------------");
    }

    public void addUser(User user) {
        this.usersList.add(user);// = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public int getShareOfSmall() {
        return shareOfSmall;
    }

    public void setShareOfSmall(int shareOfSmall) {
        this.shareOfSmall = shareOfSmall;
    }

    public int getShareOfLarge() {
        return shareOfLarge;
    }

    public void setShareOfLarge(int shareOfLarge) {
        this.shareOfLarge = shareOfLarge;
    }

    public int getShareOfMedium() {
        return shareOfMedium;
    }

    public void setShareOfMedium(int shareOfMedium) {
        this.shareOfMedium = shareOfMedium;
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

}
