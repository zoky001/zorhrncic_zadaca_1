/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.log;

import java.util.Date;
import org.foi.uzdiz.zorhrncic.dz2.shared.Constants;

/**
 *
 * @author Zoran
 */
public class ReportBuilderDirector {

    private ReportBuilder builder;

    private long id;

    public ReportBuilderDirector(ReportBuilder builder) {
        this.builder = builder;
        this.id = 0;
    }

    public Report addTitleInReport(String line, boolean isStatistic) {
        addEmptyLineInReport(isStatistic);
        String divider = Constants.ANSI_BLUE + "*****************************************************************************************" + Constants.ANSI_RESET;
        addTextLineInReport(divider, isStatistic);
        divider = Constants.ANSI_BLUE + "                    " + line.toUpperCase() + Constants.ANSI_RESET;
        OneLine oneLine = new OneLine(new Date(), divider, id++, isStatistic);
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
        builder.addLine(oneLine).build();

        divider = Constants.ANSI_BLUE + "##########################################################################################" + Constants.ANSI_RESET;
        addTextLineInReport(divider, isStatistic);
        addTextLineInReport(divider, isStatistic);
        return addTextLineInReport(divider, isStatistic);
        //  addDividerLineInReport();

    }

    public Report addTextLineInReport(String line, boolean isStatistic) {

        OneLine oneLine = new OneLine(new Date(), line, id++, isStatistic);
        return builder.addLine(oneLine).build();

    }

    public Report addEmptyLineInReport(boolean isStatistic) {

        OneLine oneLine = new OneLine(new Date(), " ", id++, isStatistic);
        return builder.addLine(oneLine).build();

    }

    public Report addDividerLineInReport(boolean isStatistic) {
        String divider = "-----------------------------------------------------------------------------------------";
        OneLine oneLine = new OneLine(new Date(), divider, id++, isStatistic);
        return builder.addLine(oneLine).build();

    }

}
