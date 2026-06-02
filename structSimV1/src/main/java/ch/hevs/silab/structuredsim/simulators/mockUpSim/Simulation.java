package ch.hevs.silab.structuredsim.simulators.mockUpSim;

import java.io.IOException;

import ch.hevs.silab.structuredsim.interfaces.StartProgram;

public class Simulation {

    public static void main(String[] args) throws IOException {
        String pathConfigFile = "./src/main/java/ch/hevs/silab/structuredsim/simulators/mockUpSim/config.properties";
        SimpleSimulationHandler ssh = new SimpleSimulationHandler();
        StartProgram.startProgram(pathConfigFile, ssh);
    }
}
