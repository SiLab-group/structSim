package ch.hevs.silab.structuredsim.gluecode;

import ch.hevs.silab.structuredsim.experimenthandling.Measure;
import ch.hevs.silab.structuredsim.interfaces.AModifier;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.interfaces.ASimulationSystemHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SimpleSimulationHandler extends ASimulationSystemHandler {


    private ConcreteModifier mc1 = new ConcreteModifier("val1", '*', 0.5, 0.5 );

    private ConcreteModifier mc2 = new ConcreteModifier("val1", '*', 0.2, 0.2);

    private List<AModifier> modifiers = new ArrayList<AModifier>();


    public SimpleSimulationHandler() {




    }

    public SimpleSimulationHandler(List<AModifier> modifiers) {


        this.modifiers = modifiers;


    }


    @Override
    public Vector<Measure> extractMeasures(String resultsFilePath) {
        String separator = "=";
        Vector<Measure> measuresList = new Vector<Measure>();
        String measureKey;
        String measureValue;
        BufferedReader in;
        try {
            in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(resultsFilePath), "UTF-8"));
            String line = "";
            while ((line = in.readLine()) != null) {

                int pos = line.indexOf(separator);
                measureKey = line.substring(0, pos);
                measureValue = (line.substring(pos + 1, line.length()));
                measuresList.add(new Measure(measureKey, measureValue));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return measuresList;
    }

    @Override
    public List<AModifier> initiateModifierList() {
        //listModifierClass.add(mc1);
        //listModifierClass.add(mc2);

        listModifierClass = modifiers;
        return listModifierClass;
    }


    @Override
    public Vector<Parameter> readParametersFile(String parametersFilePath) {
        String separator = "=";
        Vector<Parameter> parametersList = new Vector<Parameter>();
        String key;
        double value;
        BufferedReader in;
        try {
            in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(parametersFilePath), "UTF-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                int pos = line.indexOf(separator);
                key = line.substring(0, pos);
                value = Double.parseDouble(line.substring(pos + 1, line.length()));
                parametersList.add(new Parameter(key, value));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parametersList;



    }

    public Vector<Parameter> readParametersFile(InputStream inputStream) {
        String separator = "=";
        Vector<Parameter> parametersList = new Vector<>();
        String key;
        double value;

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                int pos = line.indexOf(separator);
                key = line.substring(0, pos);
                value = Double.parseDouble(line.substring(pos + 1, line.length()));
                parametersList.add(new Parameter(key, value));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parametersList;
    }


    @Override
    public void writeParametersFile(Vector<Parameter> setOfParameters, String locationToStore) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(locationToStore + "/myParamFile.txt"));
            for (Parameter p: setOfParameters) {
                bw.write(p.getKey() + "=" + p.getValue());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startSimulation(String pathToInputFile) {


        /*
        String resultFile = options.getPathToSimulatorResultFile();
        MySimulator.run(pathToInputFile, resultFile);*/

    }

    @Override
    public void stopProgram() {

    }
}


