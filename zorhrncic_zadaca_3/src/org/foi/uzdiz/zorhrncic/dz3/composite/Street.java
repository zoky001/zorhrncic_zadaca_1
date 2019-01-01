/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.composite;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz3.ezo.ChoosePickupDirection;
import org.foi.uzdiz.zorhrncic.dz3.ezo.Kontejner;
import org.foi.uzdiz.zorhrncic.dz3.ezo.PickUpDirection;
import org.foi.uzdiz.zorhrncic.dz3.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz3.users.BigUser;
import org.foi.uzdiz.zorhrncic.dz3.users.MediumUser;
import org.foi.uzdiz.zorhrncic.dz3.users.SmallUser;
import org.foi.uzdiz.zorhrncic.dz3.users.User;

/**
 *
 * @author Zoran
 */
public class Street extends ChoosePickupDirection implements IPlace {

    private IPlace area;
    private String id;

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
    protected final ReportBuilderDirector builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();

    public Street(String id, String name, int numberOfPlaces, int shareOfSmall, int shareOfMedium, int shareOfLarge) {
        this.id = id;
        this.name = name;
        this.numberOfPlaces = numberOfPlaces;
        this.shareOfSmall = shareOfSmall;
        this.shareOfMedium = shareOfMedium;
        this.shareOfLarge = shareOfLarge;
    }

    @Override
    public void printDataAboutGeneratedWaste() {
        this.builderDirector.addTextLineInReport("------------------------------------------------------------------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("           ------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("           |   IME NAD-PODRUČJA: " + ((CompositePlace) getParrent()).getName() + "          ", false);
        this.builderDirector.addTextLineInReport("           ------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("           |          IME ULICE: " + name + "                 ", false);
        this.builderDirector.addTextLineInReport("           ------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("           |   Količina otpada staklo:     |  " + getTotalAmountOfGlassWaste(), false);
        this.builderDirector.addTextLineInReport("           ------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("           |   Količina otpada papir:      |  " + getTotalAmountOfPaperWaste(), false);
        this.builderDirector.addTextLineInReport("           ------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("           |   Količina otpada metal:      |  " + getTotalAmountOfMetalWaste(), false);
        this.builderDirector.addTextLineInReport("           ------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("           |   Količina otpada bio:        |  " + getTotalAmountOfBioWaste(), false);
        this.builderDirector.addTextLineInReport("           ------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("           |   Količina otpada mixed:      |  " + getTotalAmountOfMixedWaste(), false);
        this.builderDirector.addTextLineInReport("           ------------------------------------------------", false);
        this.builderDirector.addTextLineInReport("------------------------------------------------------------------------------------------------------------", false);
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

    @Override
    public float getTotalAmountOfGlassWaste() {

        float sum = 0;

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getGlassWaste() != null) {
                sum = sum + usersList.get(i).getGlassWaste().getAmount();
            }

        }

        return sum;

    }

    @Override
    public float getTotalAmountOfMetalWaste() {

        float sum = 0;

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getMetalWaste() != null) {
                sum = sum + usersList.get(i).getMetalWaste().getAmount();
            }

        }

        return sum;

    }

    @Override
    public float getTotalAmountOfPaperWaste() {

        float sum = 0;

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getPaperWaste() != null) {
                sum = sum + usersList.get(i).getPaperWaste().getAmount();
            }

        }

        return sum;

    }

    @Override
    public float getTotalAmountOfBioWaste() {

        float sum = 0;

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getBioWaste() != null) {
                sum = sum + usersList.get(i).getBioWaste().getAmount();
            }

        }

        return sum;

    }

    @Override
    public float getTotalAmountOfMixedWaste() {

        float sum = 0;

        for (int i = 0; i < usersList.size(); i++) {

            if (usersList.get(i).getMixedWaste() != null) {
                sum = sum + usersList.get(i).getMixedWaste().getAmount();
            }

        }

        return sum;

    }

    public IPlace getArea() {
        return area;
    }

    public String getId() {
        return id;
    }

    @Override
    public IPlace getParrent() {
        return area;
    }

    @Override
    public void setParrent(IPlace area) {
        this.area = area;
    }

    @Override
    protected boolean isExistAscendingVehicle() {
        try {
            if (getAscendingVehicle() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    protected boolean isExistDescendingVehicle() {
        try {
            if (getDescendingVehicle() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    protected boolean deleteFromWaitingList(Vehicle vehicle) {
        try {
            if (getWaitingWehicleList().contains(vehicle)) {
                getWaitingWehicleList().remove(vehicle);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    protected boolean isThisVehicleInStreet(Vehicle vehicle) {

        try {
            if (getAscendingVehicle() != null && getAscendingVehicle() == vehicle) {
                return true;
            } else if (getDescendingVehicle() != null && getDescendingVehicle() == vehicle) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected PickUpDirection getVehicleDirection(Vehicle vehicle) {
        try {
            if (isThisVehicleInStreet(vehicle)) {
                if (getAscendingVehicle() == vehicle) {
                    return PickUpDirection.ASCENDING;
                } else if (getDescendingVehicle() == vehicle) {
                    return PickUpDirection.DESCENDING;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;

    }

}
