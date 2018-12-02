/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.chainOfResponsibility;

import org.foi.uzdiz.zorhrncic.dz2.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz2.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz2.iterator.TypeOfCommand;

/**
 *
 * @author Zoran
 */
public class CommandExecutorStatus extends CommandExecutor {

    public CommandExecutorStatus() {
        this.typeOfCommand = typeOfCommand.STATUS;
    }

    @Override
    protected DispecerContext executeCommandPrivate(Command command, DispecerContext dispecerContext) {
        System.out.println("JA SAM COMMAND EXECUTOR __ STATUS ___ : " + command.getTypeOfCommand().getCommand());
        return dispecerContext;
    }
}
