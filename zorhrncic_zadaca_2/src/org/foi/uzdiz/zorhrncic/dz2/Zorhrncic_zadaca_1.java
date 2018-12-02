/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2;
//zadaca 2
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.foi.uzdiz.zorhrncic.dz2.ezo.Dispecer;
import org.foi.uzdiz.zorhrncic.dz2.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz2.singleton.CommonDataSingleton;

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
        CommonDataSingleton.getInstance().printProp();

        LoadInitData load = new LoadInitData();
        load.loadData();

        Dispecer dispecer = new Dispecer(load.getAllVehicles(), load.getStreets(),load.getAreaRootElement());
        dispecer.startCollecting();;

        CommonDataSingleton.getInstance().getReportBuilderDirector().addEmptyLineInReport(false).print();
        CommonDataSingleton.getInstance().getReportBuilderDirector().addEmptyLineInReport(false).generateFile();
    }

}
