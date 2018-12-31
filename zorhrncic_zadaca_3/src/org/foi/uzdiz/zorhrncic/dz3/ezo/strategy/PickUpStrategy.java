/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo.strategy;

import org.foi.uzdiz.zorhrncic.dz3.composite.Street;
import org.foi.uzdiz.zorhrncic.dz3.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz3.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz3.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;

/**
 *
 * @author Zoran
 */
public abstract class PickUpStrategy {

    protected final Vehicle vehicle;
    protected final Street street;
    protected final int location;
    private final ReportBuilderDirector builderDirector;
    private final DispecerContext context;

    public PickUpStrategy(Street street, Vehicle vehicle, DispecerContext context) {
        this.street = street;
        this.vehicle = vehicle;
        this.location = street.getVehicleLocation(vehicle);
        this.context = context;
        this.builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();
    }

    public abstract boolean pickUpInStreet(TypesOfWaste typesOfWaste);

    protected boolean isprazniSpremnik(Spremnik spremnik, Vehicle vehicle, Street street) {
        boolean success = false;

        if (spremnik.getFilled() > 0) {
            float mjestaUVozilu = vehicle.getCapacity() - vehicle.getFilled();

            if (spremnik.getFilled() <= mjestaUVozilu) {
                vehicle.addWaste(spremnik.getFilled());
                spremnik.empty(spremnik.getFilled());
                success = true;
                vehicle.increaseNumberOfProcessedContainers();
                vehicle.addProcessedContainers(spremnik);

                this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Otpad preuzima vozilo:                " + vehicle.getName() + "              Ciklus: " + context.getCycleNumber() + ". ", true);
                this.builderDirector.addDividerLineInReport(true);

                this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), true);
                this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), true);
                this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), true);

                this.builderDirector.addDividerLineInReport(true);

                this.builderDirector.addTextLineInReport("Ulica:            " + street.getName(), true);
                this.builderDirector.addTextLineInReport("Smjer:            " + street.chooseDirection(vehicle, street).name(), true);
                this.builderDirector.addTextLineInReport("Spremnik:         " + street.getVehicleLocation(vehicle), true);

                this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addEmptyLineInReport(true);

                context.setIsAllWasteCollected(false);//isAllWasteCollected = false; //todo check if filled
                if (vehicle.getCapacity() == vehicle.getFilled()) {
                    driveToLandfill(vehicle);
                }
            } else {
                vehicle.addWaste(mjestaUVozilu);
                spremnik.empty(mjestaUVozilu);
                success = true;
                //vehicle.increaseNumberOfProcessedContainers();
                this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addTextLineInReport("Otpad preuzima vozilo:                " + vehicle.getName() + "              Ciklus: " + context.getCycleNumber() + ". ", true);
                this.builderDirector.addDividerLineInReport(true);

                this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), true);
                this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), true);
                this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), true);

                this.builderDirector.addDividerLineInReport(true);
                this.builderDirector.addEmptyLineInReport(true);

                driveToLandfill(vehicle);
                //  isAllWasteCollected = false;
                context.setIsAllWasteCollected(false);//isAllWasteCollected = false; //todo check if filled

            }

        } else {
            success = false;
        }

        return success;

    }

    private void driveToLandfill(Vehicle vehicle) {

        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addTitleInReport("Vožnja kamiona na odlagalište", true);

        this.builderDirector.addDividerLineInReport(true);
        this.builderDirector.addTextLineInReport("Na odlagalište ide vozilo:                    " + vehicle.getName(), true);
        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addTextLineInReport("Količina u vozilu:            " + vehicle.getFilled(), true);
        this.builderDirector.addTextLineInReport("Količina do popunjavanja:     " + (vehicle.getCapacity() - vehicle.getFilled()), true);
        this.builderDirector.addTextLineInReport("Kapacitet:                    " + vehicle.getCapacity(), true);

        this.builderDirector.addDividerLineInReport(true);

        this.builderDirector.addTextLineInReport("Broj vozila na odlagalištu:   " + (context.getAllVehiclesAtLandfill().size() + 1), true);

        this.builderDirector.addTitleInReport("Vožnja kamiona na odlagalište", true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);
        this.builderDirector.addEmptyLineInReport(true);

        vehicle.setLastStreet(context.getSelectedStreetIndex());

        context.getLandfill().vehicleComesToLandfill(vehicle);//getAllVehiclesAtLandfill().add(vehicle);
    }

}
