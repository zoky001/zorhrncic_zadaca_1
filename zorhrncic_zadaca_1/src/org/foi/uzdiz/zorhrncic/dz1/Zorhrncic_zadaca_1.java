/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.foi.uzdiz.zorhrncic.dz1.ezo.WasteCollection;
import org.foi.uzdiz.zorhrncic.dz1.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz1.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz1.streets.LoadInitData;

/**
 *
 * @author Zoran
 */
public class Zorhrncic_zadaca_1 {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        CommonDataSingleton.getInstance().loadParametes(args[0]);

        LoadInitData load = new LoadInitData();
        load.loadData();

        WasteCollection wasteCollection = new WasteCollection(load.getAllVehicles(), load.getStreets());
        wasteCollection.startCollecting();;

        CommonDataSingleton.getInstance().getReportBuilderDirector().addEmptyLineInReport(false).print();
        CommonDataSingleton.getInstance().getReportBuilderDirector().addEmptyLineInReport(false).generateFile();
    }

}
