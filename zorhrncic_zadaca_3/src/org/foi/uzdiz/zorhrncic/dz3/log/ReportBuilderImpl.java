/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.log;

/**
 *
 * @author Zoran
 */
public class ReportBuilderImpl implements ReportBuilder {

    private Report report;

    public ReportBuilderImpl() {
        this.report = new Report();
    }

    @Override
    public Report build() {
        return report;
    }

    @Override
    public ReportBuilder addLine(OneLine oneLine) {
        this.report.getArrayList().add(oneLine);
        return this;
    }

    @Override
    public ReportBuilder print() {
        this.report.getArrayList().forEach(line -> {

            line.toString();

        });
        return this;
    }

}
