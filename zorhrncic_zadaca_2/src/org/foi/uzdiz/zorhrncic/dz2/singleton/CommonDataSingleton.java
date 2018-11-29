/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.singleton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import static jdk.nashorn.internal.runtime.JSType.isString;
import org.foi.uzdiz.zorhrncic.dz2.factory.AbstarctFactory;
import org.foi.uzdiz.zorhrncic.dz2.factory.FactoryProducer;
import org.foi.uzdiz.zorhrncic.dz2.log.Report;
import org.foi.uzdiz.zorhrncic.dz2.log.ReportBuilder;
import org.foi.uzdiz.zorhrncic.dz2.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz2.log.ReportBuilderImpl;
import org.foi.uzdiz.zorhrncic.dz2.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz2.shared.TypesOfFactories;

/**
 *
 * @author Zoran
 */
public class CommonDataSingleton {

    private static CommonDataSingleton instance;

    Properties prop = new Properties();
    private Random generator;
    DecimalFormat df;

    private final ReportBuilder builder;
    private final ReportBuilderDirector builderDirector;

    private CommonDataSingleton() {
        this.builder = new ReportBuilderImpl();
        this.builderDirector = new ReportBuilderDirector(builder);
    }

    public void printProp() {
        prop.forEach((t, u) -> {
            System.out.println("KEY:" + t + "Value: " + u);
        });
    }

    public static synchronized CommonDataSingleton getInstance() {
        if (instance == null) {
            instance = new CommonDataSingleton();
        }
        return instance;
    }

    public ReportBuilderDirector getReportBuilderDirector() {

        return builderDirector;

    }

    public int getRandomInt(int min, int max) {

        try {

            if (generator == null) {
                createGenerator(Long.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.sjemeGeneratora)));
            }

            return generator.nextInt((max - min) + 1) + min;

        } catch (Exception e) {
            ReportBuilderDirector director = getReportBuilderDirector();

            director.addErrorInReport("Greška u generiranju random INT broja", false);

        }
        return -1;
    }

    public ArrayList<Integer> getRandomArray(int size) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        while (numbers.size() < size) {

            int random = getRandomInt(0, size - 1);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        return numbers;
    }

    public long getRandomLong() {

        try {

            if (generator == null) {
                createGenerator(Long.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.sjemeGeneratora)));
            }

            return generator.nextLong();

        } catch (Exception e) {

            ReportBuilderDirector director = getReportBuilderDirector();

            director.addErrorInReport("Greška u generiranju random LONG broja", false);

        }
        return -1;
    }

    public float getRandomFloat(float min, float max) {

        try {

            if (generator == null) {
                createGenerator(Long.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.sjemeGeneratora)));
            }
            if (df == null) {
                df = new DecimalFormat();
                df.setMinimumFractionDigits(Integer.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.brojDecimala)));
                df.setMaximumFractionDigits(Integer.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.brojDecimala)));
            }

            float myFloat = generator.nextFloat() * (max - min) + min;
            String s = df.format(myFloat).replace(",", ".");

            return Float.parseFloat(s);//Float.valueOf(df.format(myFloat));

        } catch (Exception e) {

            ReportBuilderDirector director = getReportBuilderDirector();

            director.addErrorInReport("Greška u generiranju random FLOAT broja", false);

        }
        return -1;
    }

    private void createGenerator(long seed) {

        generator = new Random(seed);

    }

    public void loadParametes(String path) {
        loadParametersPrivate(path);
    }

    private void loadParametersPrivate(String path) {

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ":";
        File file = new File(path);

        try {
            FileInputStream input = new FileInputStream(file);

            // load a properties file
            // prop.load(input);
            br = new BufferedReader(new FileReader(file));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));

            prop.load(in);

            validateProp(prop);

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

    public Object getParameterByKey(String key) {
        return getParameterByKeyPrivate(key);
    }

    private Object getParameterByKeyPrivate(String path) {

        try {
            // return confData.get(path);
            return prop.get(path);

        } catch (Exception e) {

            ReportBuilderDirector director = getReportBuilderDirector();

            director.addErrorInReport("Greška prilikom dohvata podataka.", false);

        }

        return null;

    }

    public AbstarctFactory getFactory(TypesOfFactories typesOfFactories) {
        return FactoryProducer.getFactory(typesOfFactories);
    }

    private void validateProp(Properties prop) {

        List<String> keys = new ArrayList<String>();
        keys.add(Constants.ulice);
        keys.add(Constants.spremnici);
        keys.add(Constants.vozila);
        keys.add(Constants.izlaz);
        keys.add(Constants.ispis);
        keys.add(Constants.sjemeGeneratora);
        keys.add(Constants.brojDecimala);
        keys.add(Constants.brojRadnihCiklusaZaOdvoz);
        keys.add(Constants.preuzimanje);
        keys.add(Constants.maliMin);
        keys.add(Constants.srednjiMin);
        keys.add(Constants.velikiMin);
        keys.add(Constants.maliStaklo);
        keys.add(Constants.maliPapir);
        keys.add(Constants.maliMetal);
        keys.add(Constants.maliBio);
        keys.add(Constants.maliMjesano);
        keys.add(Constants.srednjiStaklo);
        keys.add(Constants.srednjiPapir);
        keys.add(Constants.srednjiMetal);
        keys.add(Constants.srednjiBio);
        keys.add(Constants.srednjiMjesano);
        keys.add(Constants.velikiStaklo);
        keys.add(Constants.velikiPapir);
        keys.add(Constants.velikiMetal);
        keys.add(Constants.velikiBio);
        keys.add(Constants.velikiMjesano);

        keys.add(Constants.kapacitetDizelVozila);
        keys.add(Constants.punjenjeDizelVozila);
        keys.add(Constants.kapacitetElektroVozila);
        keys.add(Constants.punjenjeElektroVozila);
        keys.add(Constants.dispecer);
        keys.add(Constants.podrucja);

        prop.forEach((k, v) -> {

            //   System.out.println(k + " -> " + v);
            if (!keys.contains((String) k)) {
                if (((String) k).indexOf("ulice") > 0) {

                } else {
                    ReportBuilderDirector director = getReportBuilderDirector();
                    director.addTitleInReport("GREŠKA PRILIKOM UČITAVANJA PARAMETAR!!!", false);
                    director.addTextLineInReport("Greška prilikom dohvaćanja paremetra: " + k, false);
                    director.addDividerLineInReport(false);
                    director.addEmptyLineInReport(false).print();
                    director.addEmptyLineInReport(false).generateFile();
                    System.exit(0);

                }

            }

        }
        );

        keys.forEach(key -> {

            try {
                if (getParameterByKey(key).equals("")) {
                    ReportBuilderDirector director = getReportBuilderDirector();
                    director.addTitleInReport("GREŠKA PRILIKOM UČITAVANJA PARAMETAR!!!", false);
                    director.addTextLineInReport("Greška prilikom dohvaćanja paremetra: " + key, false);
                    director.addDividerLineInReport(false);
                    director.addEmptyLineInReport(false).print();
                    director.addEmptyLineInReport(false).generateFile();
                    System.exit(0);
                } else {
                    switch (key) {
                        case Constants.ulice:
                            // isStringLocal(getParameterByKey(Constants.ulice), Constants.ulice);
                            break;
                        case Constants.spremnici:
                            isStringLocal(getParameterByKey(Constants.spremnici), Constants.spremnici);
                            break;
                        case Constants.podrucja:
                            isStringLocal(getParameterByKey(Constants.podrucja), Constants.podrucja);
                            break;
                        case Constants.dispecer:
                            isStringLocal(getParameterByKey(Constants.dispecer), Constants.dispecer);
                            break;
                        case Constants.vozila:
                            isStringLocal(getParameterByKey(Constants.vozila), Constants.vozila);
                            break;
                        case Constants.izlaz:
                            isStringLocal(getParameterByKey(Constants.izlaz), Constants.izlaz);
                            break;
                        case Constants.ispis:
                            isNumeric(getParameterByKey(Constants.ispis), Constants.ispis);
                            break;
                        case Constants.sjemeGeneratora:
                            isNumeric(getParameterByKey(Constants.sjemeGeneratora), Constants.sjemeGeneratora);
                            break;
                        case Constants.brojDecimala:
                            isNumeric(getParameterByKey(Constants.brojDecimala), Constants.brojDecimala);
                            break;
                        case Constants.brojRadnihCiklusaZaOdvoz:
                            isNumeric(getParameterByKey(Constants.brojRadnihCiklusaZaOdvoz), Constants.brojRadnihCiklusaZaOdvoz);
                            break;
                        case Constants.preuzimanje:
                            isNumeric(getParameterByKey(Constants.preuzimanje), Constants.preuzimanje);
                            break;
                        case Constants.maliMin:
                            isNumeric(getParameterByKey(Constants.maliMin), Constants.maliMin);
                            break;
                        case Constants.srednjiMin:
                            isNumeric(getParameterByKey(Constants.srednjiMin), Constants.srednjiMin);
                            break;
                        case Constants.velikiMin:
                            isNumeric(getParameterByKey(Constants.velikiMin), Constants.velikiMin);
                            break;
                        case Constants.maliStaklo:
                            isNumeric(getParameterByKey(Constants.maliStaklo), Constants.maliStaklo);
                            break;
                        case Constants.maliPapir:
                            isNumeric(getParameterByKey(Constants.maliPapir), Constants.maliPapir);
                            break;
                        case Constants.maliMetal:
                            isNumeric(getParameterByKey(Constants.maliMetal), Constants.maliMetal);
                            break;
                        case Constants.maliBio:
                            isNumeric(getParameterByKey(Constants.maliBio), Constants.maliBio);
                            break;
                        case Constants.maliMjesano:
                            isNumeric(getParameterByKey(Constants.maliMjesano), Constants.maliMjesano);
                            break;
                        case Constants.srednjiStaklo:
                            isNumeric(getParameterByKey(Constants.srednjiStaklo), Constants.srednjiStaklo);
                            break;
                        case Constants.srednjiPapir:
                            isNumeric(getParameterByKey(Constants.srednjiPapir), Constants.srednjiPapir);
                            break;
                        case Constants.srednjiMetal:
                            isNumeric(getParameterByKey(Constants.srednjiMetal), Constants.srednjiMetal);
                            break;
                        case Constants.srednjiBio:
                            isNumeric(getParameterByKey(Constants.srednjiBio), Constants.srednjiBio);
                            break;
                        case Constants.srednjiMjesano:
                            isNumeric(getParameterByKey(Constants.srednjiMjesano), Constants.srednjiMjesano);
                            break;
                        case Constants.velikiStaklo:
                            isNumeric(getParameterByKey(Constants.velikiStaklo), Constants.velikiStaklo);
                            break;
                        case Constants.velikiPapir:
                            isNumeric(getParameterByKey(Constants.velikiPapir), Constants.velikiPapir);
                            break;
                        case Constants.velikiMetal:
                            isNumeric(getParameterByKey(Constants.velikiMetal), Constants.velikiMetal);
                            break;
                        case Constants.velikiBio:
                            isNumeric(getParameterByKey(Constants.velikiBio), Constants.velikiBio);
                            break;
                        case Constants.velikiMjesano:
                            isNumeric(getParameterByKey(Constants.velikiMjesano), Constants.velikiMjesano);
                            break;

                        case Constants.kapacitetDizelVozila:
                            isNumeric(getParameterByKey(Constants.kapacitetDizelVozila), Constants.kapacitetDizelVozila);
                            break;
                        case Constants.punjenjeDizelVozila:
                            isNumeric(getParameterByKey(Constants.punjenjeDizelVozila), Constants.punjenjeDizelVozila);
                            break;
                        case Constants.kapacitetElektroVozila:
                            isNumeric(getParameterByKey(Constants.kapacitetElektroVozila), Constants.kapacitetElektroVozila);
                            break;
                        case Constants.punjenjeElektroVozila:
                            isNumeric(getParameterByKey(Constants.punjenjeElektroVozila), Constants.punjenjeElektroVozila);
                            break;

                    }

                }

            } catch (Exception e) {

                ReportBuilderDirector director = getReportBuilderDirector();
                director.addTitleInReport("GREŠKA PRILIKOM UČITAVANJA PARAMETAR!!!", false);
                director.addTextLineInReport("Greška prilikom dohvaćanja paremetra: " + key, false);
                director.addDividerLineInReport(false);
                director.addEmptyLineInReport(false).print();
                director.addEmptyLineInReport(false).generateFile();
                System.exit(0);

            }

        });

    }

    private void isStringLocal(Object ulice, String key) {
        if (!isString(ulice)) {
            ReportBuilderDirector director = getReportBuilderDirector();
            director.addTitleInReport("GREŠKA PRILIKOM UČITAVANJA PARAMETAR!!!", false);
            director.addTextLineInReport("Greška prilikom dohvaćanja paremetra: " + key, false);
            director.addDividerLineInReport(false);
            director.addEmptyLineInReport(false).print();
            director.addEmptyLineInReport(false).generateFile();
            System.exit(0);
        }

    }

    private void isNumeric(Object parameterByKey, String velikiMjesano) {

        try {
            int i = Integer.parseInt((String) parameterByKey);
        } catch (Exception e) {
            ReportBuilderDirector director = getReportBuilderDirector();
            director.addTitleInReport("GREŠKA PRILIKOM UČITAVANJA PARAMETAR!!!", false);
            director.addTextLineInReport("Greška prilikom dohvaćanja paremetra: " + velikiMjesano, false);
            director.addDividerLineInReport(false);
            director.addEmptyLineInReport(false).print();
            director.addEmptyLineInReport(false).generateFile();
            System.exit(0);

        }

    }

}
