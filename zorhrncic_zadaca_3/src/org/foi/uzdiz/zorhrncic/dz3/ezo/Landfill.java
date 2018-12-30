/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz3.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz3.waste.BioWaste;
import org.foi.uzdiz.zorhrncic.dz3.waste.GlassWaste;
import org.foi.uzdiz.zorhrncic.dz3.waste.MetalWaste;
import org.foi.uzdiz.zorhrncic.dz3.waste.MixedWaste;
import org.foi.uzdiz.zorhrncic.dz3.waste.PaperWaste;
import org.foi.uzdiz.zorhrncic.dz3.waste.Waste;

/**
 *
 * @author Zoran
 */
public class Landfill {

    private Waste bioWaste;
    private Waste glassWaste;
    private Waste metalWaste;
    private Waste mixedWaste;
    private Waste paperWaste;
    private final ReportBuilderDirector builderDirector;
    private final DispecerContext dispecerContext;

    public Landfill(DispecerContext dispecerContext) {
        this.bioWaste = new BioWaste(0);
        this.glassWaste = new GlassWaste(0);
        this.metalWaste = new MetalWaste(0);
        this.mixedWaste = new MixedWaste(0);
        this.paperWaste = new PaperWaste(0);

        this.dispecerContext = dispecerContext;

        builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();
    }

    public List<Vehicle> getAllVehiclesAtLandfill() {
        return dispecerContext.getAllVehiclesAtLandfill();
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
        vehicle.goToLandFill();

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
        for (int i = 0; i < getAllVehiclesAtLandfill().size(); i++) {
            v = getAllVehiclesAtLandfill().get(i);
            total = total + v.getNumberOfProcessedContainers();
            this.builderDirector.addTextLineInReport(v.getName() + "                       " + (int) v.getNumberOfProcessedContainers(), true);

        }

        this.builderDirector.addDividerLineInReport(true);
        this.builderDirector.addTextLineInReport("Ukupno:                           " + (int) total, true);
        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addTitleInReport("statistika - vozila (broj preuzetih mjesta)", true);

        this.builderDirector.addDividerLineInReport(true);
        Spremnik spremnik;

        for (int i = 0; i < getAllVehiclesAtLandfill().size(); i++) {
            total = 0;
            v = getAllVehiclesAtLandfill().get(i);
            for (int j = 0; j < v.getSpremnikList().size(); j++) {
                spremnik = v.getSpremnikList().get(j);

                total = total + spremnik.getUsersList().size();
            }

            this.builderDirector.addTextLineInReport(v.getName() + "                       " + (int) total, true);

        }

        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addTitleInReport("statistika - vozila (količina preveženog otpada)", true);

        this.builderDirector.addDividerLineInReport(true);

        total = 0;
        for (int i = 0; i < getAllVehiclesAtLandfill().size(); i++) {

            v = getAllVehiclesAtLandfill().get(i);
            total = total + v.getTotal();
            this.builderDirector.addTextLineInReport(v.getName() + "                       " + v.getTotal(), true);

        }
        this.builderDirector.addDividerLineInReport(true);
        this.builderDirector.addTextLineInReport("Ukupno:                           " + total, true);
        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addTitleInReport("statistika - vozila (broj pražnjenja)", true);

        this.builderDirector.addDividerLineInReport(true);

        total = 0;
        for (int i = 0; i < getAllVehiclesAtLandfill().size(); i++) {

            v = getAllVehiclesAtLandfill().get(i);
            total = total + v.getNumberOfDepartures();
            this.builderDirector.addTextLineInReport(v.getName() + "                       " + v.getNumberOfDepartures(), true);

        }
        this.builderDirector.addDividerLineInReport(true);
        this.builderDirector.addTextLineInReport("Ukupno:                           " + total, true);
        this.builderDirector.addDividerLineInReport(true);

    }

    public void vehicleLeavesLandfill(Vehicle vehicle) {
        vehicle.prepareVehicle();
    }
}
