/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3;
//zadaca 2

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.uzdiz.zorhrncic.dz3.ezo.Dispecer;
import org.foi.uzdiz.zorhrncic.dz3.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz3.vt100.VT100Controller;
import org.foi.uzdiz.zorhrncic.dz3.vt100.VT100Model;
import org.foi.uzdiz.zorhrncic.dz3.vt100.VT100View;

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
        if (args.length == 3) {

            try {
                int brg, brd;
                brg = Integer.parseInt(args[1]);
                brd = Integer.parseInt(args[2]);
                if (brg > 0 && brd > 0) {
                    CommonDataSingleton.getInstance().initViewAndReportBuilder(brg, brd);

                } else {
                    throw new Exception("Parametri veličine ekrana moraju biti pozitivni projevi");
                }

            } catch (Exception e) {
                System.out.println("Greška prilikom učitavanja ulaznih parametara");

            }

            CommonDataSingleton.getInstance().loadParametes(args[0]);
            //CommonDataSingleton.getInstance().printProp();

            LoadInitData load = new LoadInitData();
            load.loadData();

            Dispecer dispecer = new Dispecer(load.getAllVehicles(), load.getStreets(), load.getAreaRootElementList());
            dispecer.startCollecting();;

            //    CommonDataSingleton.getInstance().getReportBuilderDirector().addEmptyLineInReport(false).print();
            //   CommonDataSingleton.getInstance().getReportBuilderDirector().addEmptyLineInReport(false).generateFile();
            CommonDataSingleton.getInstance().getVt100Controller().prepareTerminalForExit();

//            // System.out.print(ANSI_ESC + "2J");
//            VT100View vt100view = new VT100View(15, 3);
//            VT100Model vt100model = new VT100Model();
//            VT100Controller vt100 = new VT100Controller(vt100model, vt100view);
//
//            for (int i = 0; i < 100; i++) {
//                vt100.printOutputLine(i + ". redak je ispisan");
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(VT100View.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            //    vt100.clearTerminal();
//            vt100.prepareTerminalForExit();
        } else {
            System.out.println("Nije upisani parametri!!");
        }

    }

}
