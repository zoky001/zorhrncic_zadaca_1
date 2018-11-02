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
        // TODO code application logic here

    //    System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
        //   System.out.println("parametrgggggi: " + args[0]);

        CommonDataSingleton.getInstance().loadParametes(args[0]);
/*
        System.out.println("KEY: " + Constants.vozila + " Value: " + (String) CommonDataSingleton.getInstance().getParameterByKey(Constants.vozila));
        System.out.println("KEY: " + Constants.ulice + " Value: " + (String) CommonDataSingleton.getInstance().getParameterByKey(Constants.ulice));
        System.out.println("KEY: " + Constants.spremnici + " Value: " + (String) CommonDataSingleton.getInstance().getParameterByKey(Constants.spremnici));

*/
        /*   System.out.println("vozila: " + CommonDataSingleton.getInstance().getParameterByKey("vozila"));
        System.out.println("rand INT: " + CommonDataSingleton.getInstance().getRandomInt(8, 10));
        System.out.println("rand INT: " + CommonDataSingleton.getInstance().getRandomInt(12, 25));
        System.out.println("rand INT: " + CommonDataSingleton.getInstance().getRandomInt(5, 50));

        
        System.out.println("rand LONG: " + CommonDataSingleton.getInstance().getRandomLong());
        System.out.println("rand LONG: " + CommonDataSingleton.getInstance().getRandomLong());
        System.out.println("rand LONG: " + CommonDataSingleton.getInstance().getRandomLong());
        
        
        System.out.println("rand FLOAT: " + CommonDataSingleton.getInstance().getRandomFloat(new Float(0.00),new Float(5.00)));
        System.out.println("rand FLOAT: " + CommonDataSingleton.getInstance().getRandomFloat(new Float(5.00),new Float(10.00)));
        System.out.println("rand FLOAT: " + CommonDataSingleton.getInstance().getRandomFloat(new Float(10.00),new Float(15.00)));

         */
        LoadInitData load = new LoadInitData();
        load.loadData();

        WasteCollection wasteCollection = new WasteCollection(load.getAllVehicles(), load.getStreets());
        wasteCollection.startCollecting();;

        CommonDataSingleton.getInstance().getReportBuilderDirector().addEmptyLineInReport().print();
    }

}
