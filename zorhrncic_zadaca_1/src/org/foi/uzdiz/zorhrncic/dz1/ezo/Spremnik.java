/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.ezo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.foi.uzdiz.zorhrncic.dz1.bridge.Tank;
import org.foi.uzdiz.zorhrncic.dz1.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfUser;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz1.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz1.users.User;

/**
 *
 * @author Zoran
 */
public abstract class Spremnik {

    private static int idIncrement = 0;

    private Tank tank;

    int id;
    // String kindOfWaste;
    int numberOfSmall;
    int numberOfMedium;
    int numberOfLarge;
    // float capacity;
    //  private float filled;

    TypesOfWaste kindOfWaste;
    private TypesOfUser typesOfUser;
    public List<User> usersList = new ArrayList<>();

    public Spremnik() {
    }

    public Spremnik(TypesOfWaste kindOfWaste) {
        this.kindOfWaste = kindOfWaste;
    }

    public Spremnik(Spremnik target) {
        if (target != null) {
            this.id = idIncrement++;
            this.kindOfWaste = target.kindOfWaste;
            this.numberOfSmall = target.numberOfSmall;
            this.numberOfMedium = target.numberOfMedium;
            this.numberOfLarge = target.numberOfLarge;
            // this.capacity = target.capacity;
            this.typesOfUser = target.typesOfUser;
            this.tank = target.tank.clone();
            usersList = new ArrayList<>();
            // this.filled = 0;
        }

    }

    public abstract Spremnik clone();

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof Spremnik)) {
            return false;
        }
        Spremnik spremnik = (Spremnik) object2;
        return spremnik.id == id && Objects.equals(spremnik.typesOfUser, typesOfUser) && Objects.equals(spremnik.kindOfWaste, kindOfWaste) && spremnik.numberOfSmall == numberOfSmall && spremnik.numberOfMedium == numberOfMedium && spremnik.numberOfLarge == numberOfLarge;

    }

    public static void printArray(List<Spremnik> sviTipoviSpremnika) {
        
        ReportBuilderDirector builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();
        sviTipoviSpremnika.forEach((k) -> {

            if (k.getCapacity() < k.getFilled()) {
                builderDirector.addTextLineInReport("Višak", false);
            }

          
              builderDirector.addTextLineInReport("ID: " + k.getId(), false);

 
              builderDirector.addTextLineInReport("TIP: ", false);

            if (k instanceof Kanta) {
               
                 builderDirector.addTextLineInReport(" Kanta", false);
            } else if (k instanceof Kontejner) {
           
                 builderDirector.addTextLineInReport(" Kontejner", false);
            }
     
            builderDirector.addTextLineInReport("Name: " + k.getKindOfWaste(), false);
            builderDirector.addTextLineInReport("Vrsta korisnika: " + k.getTypesOfUser().name(), false);
            builderDirector.addTextLineInReport("Broj korisnika: " + k.getUsersList().size(), false);
            
            
            builderDirector.addTextLineInReport("Broj kapacitet: " + k.getCapacity(), false);
            builderDirector.addTextLineInReport("Broj popunjen: " + k.getFilled(), false);
            builderDirector.addTextLineInReport("Broj malih: " + k.getNumberOfSmall(), false);
            builderDirector.addTextLineInReport("Broj srednjih: " + k.getNumberOfMedium(), false);
            builderDirector.addTextLineInReport("Broj velikih: " + k.getNumberOfLarge(), false);
            builderDirector.addDividerLineInReport(false);
            

        });
    }

    public void addWaste(float amount) {
        //this.filled = this.filled + amount;
        this.tank.fill(amount);
    }

    public static int getIdIncrement() {
        return idIncrement;
    }

    public static void setIdIncrement(int idIncrement) {
        Spremnik.idIncrement = idIncrement;
    }

    public int getId() {
        return id;
    }

    public TypesOfWaste getKindOfWaste() {
        return kindOfWaste;
    }

    public void setKindOfWaste(TypesOfWaste kindOfWaste) {
        this.kindOfWaste = kindOfWaste;
    }

    public int getNumberOfSmall() {
        return numberOfSmall;
    }

    public void setNumberOfSmall(int numberOfSmall) {
        this.numberOfSmall = numberOfSmall;
    }

    public int getNumberOfMedium() {
        return numberOfMedium;
    }

    public void setNumberOfMedium(int numberOfMedium) {
        this.numberOfMedium = numberOfMedium;
    }

    public int getNumberOfLarge() {
        return numberOfLarge;
    }

    public void setNumberOfLarge(int numberOfLarge) {
        this.numberOfLarge = numberOfLarge;
    }

    public float getCapacity() {
        //  return capacity;

        return this.tank.getCapacity();

    }

    public TypesOfUser getTypesOfUser() {
        return typesOfUser;
    }

    public void setTypesOfUser(TypesOfUser typesOfUser) {
        this.typesOfUser = typesOfUser;
    }

    public void addUser(User user) {
        this.usersList.add(user);
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public float getFilled() {
        return this.tank.getFilled();
    }

    public void empty(float filled) {
        // this.filled = this.filled - filled;

        this.tank.empty(filled);
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

}
