/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.iterator;

/**
 *
 * @author Zoran
 */
public enum TypeOfCommand {
    PRIPREMI("PRIPREMI"),
    KRENI_BEZ_PARAMETRA("KRENI"),
    KRENI_S_PARAMETRIMA("KRENI "),
    KVAR("KVAR"),
    STATUS("STATUS"),
    ISPRAZNI("ISPRAZNI");

  
    // declaring private variable for getting values 
    private String command; 
  
    // getter method 
    public String getCommand() 
    { 
        return this.command; 
    } 
  
    // enum constructor - cannot be public or protected 
    private TypeOfCommand(String command) 
    { 
        this.command = command; 
    } 
}
