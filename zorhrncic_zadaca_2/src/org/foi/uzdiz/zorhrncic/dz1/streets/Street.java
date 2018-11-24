/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.streets;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Kontejner;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz1.users.BigUser;
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
        System.out.println("------------------------------------------------");
        System.out.println("Količina otpada staklo: " + getTotalAmountOfGlassWaste());
        System.out.println("------------------------------------------------");
        System.out.println("Količina otpada papir: " + getTotalAmountOfPaperWaste());
        System.out.println("------------------------------------------------");
        System.out.println("Količina otpada metal: " + getTotalAmountOfMetalWaste());
        System.out.println("------------------------------------------------");
        System.out.println("Količina otpada bio: " + getTotalAmountOfBioWaste());
        System.out.println("------------------------------------------------");
        System.out.println("Količina otpada mixed: " + getTotalAmountOfMixedWaste());
        System.out.println("------------------------------------------------");

        System.out.println("Korisnici broj: " + usersList.size());

        usersList.forEach((u) -> {

            System.out.print("ID: " + u.getId());

            if (u instanceof SmallUser) {
              //  System.out.println("      Mali korisnik");
            } else if (u instanceof MediumUser) {
              //  System.out.println("      Srednji korisnik");
            } else if (u instanceof BigUser) {
             //   System.out.println("      Veliki korisnik");
            }

            System.out.println("      KONTEJNERI: ");

            u.getSpremnikList().forEach((conte) -> {

                System.out.print("      id: " + conte.getId());
                System.out.println("      name: " + conte.getKindOfWaste().name());
                System.out.print("      typeUser: " + conte.getTypesOfUser().name());
                System.out.print("      broj korisnika: " + conte.getUsersList().size());

                System.out.print("      broj malih: " + conte.getNumberOfSmall());
                System.out.print("      broj srednjih: " + conte.getNumberOfMedium());
                System.out.print("      broj veliki: " + conte.getNumberOfLarge());
                System.out.print("      Dijeli s korisnicima: [ ");

                conte.getUsersList().forEach((ids) -> {

                    System.out.print("      ," + ids.getId());
                });

                System.out.println(" ]");
                System.out.println("*****************************************************");

            });

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

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public List<Spremnik> getSpremnikList() {
        return spremnikList;
    }

    public void setSpremnikList(List<Spremnik> spremnikList) {
        this.spremnikList = spremnikList;
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

    private float getTotalAmountOfGlassWaste() {

        float sum = 0;

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getGlassWaste() != null) {
                sum = sum + usersList.get(i).getGlassWaste().getAmount();
            }

        }

        return sum;

    }

    private float getTotalAmountOfMetalWaste() {

        float sum = 0;

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getMetalWaste() != null) {
                sum = sum + usersList.get(i).getMetalWaste().getAmount();
            }

        }

        return sum;

    }

    private float getTotalAmountOfPaperWaste() {

        float sum = 0;

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getPaperWaste() != null) {
                sum = sum + usersList.get(i).getPaperWaste().getAmount();
            }

        }

        return sum;

    }

    private float getTotalAmountOfBioWaste() {

        float sum = 0;

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getBioWaste() != null) {
                sum = sum + usersList.get(i).getBioWaste().getAmount();
            }

        }

        return sum;

    }

    private float getTotalAmountOfMixedWaste() {

        float sum = 0;

        for (int i = 0; i < usersList.size(); i++) {

            if (usersList.get(i).getMixedWaste() != null) {
                sum = sum + usersList.get(i).getMixedWaste().getAmount();
            }

        }

        return sum;

    }

}
