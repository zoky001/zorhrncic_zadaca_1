/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.singleton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;
import org.foi.uzdiz.zorhrncic.dz1.shared.Constants;

/**
 *
 * @author Zoran
 */
public class CommonDataSingleton {

    private static CommonDataSingleton instance;

    private HashMap<String, Object> confData = new HashMap<String, Object>();

    private Random generator;
    DecimalFormat df;

    private CommonDataSingleton() {
    }

    public static synchronized CommonDataSingleton getInstance() {
        if (instance == null) {
            instance = new CommonDataSingleton();
        }
        return instance;
    }

    public int getRandomInt() {

        try {

            if (generator == null) {
                createGenerator(Long.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.sjemeGeneratora)));
            }

            return generator.nextInt();

        } catch (Exception e) {

            System.out.println("Greška u generiranju random INT broja");

        }
        return -1;
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

    public float getRandomFloat() {

        try {

            if (generator == null) {
                createGenerator(Long.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.sjemeGeneratora)));
            }
            if (df == null) {
                df = new DecimalFormat();
                df.setMinimumFractionDigits(Integer.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.brojDecimala)));
                df.setMaximumFractionDigits(Integer.valueOf((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.brojDecimala)));
            }

            float myFloat = generator.nextFloat();
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

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                confData.put(data[0], data[1]);

                //        System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
                System.out.println(line);
            }

            confData.forEach((k, v)
                    -> {
                System.out.println("Key : _" + k + "_ Value : _" + v + "_");
            }
            );

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
            return confData.get(path);

        } catch (Exception e) {

            System.out.println("Greška prilikom dohvata podataka.");

        }

        return null;

    }

}
