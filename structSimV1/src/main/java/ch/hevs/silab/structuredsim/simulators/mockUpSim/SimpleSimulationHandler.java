package ch.hevs.silab.structuredsim.simulators.mockUpSim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import ch.hevs.silab.structuredsim.experimenthandling.Measure;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.interfaces.AModifier;
import ch.hevs.silab.structuredsim.interfaces.ASimulationSystemHandler;

public class SimpleSimulationHandler extends ASimulationSystemHandler {

    private ModifierClass1 mc1 = new ModifierClass1();
    private ModifierClass2 mc2 = new ModifierClass2();

    public SimpleSimulationHandler() {
    }

    @Override
    public List<AModifier> initiateModifierList() {
        listModifierClass.add(mc1);
        listModifierClass.add(mc2);
        return listModifierClass;
    }

    @Override
    public void startSimulation(String pathToInputFile) {
    }

    @Override
    public void stopProgram() {
    }

    @Override
    public Vector<Parameter> readParametersFile(String parametersFilePath) {
        String separator = "=";
        Vector<Parameter> parametersList = new Vector<>();

        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(parametersFilePath), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                int pos = line.indexOf(separator);
                String key = line.substring(0, pos);
                double value = Double.parseDouble(line.substring(pos + 1, line.length()));
                parametersList.add(new Parameter(key, value));
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parametersList;
    }

    @Override
    public void writeParametersFile(Vector<Parameter> setOfParameters, String locationToStore) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(locationToStore + "/myParamFile.txt"));
            for (Parameter p : setOfParameters) {
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
    public Vector<Measure> extractMeasures(String resultsFile) {
        String separator = "=";
        Vector<Measure> measuresList = new Vector<>();

        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(resultsFile), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                int pos = line.indexOf(separator);
                String measureKey = line.substring(0, pos);
                String measureValue = line.substring(pos + 1, line.length());
                measuresList.add(new Measure(measureKey, measureValue));
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return measuresList;
    }
}
