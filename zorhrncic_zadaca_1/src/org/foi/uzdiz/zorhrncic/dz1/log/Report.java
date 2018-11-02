/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        arrayList.forEach(line -> {

            System.out.println(line.toString());

        });
    }

}
