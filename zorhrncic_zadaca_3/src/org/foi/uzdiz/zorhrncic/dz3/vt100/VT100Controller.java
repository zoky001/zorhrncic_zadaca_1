/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.vt100;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Zoran
 */
public class VT100Controller {

    private VT100Model vT100Model;
    private VT100View vT100View;

    public VT100Controller(VT100Model vT100Model, VT100View vT100View) {
        this.vT100Model = vT100Model;
        this.vT100View = vT100View;
    }

    public void printOutputLine(String text) {

        this.vT100Model.addUpperTerminaOutputLine(text);
        try {
            if (!vT100View.printOutputLine(text)) {
                printInputLine("za nastavak treba pritisnuti tipka n/N");
                String response = readInputLine();
                if (!response.equalsIgnoreCase("N")) {
                    printInputLine("Izlazak...");
                    prepareTerminalForExit();
                    System.exit(0); //todo 
                } else {
                    printInputLine("Nastavak...");

                }

                // clearInputTerminal();
                vT100View.clearOutputTerminal();
                printOutputLine(text);
            }
        } catch (Exception e) {
            printInputLine("Izlazak...");
            prepareTerminalForExit();
            System.exit(0); //todo 
        }

    }

    public void printInputLine(String text) {
        this.vT100Model.addLowerTerminaOutputLine(text);

        vT100View.printInputLine(text);
    }

    public String readInputLine() {
        try {
            //     Scanner sc = new Scanner(System.in, "UTF8");

            //     return sc.nextLine();
            //Enter data using BufferReader 
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF8"));

            // Reading data using readLine 
            return reader.readLine();

        } catch (Exception e) {

            return "";

        }
    }

    public void prepareTerminalForExit() {
        vT100View.exit();
    }

    public VT100Model getvT100Model() {
        return vT100Model;
    }

    public void setvT100Model(VT100Model vT100Model) {
        this.vT100Model = vT100Model;
    }

    public VT100View getvT100View() {
        return vT100View;
    }

    public void setvT100View(VT100View vT100View) {
        this.vT100View = vT100View;
    }

}
