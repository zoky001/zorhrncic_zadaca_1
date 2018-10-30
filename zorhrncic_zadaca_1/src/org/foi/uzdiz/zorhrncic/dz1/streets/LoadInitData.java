/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.streets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Kanta;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Kontejner;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz1.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz1.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz1.users.LargeUser;
import org.foi.uzdiz.zorhrncic.dz1.users.MediumUser;
import org.foi.uzdiz.zorhrncic.dz1.users.SmallUser;
import org.foi.uzdiz.zorhrncic.dz1.users.User;

/**
 *
 * @author Zoran
 */
public class LoadInitData {

    private List<Street> streets;
    private List<Spremnik> sviTipoviSpremnika;

    public LoadInitData() {
        streets = new ArrayList<Street>();
        sviTipoviSpremnika = new ArrayList<Spremnik>();

        // loadVehiclesPrivate((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.vozila));
        ucitajSpremnikePrivate((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.spremnici));
        loadStreetsPrivate((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.ulice));
        loadUsersForStreetsPrivate();

        Spremnik.printArray(sviTipoviSpremnika);
        streets.forEach((k) -> {

            k.print();

        });
    }

    private void ucitajSpremnikePrivate(String path) {
//TODO treba validirati
        BufferedReader br = null;
        String line = " ";
        String cvsSplitBy = ";";
        File file = new File(path);
        Spremnik spremnik;

        try {

            br = new BufferedReader(new FileReader(file));
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                if (lineNo++ != 0) {
                    // use comma as separator
                    String[] data = line.split(cvsSplitBy);

                    if (data.length != 6) {
                        continue;
                    }
                    System.out.println(line);
                    System.out.println(data.length);
                    if (((String) data[1]).equalsIgnoreCase(Constants.KANTA)) {
                        spremnik = new Kanta();
                        spremnik.setKindOfWaste(data[0]);
                        spremnik.setNumberOfSmall(Integer.valueOf(data[2]));
                        spremnik.setNumberOfMedium(Integer.valueOf(data[3]));
                        spremnik.setNumberOfLarge(Integer.valueOf(data[4]));
                        spremnik.setCapacity(Integer.valueOf(data[5]));

                        sviTipoviSpremnika.add(spremnik);

                    } else if (((String) data[1]).equalsIgnoreCase(Constants.KONTEJNER)) {
                        spremnik = new Kontejner();
                        spremnik.setKindOfWaste(data[0]);
                        spremnik.setNumberOfSmall(Integer.valueOf(data[2]));
                        spremnik.setNumberOfMedium(Integer.valueOf(data[3]));
                        spremnik.setNumberOfLarge(Integer.valueOf(data[4]));
                        spremnik.setCapacity(Integer.valueOf(data[5]));

                        sviTipoviSpremnika.add(spremnik);
                    }

                }

            }

            //        streets.forEach((k)
            //                 -> System.out.println("Key : " + k.getName() + " Value : ")
            //        );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void loadUsersForStreetsPrivate() {

        try {

            streets.forEach((k) -> {

                System.out.println("Key : " + k.getName() + " Value : ");
                setNumberOfUsersInStreet(k);
                setUsersInStreet(k);

            }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUsersInStreet(Street street) {

        try {

            int numberOfSmall = street.getNumberOfSmall();
            int numberOfMedium = street.getNumberOfMedium();
            int numberOfLarge = street.getNumberOfLarge();
            User user;
            for (int i = 0; i < numberOfSmall; i++) {
                user = new SmallUser();
                street.addUser(user);

            }
            for (int i = 0; i < numberOfMedium; i++) {
                user = new MediumUser();
                street.addUser(user);

            }
            for (int i = 0; i < numberOfLarge; i++) {
                user = new LargeUser();
                street.addUser(user);

            }

            //System.out.println("UKUPNO: " + street.getNumberOfPlaces() + " veliki: " + numberOfLarge + " srednji: " + numberOfMedium + " mali: " + numberOfSmall);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setNumberOfUsersInStreet(Street street) {

        try {

            float koef = (float) street.getShareOfSmall() / (float) 100;
            int numberOfSmall = (int) (koef * street.getNumberOfPlaces());
            koef = (float) street.getShareOfMedium() / (float) 100;
            int numberOfMedium = (int) (koef * street.getNumberOfPlaces());
            koef = (float) street.getShareOfLarge() / (float) 100;
            int numberOfLarge = (int) (koef * street.getNumberOfPlaces());

            while (numberOfSmall + numberOfMedium + numberOfLarge != street.getNumberOfPlaces()) {
                if (numberOfSmall + numberOfMedium + numberOfLarge < street.getNumberOfPlaces()) {
                    numberOfSmall++;
                }
                if (numberOfSmall + numberOfMedium + numberOfLarge > street.getNumberOfPlaces()) {
                    numberOfSmall--;
                }
            }
            street.setNumberOfSmall(numberOfSmall);
            street.setNumberOfMedium(numberOfMedium);
            street.setNumberOfLarge(numberOfLarge);

            //System.out.println("UKUPNO: " + street.getNumberOfPlaces() + " veliki: " + numberOfLarge + " srednji: " + numberOfMedium + " mali: " + numberOfSmall);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadStreetsPrivate(String path) {

        BufferedReader br = null;
        String line = " ";
        String cvsSplitBy = ";";
        File file = new File(path);
        Street street;

        try {

            br = new BufferedReader(new FileReader(file));
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                if (lineNo++ != 0) {
                    // use comma as separator
                    String[] data = line.split(cvsSplitBy);
                    System.out.println(line);
                    street = new Street(data[0], Integer.valueOf(data[1]), Integer.valueOf(data[2]), Integer.valueOf(data[3]), Integer.valueOf(data[4]));
                    streets.add(street);

                    //        System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
                    //    System.out.println(line);
                }

            }

            //        streets.forEach((k)
            //                 -> System.out.println("Key : " + k.getName() + " Value : ")
            //        );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
