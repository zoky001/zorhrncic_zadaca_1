/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.vt100;

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
        }
    }

    public void printInputLine(String text) {
        this.vT100Model.addLowerTerminaOutputLine(text);

        vT100View.printInputLine(text);
    }

    public String readInputLine() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.next();
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
