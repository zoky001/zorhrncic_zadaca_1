/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.streets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Kanta;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Kontejner;
import org.foi.uzdiz.zorhrncic.dz1.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz1.log.Report;
import org.foi.uzdiz.zorhrncic.dz1.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz1.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfUser;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfVehicleEngine;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz1.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz1.users.LargeUser;
import org.foi.uzdiz.zorhrncic.dz1.users.MediumUser;
import org.foi.uzdiz.zorhrncic.dz1.users.SmallUser;
import org.foi.uzdiz.zorhrncic.dz1.users.User;
import org.foi.uzdiz.zorhrncic.dz1.waste.BioWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.GlassWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.MetalWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.MixedWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.PaperWaste;

/**
 *
 * @author Zoran
 */
public class LoadInitData {

    private List<Street> streets;
    private List<Spremnik> sviTipoviSpremnika;
    private List<Vehicle> allVehicles;
    private final ReportBuilderDirector builderDirector;
    private Report report;

    public LoadInitData() {
        streets = new ArrayList<Street>();
        sviTipoviSpremnika = new ArrayList<Spremnik>();
        allVehicles = new ArrayList<>();
        builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();
    }

    public void loadData() {


        ucitajSpremnikePrivate((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.spremnici));
        ucitajVehiclePrivate((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.vozila));
        loadStreetsPrivate((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.ulice));
        loadUsersForStreetsPrivate();
        assignSpremnikToUsersPrivate();
        genrateWasteForUsersPrivate();
        calculateTotalAmountOfUsersWasteInEveryStreet();
        //  report.print();
        popuniSpremnikeOtpadom();
        calculateTotalAmountOfWasteInEveryStreet();
        //  report.print();

        streets.forEach((k) -> {

            //     k.print();
         //   Spremnik.printArray(k.getSpremnikList());

        });

    }

    private void calculateTotalAmountOfUsersWasteInEveryStreet() {
        Spremnik spremnik;
        User user;
        float totalAmountGlass;
        float totalAmounPaper;
        float totalAmounMetal;
        float totalAmounBio;
        float totalAmounMixed;

        float totalAmounAll = 0;

        float totalAmountGlassAll = 0;
        float totalAmounPaperAll = 0;
        float totalAmounMetalAll = 0;
        float totalAmounBioAll = 0;
        float totalAmounMixedAll = 0;

        this.builderDirector.addTitleInReport("Popis ukupne količine otpada koji generiraju korisnici po ulicama", false);


        for (int i = 0; i < streets.size(); i++) {

            totalAmountGlass = 0;
            totalAmounPaper = 0;
            totalAmounMetal = 0;
            totalAmounBio = 0;
            totalAmounMixed = 0;

            for (int j = 0; j < streets.get(i).getUsersList().size(); j++) {
                user = streets.get(i).getUsersList().get(j);
                totalAmountGlass = totalAmountGlass + user.getGlassWaste().getAmount();
                totalAmounPaper = totalAmounPaper + user.getPaperWaste().getAmount();
                totalAmounMetal = totalAmounMetal + user.getMetalWaste().getAmount();

                totalAmounBio = totalAmounBio + user.getBioWaste().getAmount();
                totalAmounMixed = totalAmounMixed + user.getMixedWaste().getAmount();
            }
            totalAmountGlassAll = totalAmountGlassAll + totalAmountGlass;
            totalAmounPaperAll = totalAmounPaperAll + totalAmounPaper;
            totalAmounMetalAll = totalAmounMetalAll + totalAmounMetal;
            totalAmounBioAll = totalAmounBioAll + totalAmounBio;
            totalAmounMixedAll = totalAmounMixedAll + totalAmounMixed;

            totalAmounAll = totalAmounAll + totalAmountGlass + totalAmounPaper + totalAmounMetal + totalAmounBio + totalAmounMixed;

            this.builderDirector.addDividerLineInReport();
            this.builderDirector.addTextLineInReport("Naziv ulice:      " + streets.get(i).getName(), false);
            this.builderDirector.addDividerLineInReport();
            this.builderDirector.addTextLineInReport("Staklo:           " + totalAmountGlass, false);
            this.builderDirector.addTextLineInReport("Papir:            " + totalAmounPaper, false);
            this.builderDirector.addTextLineInReport("Metal:            " + totalAmounMetal, false);
            this.builderDirector.addTextLineInReport("Bio:              " + totalAmounBio, false);
            this.builderDirector.addTextLineInReport("Mješano:          " + totalAmounMixed, false);
            this.builderDirector.addDividerLineInReport();
            report = this.builderDirector.addEmptyLineInReport();

        }

        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addTitleInReport("Popis ukupne količine otpada koji generiraju korisnici po ulicama", false);

        this.builderDirector.addDividerLineInReport();
        this.builderDirector.addTextLineInReport("Staklo:           " + totalAmountGlassAll, false);
        this.builderDirector.addTextLineInReport("Papir:            " + totalAmounPaperAll, false);
        this.builderDirector.addTextLineInReport("Metal:            " + totalAmounMetalAll, false);
        this.builderDirector.addTextLineInReport("Bio:              " + totalAmounBioAll, false);
        this.builderDirector.addTextLineInReport("Mješano:          " + totalAmounMixedAll, false);
        this.builderDirector.addDividerLineInReport();
        this.builderDirector.addTextLineInReport("UKUPNO:           " + totalAmounAll, false);
        this.builderDirector.addDividerLineInReport();

        this.builderDirector.addTitleInReport("Popis ukupne količine otpada koji generiraju korisnici po ulicama", false);
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();

    }

    private void calculateTotalAmountOfWasteInEveryStreet() {
        Spremnik spremnik;

        float totalAmountGlass;
        float totalAmounPaper;
        float totalAmounMetal;
        float totalAmounBio;
        float totalAmounMixed;

        float totalAmountGlassAll = 0;
        float totalAmounPaperAll = 0;
        float totalAmounMetalAll = 0;
        float totalAmounBioAll = 0;
        float totalAmounMixedAll = 0;
        //   this.builderDirector.addTitleInReport("Popis ukupne količine otpada u kantama po ulicama", false);
        for (int i = 0; i < streets.size(); i++) {

            totalAmountGlass = 0;
            totalAmounPaper = 0;
            totalAmounMetal = 0;
            totalAmounBio = 0;
            totalAmounMixed = 0;
            for (int j = 0; j < streets.get(i).getSpremnikList().size(); j++) {
                spremnik = streets.get(i).getSpremnikList().get(j);

                switch (spremnik.getKindOfWaste()) {
                    case STAKLO:
                        totalAmountGlass = totalAmountGlass + spremnik.getFilled();
                        break;
                    case PAPIR:
                        totalAmounPaper = totalAmounPaper + spremnik.getFilled();
                        break;
                    case METAL:
                        totalAmounMetal = totalAmounMetal + spremnik.getFilled();
                        break;
                    case BIO:
                        totalAmounBio = totalAmounBio + spremnik.getFilled();
                        break;
                    case MJESANO:
                        totalAmounMixed = totalAmounMixed + spremnik.getFilled();
                        break;

                }

            }
            totalAmountGlassAll = totalAmountGlassAll + totalAmountGlass;
            totalAmounPaperAll = totalAmounPaperAll + totalAmounPaper;
            totalAmounMetalAll = totalAmounMetalAll + totalAmounMetal;
            totalAmounBioAll = totalAmounBioAll + totalAmounBio;
            totalAmounMixedAll = totalAmounMixedAll + totalAmounMixed;


            /*    this.builderDirector.addDividerLineInReport();
            this.builderDirector.addTextLineInReport("Naziv ulice:      " + streets.get(i).getName(), false);
            this.builderDirector.addDividerLineInReport();
            this.builderDirector.addTextLineInReport("Staklo:           " + totalAmountGlass, false);
            this.builderDirector.addTextLineInReport("Papir:            " + totalAmounPaper, false);
            this.builderDirector.addTextLineInReport("Metal:            " + totalAmounMetal, false);
            this.builderDirector.addTextLineInReport("Bio:              " + totalAmounBio, false);
            this.builderDirector.addTextLineInReport("Mješano:          " + totalAmounMixed, false);
            this.builderDirector.addDividerLineInReport();
            report = this.builderDirector.addEmptyLineInReport();*/
        }

        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addTitleInReport("Popis ukupne količine otpada u spremnicima po ulicama", false);

        this.builderDirector.addDividerLineInReport();
        this.builderDirector.addTextLineInReport("Staklo:           " + totalAmountGlassAll, false);
        this.builderDirector.addTextLineInReport("Papir:            " + totalAmounPaperAll, false);
        this.builderDirector.addTextLineInReport("Metal:            " + totalAmounMetalAll, false);
        this.builderDirector.addTextLineInReport("Bio:              " + totalAmounBioAll, false);
        this.builderDirector.addTextLineInReport("Mješano:          " + totalAmounMixedAll, false);
        this.builderDirector.addDividerLineInReport();
        this.builderDirector.addTextLineInReport("UKUPNO:           "
                + (totalAmountGlassAll + totalAmounPaperAll + totalAmounMetalAll + totalAmounBioAll + totalAmounMixedAll),
                false);
        this.builderDirector.addDividerLineInReport();

        this.builderDirector.addTitleInReport("Popis ukupne količine otpada u spremnicima po ulicama", false);
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();
        this.builderDirector.addEmptyLineInReport();

    }

    private void popuniSpremnikeOtpadom() {

        streets.forEach(street -> {
            street.getUsersList().forEach(user -> {
                user.getSpremnikList().forEach(spremnik -> {

                    if (spremnik.getKindOfWaste().equals(TypesOfWaste.STAKLO)) {

                        popuniSpremnik(spremnik, user.getGlassWaste().getAmount());

                    } else if (spremnik.getKindOfWaste().equals(TypesOfWaste.PAPIR)) {

                        popuniSpremnik(spremnik, user.getPaperWaste().getAmount());

                    } else if (spremnik.getKindOfWaste().equals(TypesOfWaste.MJESANO)) {

                        popuniSpremnik(spremnik, user.getMixedWaste().getAmount());

                    } else if (spremnik.getKindOfWaste().equals(TypesOfWaste.METAL)) {

                        popuniSpremnik(spremnik, user.getMetalWaste().getAmount());

                    } else if (spremnik.getKindOfWaste().equals(TypesOfWaste.BIO)) {

                        popuniSpremnik(spremnik, user.getBioWaste().getAmount());

                    }

                });

            });

        });

    }

    private void popuniSpremnik(Spremnik spremnik, float amount) {
        if (amount > spremnik.getCapacity()) {
            //todo 
            System.out.println("Nema mjesta u kanti.");
            spremnik.addWaste(spremnik.getCapacity());
        } else {
            spremnik.addWaste(amount);

        }

    }

    private void ucitajVehiclePrivate(String path) {
//TODO treba validirati
        BufferedReader br = null;
        String line = " ";
        String cvsSplitBy = ";";
        File file = new File(path);
        Vehicle vehicle;

        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));

         
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                if (lineNo++ != 0) {
                    // use comma as separator
                    String[] data = line.split(cvsSplitBy);

                    if (data.length != 5) {

                        System.out.println("Greška kod učitavanja vozila!!");

                        continue;
                    }

                
                    if (((String) data[2]).equalsIgnoreCase(Constants.VOZILO_STAKLO)) {
                        vehicle = new VehicleGlass();
                        vehicle.setName(data[0]);
                        if (((String) data[1]).equalsIgnoreCase(Constants.DIZEL)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.DIESEL);
                        } else if (((String) data[1]).equalsIgnoreCase(Constants.ELEKTRICNI)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.ELECTRIC);
                        } else {
                            System.out.println("Nepostojeća vrsta tipa vozila!");
                            continue;
                        }

                        vehicle.setCapacity(Float.valueOf(data[3]));
                        List<String> drivers = Arrays.asList(data[4].split(","));
                        vehicle.setDrivers(drivers);
                        allVehicles.add(vehicle);

                    } else if (((String) data[2]).equalsIgnoreCase(Constants.VOZILO_PAPIR)) {
                        vehicle = new VehiclePaper();
                        vehicle.setName(data[0]);
                        if (((String) data[1]).equalsIgnoreCase(Constants.DIZEL)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.DIESEL);
                        } else if (((String) data[1]).equalsIgnoreCase(Constants.ELEKTRICNI)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.ELECTRIC);
                        } else {
                            System.out.println("Nepostojeća vrsta tipa vozila!");
                            continue;
                        }

                        vehicle.setCapacity(Integer.valueOf(data[3]));
                        List<String> drivers = Arrays.asList(data[4].split(","));
                        vehicle.setDrivers(drivers);
                        allVehicles.add(vehicle);

                    } else if (((String) data[2]).equalsIgnoreCase(Constants.VOZILO_METAL)) {
                        vehicle = new VehicleMetal();
                        vehicle.setName(data[0]);
                        if (((String) data[1]).equalsIgnoreCase(Constants.DIZEL)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.DIESEL);
                        } else if (((String) data[1]).equalsIgnoreCase(Constants.ELEKTRICNI)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.ELECTRIC);
                        } else {
                            System.out.println("Nepostojeća vrsta tipa vozila!");
                            continue;
                        }

                        vehicle.setCapacity(Integer.valueOf(data[3]));
                        List<String> drivers = Arrays.asList(data[4].split(","));
                        vehicle.setDrivers(drivers);
                        allVehicles.add(vehicle);

                    } else if (((String) data[2]).equalsIgnoreCase(Constants.VOZILO_BIO)) {
                        vehicle = new VehicleBio();
                        vehicle.setName(data[0]);
                        if (((String) data[1]).equalsIgnoreCase(Constants.DIZEL)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.DIESEL);
                        } else if (((String) data[1]).equalsIgnoreCase(Constants.ELEKTRICNI)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.ELECTRIC);
                        } else {
                            System.out.println("Nepostojeća vrsta tipa vozila!");
                            continue;
                        }

                        vehicle.setCapacity(Integer.valueOf(data[3]));
                        List<String> drivers = Arrays.asList(data[4].split(","));
                        vehicle.setDrivers(drivers);
                        allVehicles.add(vehicle);

                    } else if (((String) data[2]).equalsIgnoreCase(Constants.VOZILO_MIJESANO)) {
                        vehicle = new VehicleMixed();
                        vehicle.setName(data[0]);
                        if (((String) data[1]).equalsIgnoreCase(Constants.DIZEL)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.DIESEL);
                        } else if (((String) data[1]).equalsIgnoreCase(Constants.ELEKTRICNI)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.ELECTRIC);
                        } else {
                            System.out.println("Nepostojeća vrsta tipa vozila!");
                            continue;
                        }

                        vehicle.setCapacity(Integer.valueOf(data[3]));
                        List<String> drivers = Arrays.asList(data[4].split(","));
                        vehicle.setDrivers(drivers);
                        allVehicles.add(vehicle);

                    }

                }

            }

        
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

    private void genrateWasteForUsersPrivate() {

        builderDirector.addEmptyLineInReport();
        builderDirector.addEmptyLineInReport();
        builderDirector.addTitleInReport("Pridružene količine otpada po korisnicima", false);

        try {
            for (int i = 0; i < streets.size(); i++) {
                builderDirector.addTitleInReport(streets.get(i).getName(), false);
                generateWasteForStreet(streets.get(i));
            }
            /*   streets.forEach((street) -> {
                builderDirector.addTitleInReport(street.getName(), false);
                generateWasteForStreet(street);

            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void generateWasteForStreet(Street street) {

        for (int j = 0; j < street.getUsersList().size(); j++) {

            assignWasteToUser(street.getUsersList().get(j));

        }

    }

    private void assignWasteToUser(User user) {
        float min = 0;

        float amount = 0;

        float minPercentage = 0;
        float maxGlass = 0;
        float maxPaper = 0;
        float maxMetel = 0;
        float maxBio = 0;
        float maxMixed = 0;
        this.builderDirector.addDividerLineInReport();
        this.builderDirector.addTextLineInReport("ID korisnika:      " + user.getId(), false);

        try {
            if (user instanceof SmallUser) {

                this.builderDirector.addTextLineInReport("Kategorija korisnika:      " + "mali korisnik", false);
                this.builderDirector.addDividerLineInReport();

                minPercentage = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliMin));

                // glass
                maxGlass = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliStaklo));
                min = (minPercentage * maxGlass) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxGlass);

                user.setGlassWaste(new GlassWaste(amount));

                this.builderDirector.addTextLineInReport("Staklo:           " + amount, false);

                //paper
                maxPaper = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliPapir));
                min = (minPercentage * maxPaper) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxPaper);

                user.setPaperWaste(new PaperWaste(amount));
                this.builderDirector.addTextLineInReport("Papir:            " + amount, false);

                //metal
                maxMetel = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliMetal));
                min = (minPercentage * maxMetel) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMetel);

                user.setMetalWaste(new MetalWaste(amount));
                this.builderDirector.addTextLineInReport("Metal:            " + amount, false);

                //bio
                maxBio = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliBio));
                min = (minPercentage * maxBio) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxBio);

                user.setBioWaste(new BioWaste(amount));
                this.builderDirector.addTextLineInReport("Bio:              " + amount, false);

                //mixed
                maxMixed = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliMjesano));
                min = (minPercentage * maxMixed) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMixed);

                user.setMixedWaste(new MixedWaste(amount));
                this.builderDirector.addTextLineInReport("Mješano:          " + amount, false);

            } else if (user instanceof MediumUser) {

                this.builderDirector.addTextLineInReport("Kategorija korisnika:      " + "srednji korisnik", false);
                this.builderDirector.addDividerLineInReport();
                minPercentage = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiMin));

                // glass
                maxGlass = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiStaklo));
                min = (minPercentage * maxGlass) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxGlass);

                user.setGlassWaste(new GlassWaste(amount));
                this.builderDirector.addTextLineInReport("Staklo:           " + amount, false);

                //paper
                maxPaper = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiPapir));
                min = (minPercentage * maxPaper) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxPaper);

                user.setPaperWaste(new PaperWaste(amount));
                this.builderDirector.addTextLineInReport("Papir:            " + amount, false);

                //metal
                maxMetel = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiMetal));
                min = (minPercentage * maxMetel) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMetel);

                user.setMetalWaste(new MetalWaste(amount));
                this.builderDirector.addTextLineInReport("Metal:            " + amount, false);

                //bio
                maxBio = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiBio));
                min = (minPercentage * maxBio) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxBio);

                user.setBioWaste(new BioWaste(amount));
                this.builderDirector.addTextLineInReport("Bio:              " + amount, false);

                //mixed
                maxMixed = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiMjesano));
                min = (minPercentage * maxMixed) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMixed);

                user.setMixedWaste(new MixedWaste(amount));
                this.builderDirector.addTextLineInReport("Mješano:          " + amount, false);

            } else if (user instanceof LargeUser) {

                this.builderDirector.addTextLineInReport("Kategorija korisnika:      " + "veliki korisnik", false);
                this.builderDirector.addDividerLineInReport();

                minPercentage = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiMin));

                // glass
                maxGlass = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiStaklo));
                min = (minPercentage * maxGlass) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxGlass);

                user.setGlassWaste(new GlassWaste(amount));
                this.builderDirector.addTextLineInReport("Staklo:           " + amount, false);

                //paper
                maxPaper = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiPapir));
                min = (minPercentage * maxPaper) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxPaper);

                user.setPaperWaste(new PaperWaste(amount));
                this.builderDirector.addTextLineInReport("Papir:            " + amount, false);
                //metal
                maxMetel = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiMetal));
                min = (minPercentage * maxMetel) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMetel);

                user.setMetalWaste(new MetalWaste(amount));
                this.builderDirector.addTextLineInReport("Metal:            " + amount, false);

                //bio
                maxBio = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiBio));
                min = (minPercentage * maxBio) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxBio);

                user.setBioWaste(new BioWaste(amount));
                this.builderDirector.addTextLineInReport("Bio:              " + amount, false);

                //mixed
                maxMixed = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiMjesano));
                min = (minPercentage * maxMixed) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMixed);

                user.setMixedWaste(new MixedWaste(amount));
                this.builderDirector.addTextLineInReport("Mješano:          " + amount, false);

            }

            this.builderDirector.addDividerLineInReport();

            this.builderDirector.addEmptyLineInReport();

        } catch (Exception e) {

            System.out.println("Error assignWasteToUser" + e);

        }

    }

    private void assignSpremnikToUsersPrivate() {

        try {
            for (int i = 0; i < streets.size(); i++) {
                assignSpremnikToStreet(streets.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void assignSpremnikToStreet(Street street) {
        List<Spremnik> nepopunjeniKontejneri = new ArrayList<Spremnik>();

        // kontejneri
        for (int i = 0; i < sviTipoviSpremnika.size(); i++) {

            if (sviTipoviSpremnika.get(i) instanceof Kontejner) {

                for (int j = 0; j < street.getUsersList().size(); j++) {

                    if (checkIfUserCanHaveThisContainer(street.getUsersList().get(j), sviTipoviSpremnika.get(i)) && !checkIfUserHaveThisContainer(street.getUsersList().get(j), sviTipoviSpremnika.get(i))) {

                        TypesOfUser typesOfUser = null;
                        if (street.getUsersList().get(j) instanceof SmallUser) {
                            typesOfUser = TypesOfUser.SMALL;
                        } else if (street.getUsersList().get(j) instanceof MediumUser) {
                            typesOfUser = TypesOfUser.MEDIUM;
                        } else if (street.getUsersList().get(j) instanceof LargeUser) {
                            typesOfUser = TypesOfUser.LARGE;
                        }

                        Kontejner kontejnerZaDodjeljivanje = (Kontejner) checkIfExistNepopunjenKontejner(sviTipoviSpremnika.get(i), nepopunjeniKontejneri, typesOfUser, street);
                        kontejnerZaDodjeljivanje.addUser(street.getUsersList().get(j));
                        kontejnerZaDodjeljivanje.setTypesOfUser(typesOfUser);

                        street.getUsersList().get(j).addSpremnik(kontejnerZaDodjeljivanje);
                        // street.getSpremnikList().add(kontejnerZaDodjeljivanje);
                        addOrDeleteSpremnikInnepopunjeniKontejneri(kontejnerZaDodjeljivanje, nepopunjeniKontejneri);
                    }

                }

            }

        }

        for (int i = 0; i < sviTipoviSpremnika.size(); i++) {
            if (sviTipoviSpremnika.get(i) instanceof Kanta) {

                for (int j = 0; j < street.getUsersList().size(); j++) {

                    if (checkIfUserCanHaveThisContainer(street.getUsersList().get(j), sviTipoviSpremnika.get(i)) && !checkIfUserHaveThisContainer(street.getUsersList().get(j), sviTipoviSpremnika.get(i))) {

                        TypesOfUser typesOfUser = null;
                        if (street.getUsersList().get(j) instanceof SmallUser) {
                            typesOfUser = TypesOfUser.SMALL;
                        } else if (street.getUsersList().get(j) instanceof MediumUser) {
                            typesOfUser = TypesOfUser.MEDIUM;
                        } else if (street.getUsersList().get(j) instanceof LargeUser) {
                            typesOfUser = TypesOfUser.LARGE;
                        }

                        Kanta kontejnerZaDodjeljivanje = (Kanta) checkIfExistNepopunjenKontejner(sviTipoviSpremnika.get(i), nepopunjeniKontejneri, typesOfUser, street);
                        kontejnerZaDodjeljivanje.addUser(street.getUsersList().get(j));
                        kontejnerZaDodjeljivanje.setTypesOfUser(typesOfUser);

                        street.getUsersList().get(j).addSpremnik(kontejnerZaDodjeljivanje);

                        addOrDeleteSpremnikInnepopunjeniKontejneri(kontejnerZaDodjeljivanje, nepopunjeniKontejneri);
                    }

                }

            }
        }

    }

    private void addOrDeleteSpremnikInnepopunjeniKontejneri(Spremnik kontejnerZaDodjeljivanje, List<Spremnik> nepopunjeniKontejneri) {
        for (int i = 0; i < nepopunjeniKontejneri.size(); i++) {

            if (nepopunjeniKontejneri.get(i).getId() == kontejnerZaDodjeljivanje.getId()) {

                if (kontejnerZaDodjeljivanje.getTypesOfUser() == TypesOfUser.SMALL) {
                    if (kontejnerZaDodjeljivanje.getNumberOfSmall() == kontejnerZaDodjeljivanje.getUsersList().size()) {
                        nepopunjeniKontejneri.remove(i);
                        return;
                    } else {
                        return;
                    }

                }

                if (kontejnerZaDodjeljivanje.getTypesOfUser() == TypesOfUser.MEDIUM) {
                    if (kontejnerZaDodjeljivanje.getNumberOfMedium() == kontejnerZaDodjeljivanje.getUsersList().size()) {
                        nepopunjeniKontejneri.remove(i);
                        return;
                    } else {
                        return;
                    }

                }

                if (kontejnerZaDodjeljivanje.getTypesOfUser() == TypesOfUser.LARGE) {
                    if (kontejnerZaDodjeljivanje.getNumberOfLarge() == kontejnerZaDodjeljivanje.getUsersList().size()) {
                        nepopunjeniKontejneri.remove(i);
                        return;
                    } else {
                        return;
                    }

                }

            }
        }

        if (kontejnerZaDodjeljivanje.getTypesOfUser().equals(TypesOfUser.SMALL)) {
            if (kontejnerZaDodjeljivanje.getNumberOfSmall() > kontejnerZaDodjeljivanje.getUsersList().size()) {
                nepopunjeniKontejneri.add(kontejnerZaDodjeljivanje);
            }
        } else if (kontejnerZaDodjeljivanje.getTypesOfUser().equals(TypesOfUser.MEDIUM)) {
            if (kontejnerZaDodjeljivanje.getNumberOfMedium() > kontejnerZaDodjeljivanje.getUsersList().size()) {
                nepopunjeniKontejneri.add(kontejnerZaDodjeljivanje);
            }
        } else if (kontejnerZaDodjeljivanje.getTypesOfUser().equals(TypesOfUser.LARGE)) {
            if (kontejnerZaDodjeljivanje.getNumberOfLarge() > kontejnerZaDodjeljivanje.getUsersList().size()) {
                nepopunjeniKontejneri.add(kontejnerZaDodjeljivanje);
            }
        }

    }

    private Spremnik checkIfExistNepopunjenKontejner(Spremnik spremnik, List<Spremnik> nepopunjeniKontejneri, TypesOfUser typesOfUser, Street street) {
        for (int i = 0; i < nepopunjeniKontejneri.size(); i++) {

            if (nepopunjeniKontejneri.get(i).getKindOfWaste().equals(spremnik.getKindOfWaste()) && nepopunjeniKontejneri.get(i).getTypesOfUser().equals(typesOfUser)) {

                if (typesOfUser.equals(TypesOfUser.SMALL)) {
                    if (spremnik.getNumberOfSmall() > spremnik.getUsersList().size()) {
                        return nepopunjeniKontejneri.get(i);
                    }
                } else if (typesOfUser.equals(TypesOfUser.MEDIUM)) {
                    if (spremnik.getNumberOfMedium() > spremnik.getUsersList().size()) {
                        return nepopunjeniKontejneri.get(i);
                    }
                } else if (typesOfUser.equals(TypesOfUser.LARGE)) {
                    if (spremnik.getNumberOfLarge() > spremnik.getUsersList().size()) {
                        return nepopunjeniKontejneri.get(i);
                    }
                }

            }
        }
        Spremnik spremnik1 = spremnik.clone();
        street.getSpremnikList().add(spremnik1);
        return spremnik1;

    }

    private boolean checkIfUserHaveThisContainer(User user, Spremnik spremnik) {

        for (int i = 0; i < user.getSpremnikList().size(); i++) {
            if (user.getSpremnikList().get(i).getKindOfWaste().equals(spremnik.getKindOfWaste())) {
                return true;
            }
        }
        return false;
    }

    private void ucitajSpremnikePrivate(String path) {
//TODO treba validirati
        BufferedReader br = null;
        String line = " ";
        String cvsSplitBy = ";";
        File file = new File(path);
        Spremnik spremnik;

        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));

            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                if (lineNo++ != 0) {
                    // use comma as separator
                    String[] data = line.split(cvsSplitBy);

                    if (data.length != 6) {
                        continue;
                    }
                    if (((String) data[1]).equalsIgnoreCase(Constants.KANTA)) {
                        spremnik = new Kanta();
                        spremnik.setKindOfWaste(convertKindOfWaste(data[0]));
                        spremnik.setNumberOfSmall(Integer.valueOf(data[2]));
                        spremnik.setNumberOfMedium(Integer.valueOf(data[3]));
                        spremnik.setNumberOfLarge(Integer.valueOf(data[4]));
                        spremnik.setCapacity(Float.valueOf(data[5]));

                        sviTipoviSpremnika.add(spremnik);

                    } else if (((String) data[1]).equalsIgnoreCase(Constants.KONTEJNER)) {
                        spremnik = new Kontejner();
                        spremnik.setKindOfWaste(convertKindOfWaste(data[0]));
                        spremnik.setNumberOfSmall(Integer.valueOf(data[2]));
                        spremnik.setNumberOfMedium(Integer.valueOf(data[3]));
                        spremnik.setNumberOfLarge(Integer.valueOf(data[4]));
                        spremnik.setCapacity(Float.valueOf(data[5]));

                        sviTipoviSpremnika.add(spremnik);
                    }

                }

            }

       
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

            // br = new BufferedReader(new FileReader(file));
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));

            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                if (lineNo++ != 0) {
                    // use comma as separator
                    String[] data = line.split(cvsSplitBy);
                    street = new Street(data[0], Integer.valueOf(data[1]), Integer.valueOf(data[2]), Integer.valueOf(data[3]), Integer.valueOf(data[4]));
                    streets.add(street);

                }

            }

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

    private TypesOfWaste convertKindOfWaste(String string) {
        TypesOfWaste typesOfWaste = null;
        switch (string) {
            case Constants.BIO:
                typesOfWaste = TypesOfWaste.BIO;
                break;
            case Constants.METAL:
                typesOfWaste = TypesOfWaste.METAL;
                break;
            case Constants.MJESANO:
                typesOfWaste = TypesOfWaste.MJESANO;
                break;
            case Constants.PAPIR:
                typesOfWaste = TypesOfWaste.PAPIR;
                break;
            case Constants.STAKLO:
                typesOfWaste = TypesOfWaste.STAKLO;
                break;

        }

        return typesOfWaste;

    }

    private boolean checkIfUserCanHaveThisContainer(User user, Spremnik spremnik) {

        TypesOfUser typesOfUser = null;

        boolean canHave = false;

        if (user instanceof SmallUser) {
            if (spremnik.getNumberOfSmall() > 0) {
                canHave = true;
            }
        } else if (user instanceof MediumUser) {
            if (spremnik.getNumberOfMedium() > 0) {
                canHave = true;
            }
        } else if (user instanceof LargeUser) {
            if (spremnik.getNumberOfLarge() > 0) {
                canHave = true;
            }
        }

        return canHave;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public List<Vehicle> getAllVehicles() {
        return allVehicles;
    }

}
