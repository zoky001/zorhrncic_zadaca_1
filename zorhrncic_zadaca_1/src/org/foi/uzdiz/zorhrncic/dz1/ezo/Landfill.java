/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz1.ezo;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz1.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz1.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz1.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz1.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz1.waste.BioWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.GlassWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.MetalWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.MixedWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.PaperWaste;
import org.foi.uzdiz.zorhrncic.dz1.waste.Waste;

/**
 *
 * @author Zoran
 */
public class Landfill {

    private List<Vehicle> allVehiclesAtLandfill;

    private Waste bioWaste;
    private Waste glassWaste;
    private Waste metalWaste;
    private Waste mixedWaste;
    private Waste paperWaste;
    private final ReportBuilderDirector builderDirector;

    public Landfill() {
        this.allVehiclesAtLandfill = new ArrayList<Vehicle>();
        this.bioWaste = new BioWaste(0);
        this.glassWaste = new GlassWaste(0);
        this.metalWaste = new MetalWaste(0);
        this.mixedWaste = new MixedWaste(0);
        this.paperWaste = new PaperWaste(0);

        builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();
    }

    public List<Vehicle> getAllVehiclesAtLandfill() {
        return allVehiclesAtLandfill;
    }

    public void setAllVehiclesAtLandfill(List<Vehicle> allVehiclesAtLandfill) {
        this.allVehiclesAtLandfill = allVehiclesAtLandfill;
    }

    public void vehicleComesToLandfill(Vehicle vehicle) {
        vehicle.resetNumberOfCyclesAtLandfill();
        float amount;

        if (vehicle instanceof VehicleBio) {
            amount = this.bioWaste.getAmount();
            amount = amount + vehicle.getFilled();
            this.bioWaste.setAmount(amount);
            // vehicle.setFilled(0);
            vehicle.emptyVehicle();
        } else if (vehicle instanceof VehicleGlass) {
            amount = this.glassWaste.getAmount();
            amount = amount + vehicle.getFilled();
            this.glassWaste.setAmount(amount);
            vehicle.emptyVehicle();
        } else if (vehicle instanceof VehicleMetal) {
            amount = this.metalWaste.getAmount();
            amount = amount + vehicle.getFilled();
            this.metalWaste.setAmount(amount);
            vehicle.emptyVehicle();
        } else if (vehicle instanceof VehicleMixed) {
            amount = this.mixedWaste.getAmount();
            amount = amount + vehicle.getFilled();
            this.mixedWaste.setAmount(amount);
            vehicle.emptyVehicle();
        } else if (vehicle instanceof VehiclePaper) {
            amount = this.paperWaste.getAmount();
            amount = amount + vehicle.getFilled();
            this.paperWaste.setAmount(amount);
            vehicle.emptyVehicle();
        }

        getAllVehiclesAtLandfill().add(vehicle);

    }

    public void creteReport() {
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addTitleInReport("statistika - odlagalište (količina otpada)", true);

        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addTextLineInReport("Količina stakla:                  " + glassWaste.getAmount(), true);
        this.builderDirector.addTextLineInReport("Količina papira:                  " + paperWaste.getAmount(), true);
        this.builderDirector.addTextLineInReport("Količina metala:                  " + metalWaste.getAmount(), true);
        this.builderDirector.addTextLineInReport("Količina bio:                     " + bioWaste.getAmount(), true);
        this.builderDirector.addTextLineInReport("Količina mješano:                 " + mixedWaste.getAmount(), true);

        this.builderDirector.addDividerLineInReport(true);
        float suma = glassWaste.getAmount() + paperWaste.getAmount() + metalWaste.getAmount() + bioWaste.getAmount() + mixedWaste.getAmount();
        this.builderDirector.addTextLineInReport("Ukupno:                           " + suma, true);

        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addTitleInReport("statistika - vozila (broj preuzetih spremnika)", true);

        this.builderDirector.addDividerLineInReport(true);
        float total = 0;
        Vehicle v;
        for (int i = 0; i < allVehiclesAtLandfill.size(); i++) {
            v = allVehiclesAtLandfill.get(i);
            total = total + v.getNumberOfProcessedContainers();
            this.builderDirector.addTextLineInReport(v.getName() + "                       " + (int) v.getNumberOfProcessedContainers(), true);

        }

        this.builderDirector.addDividerLineInReport(true);
        this.builderDirector.addTextLineInReport("Ukupno:                           " + (int) total, true);
        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addTitleInReport("statistika - vozila (broj preuzetih mjesta)", true);

        this.builderDirector.addDividerLineInReport(true);
        Spremnik spremnik;

        for (int i = 0; i < allVehiclesAtLandfill.size(); i++) {
            total = 0;
            v = allVehiclesAtLandfill.get(i);
            for (int j = 0; j < v.getSpremnikList().size(); j++) {
                spremnik = v.getSpremnikList().get(j);

                total = total + spremnik.getUsersList().size();
            }

            this.builderDirector.addTextLineInReport(v.getName() + "                       " + (int) total, true);

        }

        this.builderDirector.addDividerLineInReport(true);

    }

    public void vehicleLeavesLandfill(Vehicle vehicle) {
        getAllVehiclesAtLandfill().remove(vehicle);
    }
}
