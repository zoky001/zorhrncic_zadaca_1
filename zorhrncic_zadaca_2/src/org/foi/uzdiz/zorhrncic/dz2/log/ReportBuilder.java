/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.log;

/**
 *
 * @author Zoran
 */
public interface ReportBuilder {

    Report build();

    ReportBuilder addLine(OneLine oneLine);

    ReportBuilder print();

}
