/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.ezo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.parser.TokenType;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorPripremi;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutor;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorBolovanje;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorGodisnjiOdmor;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorIsprazni;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorIzlaz;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorKontrola;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorKreniWithParameters;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorKreniWithoutParameters;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorKvar;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorNovi;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorObradi;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorOtkaz;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorPreuzmi;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorStatus;
import org.foi.uzdiz.zorhrncic.dz3.chainOfResponsibility.CommandExecutorVozaci;
import org.foi.uzdiz.zorhrncic.dz3.composite.CompositePlace;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleBio;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleGlass;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleMetal;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehicleMixed;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.VehiclePaper;
import org.foi.uzdiz.zorhrncic.dz3.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz3.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfWaste;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;
import org.foi.uzdiz.zorhrncic.dz3.composite.Street;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.Driver;
import org.foi.uzdiz.zorhrncic.dz3.iterator.Command;
import org.foi.uzdiz.zorhrncic.dz3.iterator.CommandRepository;
import org.foi.uzdiz.zorhrncic.dz3.iterator.TypeOfCommand;
import org.foi.uzdiz.zorhrncic.dz3.waste.BioWaste;
import org.foi.uzdiz.zorhrncic.dz3.iterator.IIterator;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypeOfDriverState;
import org.foi.uzdiz.zorhrncic.dz3.vt100.VT100Controller;

/**
 *
 * @author Zoran
 */
public class Dispecer {

    DispecerContext dispecerContext;

    private List<Vehicle> allVehiclesInProcess;
    ///private List<Vehicle> allVehiclesAtLandfill;
    private List<Street> streets;
    private Vehicle vehicleInProcess;
    private int cycleNumber = 0;
    private boolean isAllWasteCollected = false;
    private boolean isVehicleSelectsStreet = false;
    private ArrayList<Integer> randomStreetArray;
    private int numberOfCyclesAtLandfill;
    private Street selectedStreet;
    private int selectedStreetIndex;
    private final ReportBuilderDirector builderDirector;
    private final CommandRepository commandRepository;
    private final CommandExecutor commandExecutor;
    private final VT100Controller vt100;

    public Dispecer(List<Vehicle> allVehicles, List<Street> streets, List<CompositePlace> areaRoot) {
        this.dispecerContext = new DispecerContext(allVehicles, streets, areaRoot);
        vt100 = CommonDataSingleton.getInstance().getVt100Controller();
        this.commandRepository = new CommandRepository(this.dispecerContext);

        this.commandExecutor = new CommandExecutorPripremi()
                .setNext(new CommandExecutorKreniWithParameters()
                        .setNext(new CommandExecutorBolovanje()
                                .setNext(new CommandExecutorOtkaz()
                                        .setNext(new CommandExecutorGodisnjiOdmor()
                                                .setNext(new CommandExecutorIzlaz()
                                                        .setNext(new CommandExecutorNovi()
                                                                .setNext(new CommandExecutorObradi()
                                                                        .setNext(new CommandExecutorPreuzmi()
                                                                                .setNext(new CommandExecutorVozaci()
                                                                                        .setNext(new CommandExecutorKvar()
                                                                                                .setNext(new CommandExecutorStatus()
                                                                                                        .setNext(new CommandExecutorIsprazni()
                                                                                                                .setNext(new CommandExecutorKreniWithoutParameters()
                                                                                                                        .setNext(new CommandExecutorKontrola()))))))))))))));

        this.allVehiclesInProcess = allVehicles;
        this.streets = streets;

        builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();
        try {
            this.numberOfCyclesAtLandfill = Integer.parseInt((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.brojRadnihCiklusaZaOdvoz));

        } catch (Exception e) {
            builderDirector.addErrorInReport("Greška prilikom učitavanja broja radnih ciklusa za odvoz!", false);
            System.exit(0);
        }
    }

    public void startCollecting() {

        for (IIterator iterator = commandRepository.getIterator(); iterator.hasNext();) {
            try {
                Command command = iterator.next();
                //System.out.println(command.getTypeOfCommand());
                dispecerContext = commandExecutor.executeCommand(command, dispecerContext);
                assignFreeDriversToVehicles();
            } catch (Exception ex) {
                Logger.getLogger(Dispecer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        readCommandsFromConsole();
    }

    private void readCommandsFromConsole() {
        try {
            vt100.printInputLine("Unos komande:");
            String line = vt100.readInputLine();
            if (line == null) {
                throw new Exception();
            }
            vt100.printInputLine("Izvršavam: " + line);

            Command command = parseLineToCommand(line);
            if (command != null) {
                dispecerContext = commandExecutor.executeCommand(command, dispecerContext);
                assignFreeDriversToVehicles();
                readCommandsFromConsole();
            } else {
                vt100.printInputLine("Pogrešna komanda!!");
                readCommandsFromConsole();
            }

        } catch (Exception e) {
            vt100.printInputLine("Izlazak...");
            CommonDataSingleton.getInstance().exitFromProgram();
        }
    }

    private Command parseLineToCommand(String line) {
        Command newCommand = null;
        try {
            String cvsSplitBy = ";";
            // use comma as separator
            String[] data = line.split(cvsSplitBy);

            if (data.length < 1 || data.length > 3) {
                throw new Exception("Pogrešna komanda!! Unos: \"" + line.toString() + "\"");
            }

            String dataCommand = (String) ((data[0]).toUpperCase());

            if (dataCommand.equalsIgnoreCase(TypeOfCommand.IZLAZ.getCommand())) {

                newCommand = new Command(TypeOfCommand.IZLAZ, null, -1, null, null);
            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.VOZACI.getCommand()) || dataCommand.startsWith(TypeOfCommand.VOZACI1.getCommand())) {

                newCommand = new Command(TypeOfCommand.VOZACI, null, -1, null, null);

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.NOVI.getCommand())) {

                newCommand = new Command(TypeOfCommand.NOVI, null, -1, null, laodNewDriversIfExist(data, 1));

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.PREUZMI.getCommand())) {

                newCommand = new Command(TypeOfCommand.PREUZMI, laodVehiclesIfExist(data, 2), -1, null, laodDriversIfExist(data, 1));

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.OTKAZ.getCommand())) {

                newCommand = new Command(TypeOfCommand.OTKAZ, null, -1, null, laodDriversIfExist(data, 1));

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.BOLOVANJE.getCommand())) {

                newCommand = new Command(TypeOfCommand.BOLOVANJE, null, -1, null, laodDriversIfExist(data, 1));

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.GODISNJI_ODMOR.getCommand()) || dataCommand.contains(TypeOfCommand.ODMOR.getCommand())) {

                newCommand = new Command(TypeOfCommand.GODISNJI_ODMOR, null, -1, null, laodDriversIfExist(data, 1));

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.OBRADI.getCommand())) {

                newCommand = new Command(TypeOfCommand.OBRADI, laodVehiclesIfExist(data, 2), -1, laodRootAreaIfExist(data, 1), null);

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.PRIPREMI.getCommand())) {

                newCommand = new Command(TypeOfCommand.PRIPREMI, laodVehiclesIfExist(data, 1), -1, null, null);

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.KRENI_BEZ_PARAMETRA.getCommand())) {

                newCommand = new Command(TypeOfCommand.KRENI_BEZ_PARAMETRA, new ArrayList<Vehicle>(), -1, null, null);

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.KVAR.getCommand())) {

                newCommand = new Command(TypeOfCommand.KVAR, laodVehiclesIfExist(data, 1), -1, null, null);

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.STATUS.getCommand())) {

                newCommand = new Command(TypeOfCommand.STATUS, new ArrayList<Vehicle>(), -1, null, null);

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.ISPRAZNI.getCommand())) {

                newCommand = new Command(TypeOfCommand.ISPRAZNI, laodVehiclesIfExist(data, 1), -1, null, null);

            } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.KONTROLA.getCommand())) {

                newCommand = new Command(TypeOfCommand.KONTROLA, laodVehiclesIfExist(data, 1), -1, null, null);

            } else if (dataCommand.startsWith(TypeOfCommand.KRENI_S_PARAMETRIMA.getCommand())) {
                String[] kreniData = dataCommand.split(" ");
                if (kreniData.length == 2 && kreniData[0].equalsIgnoreCase(TypeOfCommand.KRENI_BEZ_PARAMETRA.getCommand())) {

                    newCommand = new Command(TypeOfCommand.KRENI_S_PARAMETRIMA, new ArrayList<Vehicle>(), Integer.valueOf(kreniData[1]), null, null);

                }

            } else {

                throw new Exception("Nepoznata komanda!! Zapis: " + dataCommand);

            }
        } catch (Exception e) {
            vt100.printInputLine(e.getMessage());
        }

        return newCommand;
    }

    private void assignFreeDriversToVehicles() {
        try {
            for (Vehicle vehicle : dispecerContext.getAllVehicles()) {
                if (!isExistMainDriver(vehicle)) {
                    Driver d = findFreeDriver();
                    if (d != null) {
                        d.zauzmiVozilo(vehicle);
                    } else {
                        builderDirector.addTextLineInReport("DISPEČER: Nema nedodjenjenih vozača za vozilo " + vehicle.getName() + "!", true);

                    }
                }

            }
        } catch (Exception e) {
        }
    }

    private Driver findFreeDriver() {
        try {
            for (Driver driver : dispecerContext.getDriversList()) {
                if (driver.getState() == TypeOfDriverState.NEDODJELJEN) {
                    return driver;
                }
            }

        } catch (Exception e) {

        }
        return null;
    }

    private boolean isExistMainDriver(Vehicle vehicle) {
        try {
            for (Driver driver : vehicle.getDrivers()) {
                if (driver.getState() == TypeOfDriverState.VOZI_KAMION) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    //new 
    private CompositePlace laodRootAreaIfExist(String[] data, int i) {
        CompositePlace place = null;

        try {
            if (data[i] != null) {
                String areaID = data[i];
                place = fetchRootAreaByID(areaID);
                if (place == null) {
                    throw new Exception();
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            builderDirector.addErrorInReport("Pogreška kod učitavanja područja komande " + data, false);
        }

        return place;
    }

    private List<Driver> laodNewDriversIfExist(String[] data, int i) {
        List<Driver> driverList = new ArrayList<>();

        try {
            if (data[i] != null) {
                List<String> names = Arrays.asList(data[i].split(","));
                driverList = createDriversByName(names);
                if (driverList == null) {
                    throw new Exception();
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            builderDirector.addErrorInReport("Pogreška kod učitavanja vozača komande " + data, false);
        }

        return driverList;
    }

    private List<Driver> createDriversByName(List<String> names) {
        List<Driver> driversList = new ArrayList<Driver>();

        for (String name : names) {
            driversList.add(new Driver(name.trim()));
        }
        return driversList;
    }

    private List<Driver> laodDriversIfExist(String[] data, int i) {
        List<Driver> driverList = new ArrayList<>();

        try {
            if (data[i] != null) {
                List<String> names = Arrays.asList(data[i].split(","));
                driverList = fetchDriversByName(names);
                if (driverList == null) {
                    throw new Exception();
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            builderDirector.addErrorInReport("Pogreška kod učitavanja vozača komande " + data, false);
        }

        return driverList;
    }

    private List<Vehicle> laodVehiclesIfExist(String[] data, int position) {
        List<Vehicle> vehicles = new ArrayList<>();
        List<String> vehiclesListOfID;// = Arrays.asList(data[1].split(","));
        try {
            if (data[position] != null) {
                vehiclesListOfID = Arrays.asList(data[position].split(","));
                vehicles = fetchVehiclesById(vehiclesListOfID);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            builderDirector.addErrorInReport("Pogreška kod učitavanja vozila komande " + data, false);
        }

        return vehicles;
    }

    private CompositePlace fetchRootAreaByID(String ID) {
        CompositePlace place = null;

        for (CompositePlace root : this.dispecerContext.getAreaRootElement()) {
            if (root.getId().equalsIgnoreCase(ID)) {
                return root;
            }
        }

        return place;

    }

    private List<Driver> fetchDriversByName(List<String> names) {
        List<Driver> driversList = new ArrayList<Driver>();

        for (String name : names) {
            for (Driver driver : this.dispecerContext.getDriversList()) {
                if (driver.getName().equalsIgnoreCase(name)) {
                    driversList.add(driver);
                }
            }

        }
        return driversList;
    }

    private List<Vehicle> fetchVehiclesById(List<String> vehiclesListOfID) {
        List<Vehicle> vehicles = new ArrayList<>();
        for (String id : vehiclesListOfID) {
            for (Vehicle vehicle : this.dispecerContext.getAllVehicles()) {
                if (vehicle.getId().equalsIgnoreCase(id)) {
                    vehicles.add(vehicle);
                }
            }

        }
        return vehicles;

    }

}
