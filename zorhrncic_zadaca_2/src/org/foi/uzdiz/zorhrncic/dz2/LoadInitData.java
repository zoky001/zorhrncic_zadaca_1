/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2;

import org.foi.uzdiz.zorhrncic.dz2.composite.Street;
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
import org.foi.uzdiz.zorhrncic.dz2.composite.AreaModel;
import org.foi.uzdiz.zorhrncic.dz2.composite.CompositePlace;
import org.foi.uzdiz.zorhrncic.dz2.composite.Place;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Kanta;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Kontejner;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz2.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz2.factory.AbstarctFactory;
import org.foi.uzdiz.zorhrncic.dz2.log.Report;
import org.foi.uzdiz.zorhrncic.dz2.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz2.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfFactories;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfSpremnik;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfUser;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfVehicleEngine;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz2.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz2.users.BigUser;
import org.foi.uzdiz.zorhrncic.dz2.users.MediumUser;
import org.foi.uzdiz.zorhrncic.dz2.users.SmallUser;
import org.foi.uzdiz.zorhrncic.dz2.users.User;
import org.foi.uzdiz.zorhrncic.dz2.waste.BioWaste;
import org.foi.uzdiz.zorhrncic.dz2.waste.GlassWaste;
import org.foi.uzdiz.zorhrncic.dz2.waste.MetalWaste;
import org.foi.uzdiz.zorhrncic.dz2.waste.MixedWaste;
import org.foi.uzdiz.zorhrncic.dz2.waste.PaperWaste;

/**
 *
 * @author Zoran
 */
public class LoadInitData {

    private List<Street> streets;
    private List<CompositePlace> areasModelList;
    private List<Spremnik> sviTipoviSpremnika;
    private List<Vehicle> allVehicles;
    private final ReportBuilderDirector builderDirector;
    private Report report;
    private AbstarctFactory userFactory;
    private AbstarctFactory spremnikFactory;
    private final AbstarctFactory wasteFactory;
    private final AbstarctFactory vehicleFactory;
    private CompositePlace areaRootElement;

    public LoadInitData() {
        areasModelList = new ArrayList<CompositePlace>();
        streets = new ArrayList<Street>();
        sviTipoviSpremnika = new ArrayList<Spremnik>();
        allVehicles = new ArrayList<>();
        builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();
        userFactory = CommonDataSingleton.getInstance().getFactory(TypesOfFactories.USER_FACTORY);
        spremnikFactory = CommonDataSingleton.getInstance().getFactory(TypesOfFactories.SPREMNIK_FACTORY);
        wasteFactory = CommonDataSingleton.getInstance().getFactory(TypesOfFactories.WASTE_FACTORY);
        vehicleFactory = CommonDataSingleton.getInstance().getFactory(TypesOfFactories.VEHICLE_FACTORY);
    }

    public void loadData() {

        ucitajSpremnikePrivate((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.spremnici));
        ucitajVehiclePrivate((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.vozila));
        loadStreetsPrivate((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.ulice));
        loadAreasPrivate((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.podrucja));
        putTheStreetsInTheArea();
        loadUsersForStreetsPrivate();
        assignSpremnikToUsersPrivate();
        genrateWasteForUsersPrivate();
        calculateTotalAmountOfUsersWasteInEveryStreet();
        //  report.print();
        
        areaRootElement.printDataAboutGeneratedWaste();
     /*   streets.forEach((k) -> {

                 k.printDataAboutGeneratedWaste();
            //   Spremnik.printArray(k.getSpremnikList());
        });
        */
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

            this.builderDirector.addDividerLineInReport(false);
            this.builderDirector.addTextLineInReport("Naziv ulice:      " + streets.get(i).getName(), false);
            this.builderDirector.addDividerLineInReport(false);
            this.builderDirector.addTextLineInReport("Staklo:           " + totalAmountGlass, false);
            this.builderDirector.addTextLineInReport("Papir:            " + totalAmounPaper, false);
            this.builderDirector.addTextLineInReport("Metal:            " + totalAmounMetal, false);
            this.builderDirector.addTextLineInReport("Bio:              " + totalAmounBio, false);
            this.builderDirector.addTextLineInReport("Mješano:          " + totalAmounMixed, false);
            this.builderDirector.addDividerLineInReport(false);
            report = this.builderDirector.addEmptyLineInReport(false);

        }

        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addTitleInReport("Popis ukupne količine otpada koji generiraju korisnici po ulicama", false);

        this.builderDirector.addDividerLineInReport(false);
        this.builderDirector.addTextLineInReport("Staklo:           " + totalAmountGlassAll, false);
        this.builderDirector.addTextLineInReport("Papir:            " + totalAmounPaperAll, false);
        this.builderDirector.addTextLineInReport("Metal:            " + totalAmounMetalAll, false);
        this.builderDirector.addTextLineInReport("Bio:              " + totalAmounBioAll, false);
        this.builderDirector.addTextLineInReport("Mješano:          " + totalAmounMixedAll, false);
        this.builderDirector.addDividerLineInReport(false);
        this.builderDirector.addTextLineInReport("UKUPNO:           " + totalAmounAll, false);
        this.builderDirector.addDividerLineInReport(false);

        this.builderDirector.addTitleInReport("Popis ukupne količine otpada koji generiraju korisnici po ulicama", false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);

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

        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addTitleInReport("Popis ukupne količine otpada u spremnicima po ulicama", false);

        this.builderDirector.addDividerLineInReport(false);
        this.builderDirector.addTextLineInReport("Staklo:           " + totalAmountGlassAll, false);
        this.builderDirector.addTextLineInReport("Papir:            " + totalAmounPaperAll, false);
        this.builderDirector.addTextLineInReport("Metal:            " + totalAmounMetalAll, false);
        this.builderDirector.addTextLineInReport("Bio:              " + totalAmounBioAll, false);
        this.builderDirector.addTextLineInReport("Mješano:          " + totalAmounMixedAll, false);
        this.builderDirector.addDividerLineInReport(false);
        this.builderDirector.addTextLineInReport("UKUPNO:           "
                + (totalAmountGlassAll + totalAmounPaperAll + totalAmounMetalAll + totalAmounBioAll + totalAmounMixedAll),
                false);
        this.builderDirector.addDividerLineInReport(false);

        this.builderDirector.addTitleInReport("Popis ukupne količine otpada u spremnicima po ulicama", false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);
        this.builderDirector.addEmptyLineInReport(false);

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

            builderDirector.addEmptyLineInReport(false);
            builderDirector.addEmptyLineInReport(false);
            builderDirector.addDividerLineInReport(Boolean.FALSE);
            builderDirector.addTextLineInReport("Nema više mjesta u kanti. Kanta: " + spremnik.getId(), false);
            builderDirector.addDividerLineInReport(Boolean.FALSE);
            builderDirector.addEmptyLineInReport(false);
            builderDirector.addEmptyLineInReport(false);

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

                    if (data.length != 6) {

                        builderDirector.addErrorInReport("Greška kod učitavanja vozila!! \n\n " + data, false);

                        continue;
                    }

                    if (((String) data[3]).equalsIgnoreCase(Constants.VOZILO_STAKLO)) {
                        vehicle = vehicleFactory.getVehicle(TypesOfWaste.STAKLO, Float.valueOf(data[4]));//new VehicleGlass();
                        vehicle.setId(data[0]);
                        vehicle.setName(data[1]);
                        if (((String) data[2]).equalsIgnoreCase(Constants.DIZEL)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.DIESEL);
                        } else if (((String) data[2]).equalsIgnoreCase(Constants.ELEKTRICNI)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.ELECTRIC);
                        } else {

                            builderDirector.addErrorInReport("Nepostojeća vrsta tipa vozila!", false);
                            continue;
                        }

                        //     vehicle.setCapacity(Float.valueOf(data[3]));
                        List<String> drivers = Arrays.asList(data[5].split(","));
                        vehicle.setDrivers(drivers);
                        allVehicles.add(vehicle);

                    } else if (((String) data[3]).equalsIgnoreCase(Constants.VOZILO_PAPIR)) {
                        vehicle = vehicleFactory.getVehicle(TypesOfWaste.PAPIR, Float.valueOf(data[4]));//new VehiclePaper();
                        vehicle.setId(data[0]);
                        vehicle.setName(data[1]);
                        if (((String) data[2]).equalsIgnoreCase(Constants.DIZEL)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.DIESEL);
                        } else if (((String) data[2]).equalsIgnoreCase(Constants.ELEKTRICNI)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.ELECTRIC);
                        } else {

                            builderDirector.addErrorInReport("Nepostojeća vrsta tipa vozila!", false);
                            continue;
                        }

                        //  vehicle.setCapacity(Integer.valueOf(data[3]));
                        List<String> drivers = Arrays.asList(data[5].split(","));
                        vehicle.setDrivers(drivers);
                        allVehicles.add(vehicle);

                    } else if (((String) data[3]).equalsIgnoreCase(Constants.VOZILO_METAL)) {
                        vehicle = vehicleFactory.getVehicle(TypesOfWaste.METAL, Float.valueOf(data[4]));//new VehicleMetal();
                        vehicle.setId(data[0]);
                        vehicle.setName(data[1]);
                        if (((String) data[2]).equalsIgnoreCase(Constants.DIZEL)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.DIESEL);
                        } else if (((String) data[2]).equalsIgnoreCase(Constants.ELEKTRICNI)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.ELECTRIC);
                        } else {

                            builderDirector.addErrorInReport("Nepostojeća vrsta tipa vozila!", false);
                            continue;
                        }

                        //vehicle.setCapacity(Integer.valueOf(data[3]));
                        List<String> drivers = Arrays.asList(data[5].split(","));
                        vehicle.setDrivers(drivers);
                        allVehicles.add(vehicle);

                    } else if (((String) data[3]).equalsIgnoreCase(Constants.VOZILO_BIO)) {
                        vehicle = vehicleFactory.getVehicle(TypesOfWaste.BIO, Float.valueOf(data[4]));//new VehicleBio();
                        vehicle.setId(data[0]);
                        vehicle.setName(data[1]);
                        if (((String) data[2]).equalsIgnoreCase(Constants.DIZEL)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.DIESEL);
                        } else if (((String) data[2]).equalsIgnoreCase(Constants.ELEKTRICNI)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.ELECTRIC);
                        } else {

                            builderDirector.addErrorInReport("Nepostojeća vrsta tipa vozila!", false);
                            continue;
                        }

                        //  vehicle.setCapacity(Integer.valueOf(data[3]));
                        List<String> drivers = Arrays.asList(data[5].split(","));
                        vehicle.setDrivers(drivers);
                        allVehicles.add(vehicle);

                    } else if (((String) data[3]).equalsIgnoreCase(Constants.VOZILO_MIJESANO)) {
                        vehicle = vehicleFactory.getVehicle(TypesOfWaste.MJESANO, Float.valueOf(data[4]));// new VehicleMixed();
                        vehicle.setId(data[0]);
                        vehicle.setName(data[1]);
                        if (((String) data[2]).equalsIgnoreCase(Constants.DIZEL)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.DIESEL);
                        } else if (((String) data[2]).equalsIgnoreCase(Constants.ELEKTRICNI)) {
                            vehicle.setTypesOfVehicleEngine(TypesOfVehicleEngine.ELECTRIC);
                        } else {

                            builderDirector.addErrorInReport("Nepostojeća vrsta tipa vozila!", false);
                            continue;
                        }

                        //   vehicle.setCapacity(Integer.valueOf(data[3]));
                        List<String> drivers = Arrays.asList(data[5].split(","));
                        vehicle.setDrivers(drivers);
                        allVehicles.add(vehicle);

                    }

                }

            }

        } catch (FileNotFoundException e) {
            builderDirector.addErrorInReport("Greška kod učitavanja vozila!! \n\n ", false);
            e.printStackTrace();
        } catch (IOException e) {
            builderDirector.addErrorInReport("Greška kod učitavanja vozila!! \n\n ", false);
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

        builderDirector.addEmptyLineInReport(false);
        builderDirector.addEmptyLineInReport(false);
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
        this.builderDirector.addDividerLineInReport(false);
        this.builderDirector.addTextLineInReport("ID korisnika:      " + user.getId(), false);

        try {
            if (user instanceof SmallUser) {

                this.builderDirector.addTextLineInReport("Kategorija korisnika:      " + "mali korisnik", false);
                this.builderDirector.addDividerLineInReport(false);

                minPercentage = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliMin));

                // glass
                maxGlass = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliStaklo));
                min = (minPercentage * maxGlass) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxGlass);

                //     user.setGlassWaste(new GlassWaste(amount));
                user.setGlassWaste(wasteFactory.getWaste(TypesOfWaste.BIO, amount));

                this.builderDirector.addTextLineInReport("Staklo:           " + amount, false);

                //paper
                maxPaper = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliPapir));
                min = (minPercentage * maxPaper) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxPaper);

                // user.setPaperWaste(new PaperWaste(amount));
                user.setPaperWaste(wasteFactory.getWaste(TypesOfWaste.PAPIR, amount));
                this.builderDirector.addTextLineInReport("Papir:            " + amount, false);

                //metal
                maxMetel = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliMetal));
                min = (minPercentage * maxMetel) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMetel);

                //user.setMetalWaste(new MetalWaste(amount));
                user.setMetalWaste(wasteFactory.getWaste(TypesOfWaste.METAL, amount));
                this.builderDirector.addTextLineInReport("Metal:            " + amount, false);

                //bio
                maxBio = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliBio));
                min = (minPercentage * maxBio) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxBio);

                // user.setBioWaste(new BioWaste(amount));
                user.setBioWaste(wasteFactory.getWaste(TypesOfWaste.BIO, amount));
                this.builderDirector.addTextLineInReport("Bio:              " + amount, false);

                //mixed
                maxMixed = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.maliMjesano));
                min = (minPercentage * maxMixed) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMixed);

                //     user.setMixedWaste(new MixedWaste(amount));
                user.setMixedWaste(wasteFactory.getWaste(TypesOfWaste.MJESANO, amount));

                this.builderDirector.addTextLineInReport("Mješano:          " + amount, false);

            } else if (user instanceof MediumUser) {

                this.builderDirector.addTextLineInReport("Kategorija korisnika:      " + "srednji korisnik", false);
                this.builderDirector.addDividerLineInReport(false);
                minPercentage = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiMin));

                // glass
                maxGlass = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiStaklo));
                min = (minPercentage * maxGlass) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxGlass);

                //user.setGlassWaste(new GlassWaste(amount));
                user.setGlassWaste(wasteFactory.getWaste(TypesOfWaste.STAKLO, amount));

                this.builderDirector.addTextLineInReport("Staklo:           " + amount, false);

                //paper
                maxPaper = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiPapir));
                min = (minPercentage * maxPaper) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxPaper);

                // user.setPaperWaste(new PaperWaste(amount));
                user.setPaperWaste(wasteFactory.getWaste(TypesOfWaste.PAPIR, amount));
                this.builderDirector.addTextLineInReport("Papir:            " + amount, false);

                //metal
                maxMetel = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiMetal));
                min = (minPercentage * maxMetel) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMetel);

                //user.setMetalWaste(new MetalWaste(amount));
                user.setMetalWaste(wasteFactory.getWaste(TypesOfWaste.METAL, amount));
                this.builderDirector.addTextLineInReport("Metal:            " + amount, false);

                //bio
                maxBio = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiBio));
                min = (minPercentage * maxBio) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxBio);

                //  user.setBioWaste(new BioWaste(amount));
                user.setBioWaste(wasteFactory.getWaste(TypesOfWaste.BIO, amount));
                this.builderDirector.addTextLineInReport("Bio:              " + amount, false);

                //mixed
                maxMixed = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.srednjiMjesano));
                min = (minPercentage * maxMixed) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMixed);

                //user.setMixedWaste(new MixedWaste(amount));
                user.setMixedWaste(wasteFactory.getWaste(TypesOfWaste.MJESANO, amount));

                this.builderDirector.addTextLineInReport("Mješano:          " + amount, false);

            } else if (user instanceof BigUser) {

                this.builderDirector.addTextLineInReport("Kategorija korisnika:      " + "veliki korisnik", false);
                this.builderDirector.addDividerLineInReport(false);

                minPercentage = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiMin));

                // glass
                maxGlass = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiStaklo));
                min = (minPercentage * maxGlass) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxGlass);

                //  user.setGlassWaste(new GlassWaste(amount));
                user.setGlassWaste(wasteFactory.getWaste(TypesOfWaste.STAKLO, amount));
                this.builderDirector.addTextLineInReport("Staklo:           " + amount, false);

                //paper
                maxPaper = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiPapir));
                min = (minPercentage * maxPaper) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxPaper);

                //user.setPaperWaste(new PaperWaste(amount));
                user.setPaperWaste(wasteFactory.getWaste(TypesOfWaste.PAPIR, amount));
                this.builderDirector.addTextLineInReport("Papir:            " + amount, false);
                //metal
                maxMetel = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiMetal));
                min = (minPercentage * maxMetel) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMetel);

                //      user.setMetalWaste(new MetalWaste(amount));
                user.setMetalWaste(wasteFactory.getWaste(TypesOfWaste.METAL, amount));
                this.builderDirector.addTextLineInReport("Metal:            " + amount, false);

                //bio
                maxBio = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiBio));
                min = (minPercentage * maxBio) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxBio);

                // user.setBioWaste(new BioWaste(amount));
                user.setBioWaste(wasteFactory.getWaste(TypesOfWaste.BIO, amount));
                this.builderDirector.addTextLineInReport("Bio:              " + amount, false);

                //mixed
                maxMixed = Float.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.velikiMjesano));
                min = (minPercentage * maxMixed) / 100;

                amount = CommonDataSingleton.getInstance().getRandomFloat(min, maxMixed);

                // user.setMixedWaste(new MixedWaste(amount));
                user.setMixedWaste(wasteFactory.getWaste(TypesOfWaste.MJESANO, amount));

                this.builderDirector.addTextLineInReport("Mješano:          " + amount, false);

            }

            this.builderDirector.addDividerLineInReport(false);

            this.builderDirector.addEmptyLineInReport(false);

        } catch (Exception e) {

            builderDirector.addErrorInReport("Error assignWasteToUser" + e, false);

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
                        } else if (street.getUsersList().get(j) instanceof BigUser) {
                            typesOfUser = TypesOfUser.BIG;
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
                        } else if (street.getUsersList().get(j) instanceof BigUser) {
                            typesOfUser = TypesOfUser.BIG;
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

                if (kontejnerZaDodjeljivanje.getTypesOfUser() == TypesOfUser.BIG) {
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
        } else if (kontejnerZaDodjeljivanje.getTypesOfUser().equals(TypesOfUser.BIG)) {
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
                } else if (typesOfUser.equals(TypesOfUser.BIG)) {
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
                        builderDirector.addErrorInReport("Pogrešan zapis spremnika u datoteci!! Zapis: " + line.toString(), false);
                        continue;
                    }
                    if (((String) data[1]).equalsIgnoreCase(Constants.KANTA)) {
                        //    spremnik = spremnikFactory.getSpremnik(TypesOfSpremnik.KANTA, convertKindOfWaste(data[0]));//new Kanta();
                        spremnik = spremnikFactory.getSpremnik(TypesOfSpremnik.KANTA, convertKindOfWaste(data[0]), Float.valueOf(data[5]));//new Kanta();
                        //spremnik.setKindOfWaste(convertKindOfWaste(data[0]));
                        spremnik.setNumberOfSmall(Integer.valueOf(data[2]));
                        spremnik.setNumberOfMedium(Integer.valueOf(data[3]));
                        spremnik.setNumberOfLarge(Integer.valueOf(data[4]));
                        //  spremnik.setCapacity(Float.valueOf(data[5]));

                        sviTipoviSpremnika.add(spremnik);

                    } else if (((String) data[1]).equalsIgnoreCase(Constants.KONTEJNER)) {
                        spremnik = spremnikFactory.getSpremnik(TypesOfSpremnik.KONTEJNER, convertKindOfWaste(data[0]), Float.valueOf(data[5]));//new Kontejner();
                        //spremnik.setKindOfWaste(convertKindOfWaste(data[0]));
                        spremnik.setNumberOfSmall(Integer.valueOf(data[2]));
                        spremnik.setNumberOfMedium(Integer.valueOf(data[3]));
                        spremnik.setNumberOfLarge(Integer.valueOf(data[4]));
                        //spremnik.setCapacity(Float.valueOf(data[5]));

                        sviTipoviSpremnika.add(spremnik);
                    }

                }

            }

        } catch (FileNotFoundException e) {
            builderDirector.addErrorInReport("Pogrešan zapis spremnika u datoteci!!", false);

            e.printStackTrace();
        } catch (IOException e) {
            builderDirector.addErrorInReport("Pogrešan zapis spremnika u datoteci!!", false);

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
                user = userFactory.getUser(TypesOfUser.SMALL);//new SmallUser();
                street.addUser(user);

            }
            for (int i = 0; i < numberOfMedium; i++) {
                user = userFactory.getUser(TypesOfUser.MEDIUM);//new MediumUser();
                street.addUser(user);

            }
            for (int i = 0; i < numberOfLarge; i++) {
                user = userFactory.getUser(TypesOfUser.BIG);// new BigUser();
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
        // System.out.println(path);
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
                    if (data.length == 6) {
                        street = new Street(data[0], data[1], Integer.valueOf(data[2]), Integer.valueOf(data[3]), Integer.valueOf(data[4]), Integer.valueOf(data[5]));
                        streets.add(street);
                    } else {
                        builderDirector.addErrorInReport("Pogrešan zapis ulice u datoteci!! Zapis: " + line.toString(), false);
                    }

                }

            }

        } catch (FileNotFoundException e) {
            builderDirector.addErrorInReport("Pogrešan zapis ulice u datoteci!!", false);

            e.printStackTrace();
        } catch (IOException e) {
            builderDirector.addErrorInReport("Pogrešan zapis ulice u datoteci!!", false);

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

    private void loadAreasPrivate(String path) {
        BufferedReader br = null;
        String line = " ";
        String cvsSplitBy = ";";
        // System.out.println(path);
        File file = new File(path);
        CompositePlace areaModel;

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
                    if (data.length == 3) {
                        areaModel = new CompositePlace(data[0], data[1]);
                        areaModel.setParts(Arrays.asList(data[2].split(",")));
                        areasModelList.add(areaModel);
                    } else {
                        builderDirector.addErrorInReport("Pogrešan zapis područja u datoteci!! Zapis: " + line.toString(), false);
                    }

                }

            }

        } catch (FileNotFoundException e) {
            builderDirector.addErrorInReport("Pogrešan zapis područja u datoteci!!", false);
            e.printStackTrace();
        } catch (IOException e) {
            builderDirector.addErrorInReport("Pogrešan zapis područja u datoteci!!", false);
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

    private void putTheStreetsInTheArea() {
        areaRootElement = findRootArea();
        System.out.println("ROOT is " + areaRootElement.getId() + " - " + areaRootElement.getName());

        Place subPlace;
        /*    for (String id : areaRootElement.getParts()) {
            subPlace = findSubPlaceByID(id);
            if (subPlace != null) {
                areaRootElement.add(subPlace);
            } else {
                builderDirector.addErrorInReport("Ne postoji područje/ulica sa ID: " + id, false);
            }
        }
         */
        areaRootElement = (CompositePlace) findAllSubAreasRecursive(areaRootElement);

        System.out.println("tu sam");

    }

    private Place findAllSubAreasRecursive(Place compositePlace) {
        Place subPlace;

        if (compositePlace instanceof CompositePlace) {

            for (String id : ((CompositePlace) compositePlace).getParts()) {
                subPlace = findSubPlaceByID(id);
                if (subPlace != null) {
                    ((CompositePlace) compositePlace).add(subPlace);
                } else {
                    builderDirector.addErrorInReport("Ne postoji područje/ulica sa ID: " + id, false);
                }
            }

            for (Place place : ((CompositePlace) compositePlace).getmChildPlaces()) {

                if (place instanceof CompositePlace) {
                    for (String id : ((CompositePlace) place).getParts()) {
                        subPlace = findSubPlaceByID(id);
                        if (subPlace != null) {
                            ((CompositePlace) place).add(subPlace);
                            if (subPlace instanceof CompositePlace) {
                                subPlace = findAllSubAreasRecursive(subPlace);
                            }
                        } else {
                            builderDirector.addErrorInReport("Ne postoji područje/ulica sa ID: " + id, false);
                        }
                    }
                } else {
                    System.out.println("to je ulica");
                    // return null;
                }

            }

        } else {
            return compositePlace;
        }

        return compositePlace;

    }

    private Place findSubPlaceByID(String id) {
        Place place = null;
//todo check if exist more root area
        for (CompositePlace area : areasModelList) {
            if (area.getId().equalsIgnoreCase(id)) {
                return place = area;
            }
        }
        for (Street street : streets) {
            if (street.getId().equalsIgnoreCase(id)) {
                return place = street;
            }
        }
        return place;
    }

    private CompositePlace findRootArea() {
        CompositePlace areaModelRoot = null;
//todo check if exist more root area
        for (CompositePlace areaModel : areasModelList) {
            if (!checkIfItIsSubArea(areaModel)) {
                return areaModelRoot = areaModel;
            }
        }
        return areaModelRoot;
    }

    private boolean checkIfItIsSubArea(CompositePlace area) {
        boolean isSubArea = false;
        String id = area.getId();

        for (CompositePlace areaModel : areasModelList) {
            if (areaModel.getParts().contains(id)) {
                isSubArea = true;
            }

        }
        return isSubArea;
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
        } else if (user instanceof BigUser) {
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
