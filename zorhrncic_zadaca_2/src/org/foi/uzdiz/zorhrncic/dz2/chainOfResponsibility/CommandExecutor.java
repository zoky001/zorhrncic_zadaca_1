/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.chainOfResponsibility;

import org.foi.uzdiz.zorhrncic.dz2.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz2.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz2.iterator.CommandRepository;
import org.foi.uzdiz.zorhrncic.dz2.iterator.TypeOfCommand;
import org.foi.uzdiz.zorhrncic.dz2.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz2.singleton.CommonDataSingleton;

/**
 *
 * @author Zoran
 */
public abstract class CommandExecutor {

    protected TypeOfCommand typeOfCommand;
// The next element in the chain of responsibility
    protected CommandExecutor next;
    protected final ReportBuilderDirector builderDirector;

    Command command;
    DispecerContext context;

    public CommandExecutor() {

        builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();

    }

    public CommandExecutor setNext(CommandExecutor l) {
        next = l;
        return this;
    }

    public DispecerContext executeCommand(Command command, DispecerContext dispecerContext) throws Exception {

        if (command.getTypeOfCommand() == this.typeOfCommand) {
            return executeCommandPrivate(command, dispecerContext);

        } else {
            if (next != null) {
                return next.executeCommand(command, dispecerContext);
            } else {

                builderDirector.addErrorInReport("Ne postoji implementacije izvršavanja sljedeće komande! " + command.getTypeOfCommand().getCommand(), true);
                throw new Exception("Ne postoji implementacije izvršavanja sljedeće komande! " + command.getTypeOfCommand().getCommand());
            }
        }

    }

    abstract protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext);
}
