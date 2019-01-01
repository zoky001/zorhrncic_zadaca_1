/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.log;

import java.util.Date;
import org.foi.uzdiz.zorhrncic.dz3.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz3.vt100.VT100Color;
import org.foi.uzdiz.zorhrncic.dz3.vt100.VT100Controller;
import org.foi.uzdiz.zorhrncic.dz3.vt100.VT100Model;
import org.foi.uzdiz.zorhrncic.dz3.vt100.VT100View;

/**
 *
 * @author Zoran
 */
public class ReportBuilderDirector {

    private ReportBuilder builder;

    private long id;
    private final VT100Controller vt100;
    private VT100Color defaultColor = VT100Color.DEFAULT;

    public ReportBuilderDirector(ReportBuilder builder, VT100Controller controller) {
        this.builder = builder;
        this.id = 0;

        vt100 = controller;
    }

    public Report addTitleInReport(String line, boolean isStatistic) {

        addEmptyLineInReport(isStatistic);
        String divider = "*****************************************************************************************";

        addTextLineInReport(divider, isStatistic);
        divider = "   " + line.toUpperCase();
        OneLine oneLine = new OneLine(new Date(), divider, id++, isStatistic);
        printLine(oneLine, VT100Color.BLUE);

        builder.addLine(oneLine).build();

        divider = "*****************************************************************************************";
        return addTextLineInReport(divider, isStatistic);
        //  addDividerLineInReport();

    }

    public Report addErrorInReport(String line, boolean isStatistic) {
        addEmptyLineInReport(isStatistic);
        String divider = "#####################################################################################################";

        addTextLineInReport(divider, isStatistic);
        //addTextLineInReport(divider, isStatistic);
        //addTextLineInReport(divider, isStatistic);

        divider = "   " + line.toUpperCase();
        OneLine oneLine = new OneLine(new Date(), divider, id++, isStatistic);
        printLine(oneLine, VT100Color.RED);
        builder.addLine(oneLine).build();

        divider = "#####################################################################################################";
        //  addTextLineInReport(divider, isStatistic);
        // addTextLineInReport(divider, isStatistic);
        return addTextLineInReport(divider, isStatistic);
        //  addDividerLineInReport();

    }

    public Report addTextLineInReport(String line, boolean isStatistic) {

        OneLine oneLine = new OneLine(new Date(), line, id++, isStatistic);
        printLine(oneLine, this.defaultColor);

        return builder.addLine(oneLine).build();

    }

    public Report addEmptyLineInReport(boolean isStatistic) {
        OneLine oneLine = new OneLine(new Date(), " ", id++, isStatistic);
        printLine(oneLine, VT100Color.DEFAULT);

        return builder.addLine(oneLine).build();

    }

    public Report addDividerLineInReport(boolean isStatistic) {
        String divider = "-----------------------------------------------------------------------------------------";

        OneLine oneLine = new OneLine(new Date(), divider, id++, isStatistic);
        printLine(oneLine, this.defaultColor);

        return builder.addLine(oneLine).build();

    }

    public void setColor(VT100Color color, boolean isStatistic) {
        this.defaultColor = color;
    }

    public void printLine(OneLine line, VT100Color color) {

        boolean isStatistic = false;

        if (((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.ispis)).equalsIgnoreCase(Constants.ISPIS_SVE)) {
            isStatistic = false;
        } else {
            isStatistic = true;
        }

        if (isStatistic) {

            if (line.isIsStatistic()) {
                // System.out.println(line.toString());
                // vt100.printOutputLine(line.getData());
                vt100.printOutputLineInColor(line.getData(), color);

            }

        } else {
            //vt100.printOutputLine(line.getData());
            vt100.printOutputLineInColor(line.getData(), color);
            // System.out.println(line.toString());

        }

    }

}
