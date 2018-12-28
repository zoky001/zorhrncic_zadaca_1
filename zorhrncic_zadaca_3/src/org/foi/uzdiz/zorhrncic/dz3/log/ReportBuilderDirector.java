/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.log;

import java.util.Date;
import org.foi.uzdiz.zorhrncic.dz3.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;
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

    public ReportBuilderDirector(ReportBuilder builder, VT100Controller controller) {
        this.builder = builder;
        this.id = 0;

        vt100 = controller;
    }

    public Report addTitleInReport(String line, boolean isStatistic) {

        addEmptyLineInReport(isStatistic);
        String divider = Constants.ANSI_BLUE + "*****************************************************************************************" + Constants.ANSI_RESET;



        addTextLineInReport(divider, isStatistic);
        divider = Constants.ANSI_BLUE + "                    " + line.toUpperCase() + Constants.ANSI_RESET;
        OneLine oneLine = new OneLine(new Date(), divider, id++, isStatistic);
        printLine(oneLine);

        builder.addLine(oneLine).build();

        divider = Constants.ANSI_BLUE + "*****************************************************************************************" + Constants.ANSI_RESET;
        return addTextLineInReport(divider, isStatistic);
        //  addDividerLineInReport();

    }

    public Report addErrorInReport(String line, boolean isStatistic) {
        addEmptyLineInReport(isStatistic);
        String divider = Constants.ANSI_BLUE + "##########################################################################################" + Constants.ANSI_RESET;

        addTextLineInReport(divider, isStatistic);
        addTextLineInReport(divider, isStatistic);
        addTextLineInReport(divider, isStatistic);

        divider = Constants.ANSI_BLUE + "                    " + line.toUpperCase() + Constants.ANSI_RESET;
        OneLine oneLine = new OneLine(new Date(), divider, id++, isStatistic);
        printLine(oneLine);
        builder.addLine(oneLine).build();

        divider = Constants.ANSI_BLUE + "##########################################################################################" + Constants.ANSI_RESET;
        addTextLineInReport(divider, isStatistic);
        addTextLineInReport(divider, isStatistic);
        return addTextLineInReport(divider, isStatistic);
        //  addDividerLineInReport();

    }

    public Report addTextLineInReport(String line, boolean isStatistic) {

        OneLine oneLine = new OneLine(new Date(), line, id++, isStatistic);
        printLine(oneLine);

        return builder.addLine(oneLine).build();

    }

    public Report addEmptyLineInReport(boolean isStatistic) {
        OneLine oneLine = new OneLine(new Date(), " ", id++, isStatistic);
        printLine(oneLine);

        return builder.addLine(oneLine).build();

    }

    public Report addDividerLineInReport(boolean isStatistic) {
        String divider = "-----------------------------------------------------------------------------------------";

        OneLine oneLine = new OneLine(new Date(), divider, id++, isStatistic);
        printLine(oneLine);

        return builder.addLine(oneLine).build();

    }

    public void printLine(OneLine line) {

        boolean isStatistic = false;

        if (((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.ispis)).equalsIgnoreCase(Constants.ISPIS_SVE)) {
            isStatistic = false;
        } else {
            isStatistic = true;
        }

        if (isStatistic) {

            if (line.isIsStatistic()) {
                // System.out.println(line.toString());
                vt100.printOutputLine(line.getData());

            }

        } else {
            vt100.printOutputLine(line.getData());
        }

    }

}
