/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.vt100;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zoran
 */
public class VT100View {

    int screenHight = 25;

    int middleDivider = 25;

    int firstTerminalColumn = 1;

    int firstTerminalLine = 1;

    int lastTerminalLine = 53;

    int lastTerminalColumn = 80;

    int currentOutputLine = 2;

    int firstOutputLine = 2;
    int lastOutputLine = 26;

    int firstOutputColumn = 2;
    int lastOutputColumn = 79;

    int firstInputLine = 28;
    int lastInputLine = 52;

    int firstInputColumn = 2;
    int lastInputColumn = 79;

    public static final String ANSI_ESC = "\033[";

    private final int brd;
    private final int brg;
    private int currentInputLine;

    public VT100View(int brg, int brd) {

        this.brg = brg;
        this.lastOutputLine = brg + 1;

        this.firstInputLine = this.lastOutputLine + 2;
        this.brd = brd;
        this.lastInputLine = this.firstInputLine + brd - 1;

        this.lastTerminalLine = brg + brd + 3;

        this.middleDivider = brg + 2;
        this.currentOutputLine = 2;
        this.currentInputLine = brg + 3;

        this.init();
    }

    private void init() {
        clearTerminal();

        for (int i = firstTerminalLine; i <= lastTerminalLine; i++) {

            if (i == firstTerminalLine) {
                for (int j = firstTerminalColumn; j <= lastTerminalColumn; j++) {
                    prikazi(i, j, 92, "-");
                }
            }

            if (i == middleDivider) {
                for (int j = firstTerminalColumn; j <= lastTerminalColumn; j++) {
                    prikazi(i, j, 92, "-");
                }
            }

            if (i == lastTerminalLine) {
                for (int j = firstTerminalColumn; j <= lastTerminalColumn; j++) {
                    prikazi(i, j, 92, "_");
                }
            }

            prikazi(i, firstTerminalColumn, 92, "|");
            prikazi(i, lastTerminalColumn, 92, "|");

        }
    }

    public boolean printOutputLine(String text) {
        if (currentOutputLine <= lastOutputLine) {
            prikazi(currentOutputLine, firstOutputColumn, 37, text);
            currentOutputLine++;
            return true;
        } else {
            return false;

        }

    }

    public boolean printOutputLineInColor(String text, VT100Color color) {
        if (currentOutputLine <= lastOutputLine) {
            prikazi(currentOutputLine, firstOutputColumn, color.getColor(), text);
            currentOutputLine++;
            return true;
        } else {
            return false;

        }

    }

    public void clearOutputTerminal() {

        for (int i = firstOutputLine; i <= lastOutputLine; i++) {
            deleteLine(i);
            currentOutputLine = firstOutputLine;

        }

    }

    private void clearInputTerminal() {

        for (int i = firstInputLine; i <= lastInputLine; i++) {
//            for (int j = firstInputColumn; j <= lastInputColumn; j++) {
//                prikazi(i, j, 37, " ");
//
//            }
            deleteLine(i);

            currentInputLine = firstInputLine;

        }

    }

    public void printInputLine(String text) {

        if (currentInputLine > lastInputLine) {
            clearInputTerminal();
        }
        prikazi(currentInputLine, firstInputColumn, 91, text);
        postavi(currentInputLine, firstInputColumn + text.length() + 1);
        prikazi(currentInputLine, firstInputColumn + text.length() + 1, 37, "");

        currentInputLine++;
// todo 
    }

    public void clearTerminal() {
        System.out.print(ANSI_ESC + "2J");
    }

    static void postavi(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    static void deleteLine(int x) {
        postavi(x, 79);
        System.out.print(ANSI_ESC + "1K");
        prikazi(x, 1, 92, "|");

    }

    static void prikazi(int x, int y, int boja, String tekst) {
        postavi(x, y);
        System.out.print(ANSI_ESC + boja + "m");
        if (tekst.length() > 78) {
            System.out.print(tekst.substring(0, 78));
        } else {
            System.out.print(tekst);

        }

    }

    static void pozadina(int x, int y, int boja, String tekst) {
        postavi(x, y);
        System.out.print(ANSI_ESC+"48;5;" + 15 + "m");
        System.out.print(ANSI_ESC + boja + "m");
        if (tekst.length() > 78) {
            System.out.print(tekst.substring(0, 78));
        } else {
            System.out.print(tekst);

        }

    }

    public int getScreenHight() {
        return screenHight;
    }

    public void setScreenHight(int screenHight) {
        this.screenHight = screenHight;
    }

    public int getScreenWidth() {
        return lastTerminalColumn;
    }

    public void setScreenWidth(int screenWidth) {
        this.lastTerminalColumn = screenWidth;
    }

    public void exit() {
        postavi(lastTerminalLine + 1, firstTerminalColumn);
    }

}
