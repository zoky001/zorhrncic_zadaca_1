/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.singleton;

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
import org.foi.uzdiz.zorhrncic.dz1.log.Report;
import org.foi.uzdiz.zorhrncic.dz1.log.ReportBuilder;
import org.foi.uzdiz.zorhrncic.dz1.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz1.log.ReportBuilderImpl;
import org.foi.uzdiz.zorhrncic.dz1.shared.Constants;

/**
 *
 * @author Zoran
 */
public class CommonDataSingleton {

    private static CommonDataSingleton instance;

    private HashMap<String, Object> confData = new HashMap<String, Object>();
    Properties prop = new Properties();
    private Random generator;
    DecimalFormat df;

    private final ReportBuilder builder;
    private final ReportBuilderDirector builderDirector;

    private CommonDataSingleton() {
        this.builder = new ReportBuilderImpl();
        this.builderDirector = new ReportBuilderDirector(builder);
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

            System.out.println("Greška u generiranju random INT broja");

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

            System.out.println("Greška u generiranju random LONG broja");

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

            System.out.println("Greška u generiranju random FLOAT broja");

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
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                confData.put(data[0], data[1]);

               
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

    public Object getParameterByKey(String key) {
        return getParameterByKeyPrivate(key);
    }

    private Object getParameterByKeyPrivate(String path) {

        try {
            // return confData.get(path);
            return prop.get(path);

        } catch (Exception e) {

            System.out.println("Greška prilikom dohvata podataka.");

        }

        return null;

    }

}
