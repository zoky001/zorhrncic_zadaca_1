/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz3.iterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.foi.uzdiz.zorhrncic.dz3.composite.CompositePlace;
import org.foi.uzdiz.zorhrncic.dz3.ezo.DispecerContext;
import org.foi.uzdiz.zorhrncic.dz3.ezo.Spremnik;
import org.foi.uzdiz.zorhrncic.dz3.ezo.drivers.Driver;
import org.foi.uzdiz.zorhrncic.dz3.ezo.vehicle.Vehicle;
import org.foi.uzdiz.zorhrncic.dz3.log.ReportBuilderDirector;
import org.foi.uzdiz.zorhrncic.dz3.shared.Constants;
import org.foi.uzdiz.zorhrncic.dz3.shared.TypesOfSpremnik;
import org.foi.uzdiz.zorhrncic.dz3.singleton.CommonDataSingleton;

/**
 *
 * @author Zoran
 */
public class CommandRepository implements Container {

    private final ReportBuilderDirector builderDirector;

    public List<Command> commandsList = new ArrayList<>();
    List<Vehicle> vehicles;
    private final List<CompositePlace> roots;
    private final DispecerContext context;

    public CommandRepository(DispecerContext context) {

        builderDirector = CommonDataSingleton.getInstance().getReportBuilderDirector();
        this.vehicles = context.getAllVehicles();
        this.roots = context.getAreaRootElement();
        this.context = context;

        loadCommandsFromFile((String) CommonDataSingleton.getInstance().getParameterByKey(Constants.dispecer));

    }

    private void loadCommandsFromFile(String path) {
        BufferedReader br = null;
        String line = " ";
        String cvsSplitBy = ";";
        File file = new File(path);
        Spremnik spremnik;
        Command newCommand;

        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));

            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                if (lineNo++ != 0) {
                    // use comma as separator
                    String[] data = line.split(cvsSplitBy);

                    if (data.length < 1 || data.length > 3) {
                        builderDirector.addErrorInReport("Pogrešan zapis podataka dispečar u datoteci!! Zapis: " + line.toString(), false);
                        continue;
                    }

                    String dataCommand = (String) ((data[0]).toUpperCase());

                    if (dataCommand.equalsIgnoreCase(TypeOfCommand.IZLAZ.getCommand())) {

                        newCommand = new Command(TypeOfCommand.IZLAZ, null, -1, null, null);
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.VOZACI.getCommand())) {

                        newCommand = new Command(TypeOfCommand.VOZACI, null, -1, null, null);
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.NOVI.getCommand())) {

                        newCommand = new Command(TypeOfCommand.NOVI, null, -1, null, laodNewDriversIfExist(data, 1));
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.PREUZMI.getCommand())) {

                        newCommand = new Command(TypeOfCommand.PREUZMI, laodVehiclesIfExist(data, 2), -1, null, laodDriversIfExist(data, 1));
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.OTKAZ.getCommand())) {

                        newCommand = new Command(TypeOfCommand.OTKAZ, null, -1, null, laodDriversIfExist(data, 1));
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.BOLOVANJE.getCommand())) {

                        newCommand = new Command(TypeOfCommand.BOLOVANJE, null, -1, null, laodDriversIfExist(data, 1));
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.GODISNJI_ODMOR.getCommand())) {

                        newCommand = new Command(TypeOfCommand.GODISNJI_ODMOR, null, -1, null, laodDriversIfExist(data, 1));
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.OBRADI.getCommand())) {

                        newCommand = new Command(TypeOfCommand.OBRADI, laodVehiclesIfExist(data, 2), -1, laodRootAreaIfExist(data, 1), null);
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.PRIPREMI.getCommand())) {

                        newCommand = new Command(TypeOfCommand.PRIPREMI, laodVehiclesIfExist(data, 1), -1, null, null);
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.KRENI_BEZ_PARAMETRA.getCommand())) {

                        newCommand = new Command(TypeOfCommand.KRENI_BEZ_PARAMETRA, new ArrayList<Vehicle>(), -1, null, null);
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.KVAR.getCommand())) {

                        newCommand = new Command(TypeOfCommand.KVAR, laodVehiclesIfExist(data, 1), -1, null, null);
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.STATUS.getCommand())) {

                        newCommand = new Command(TypeOfCommand.STATUS, new ArrayList<Vehicle>(), -1, null, null);
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.ISPRAZNI.getCommand())) {

                        newCommand = new Command(TypeOfCommand.ISPRAZNI, laodVehiclesIfExist(data, 1), -1, null, null);
                        commandsList.add(newCommand);
                    } else if (dataCommand.equalsIgnoreCase(TypeOfCommand.KONTROLA.getCommand())) {

                        newCommand = new Command(TypeOfCommand.KONTROLA, laodVehiclesIfExist(data, 1), -1, null, null);
                        commandsList.add(newCommand);
                    } else if (dataCommand.startsWith(TypeOfCommand.KRENI_S_PARAMETRIMA.getCommand())) {
                        String[] kreniData = dataCommand.split(" ");
                        if (kreniData.length == 2 && kreniData[0].equalsIgnoreCase(TypeOfCommand.KRENI_BEZ_PARAMETRA.getCommand())) {

                            newCommand = new Command(TypeOfCommand.KRENI_S_PARAMETRIMA, new ArrayList<Vehicle>(), Integer.valueOf(kreniData[1]), null, null);
                            commandsList.add(newCommand);
                        }

                    } else {

                        builderDirector.addErrorInReport("Nepoznata komanda!! Zapis: " + dataCommand, false);

                    }

                }

            }

        } catch (FileNotFoundException e) {
            builderDirector.addErrorInReport("Pogrešan zapis podataka dispečar u datoteci!!", false);

            e.printStackTrace();
        } catch (IOException e) {
            builderDirector.addErrorInReport("Pogrešan zapis podataka dispečar u datoteci!!", false);

            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

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

        for (CompositePlace root : this.roots) {
            if (root.getId().equalsIgnoreCase(ID)) {
                return root;
            }
        }

        return place;

    }

    private List<Driver> fetchDriversByName(List<String> names) {
        List<Driver> driversList = new ArrayList<Driver>();

        for (String name : names) {
            for (Driver driver : this.context.getDriversList()) {
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
            for (Vehicle vehicle : this.vehicles) {
                if (vehicle.getId().equalsIgnoreCase(id)) {
                    vehicles.add(vehicle);
                }
            }

        }
        return vehicles;

    }

    @Override
    public IIterator getIterator() {
        return new CommandIterator();
    }

    private class CommandIterator implements IIterator {

        int index;

        @Override
        public boolean hasNext() {
            if (index < commandsList.size()) {
                return true;
            }
            return false;
        }

        @Override
        public Command next() {
            if (this.hasNext()) {
                return commandsList.get(index++);
            }
            return null;
        }

    }

}
