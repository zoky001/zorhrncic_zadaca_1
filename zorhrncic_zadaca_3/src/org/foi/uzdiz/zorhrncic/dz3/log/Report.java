/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.uzdiz.zorhrncic.dz3.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;

/**
 *
 * @author Zoran
 */
public class Report {

    private List<OneLine> arrayList;

    public Report() {
        this.arrayList = new ArrayList<>();
    }

    public List<OneLine> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<OneLine> arrayList) {
        this.arrayList = arrayList;
    }

    public void print() {

        boolean isStatistic = false;

        if (((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.ispis)).equalsIgnoreCase(Constants.ISPIS_SVE)) {
            isStatistic = false;
        } else {
            isStatistic = true;
        }

        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }

        OneLine line;
        for (int i = 0; i < arrayList.size(); i++) {
            line = arrayList.get(i);
            if (isStatistic) {

                if (line.isIsStatistic()) {
                    System.out.println(line.toString());
                }

            } else {
                System.out.println(line.toString());
            }

        }

    }

    public void generateFile() {
        String filename = (String) CommonDataSingleton.getInstance().getParameterByKey(Constants.izlaz);
        boolean isStatistic = false;

        if (((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.ispis)).equalsIgnoreCase(Constants.ISPIS_SVE)) {
            isStatistic = false;
        } else {
            isStatistic = true;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

            StringBuilder builder = new StringBuilder();

            OneLine line;
            for (int i = 0; i < arrayList.size(); i++) {
                if (isStatistic) {
                    line = arrayList.get(i);
                    if (line.isIsStatistic()) {
                        builder.append(line.toString() + "\n");
                        bw.write(line.toString());
                        bw.newLine();
                    }

                } else {
                    line = arrayList.get(i);
                    builder.append(line.toString() + "\n");
                    bw.write(line.toString());
                    bw.newLine();
                }

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
