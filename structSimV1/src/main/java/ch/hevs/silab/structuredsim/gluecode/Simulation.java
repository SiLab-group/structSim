package ch.hevs.silab.structuredsim.gluecode;

import ch.hevs.silab.structuredsim.interfaces.StartProgram;

import java.io.IOException;
import java.io.InputStream;

public class Simulation extends StartProgram {

    // Mock-up code for a simple simulation
    public static void main(String[] args) throws IOException {
        //String pathConfigFile = "src/main/java/ch/hevs/silab/structuredsim/resources/config.properties";
        //String pathConfigFile = "src/main/resources/config.properties";


        /*
        String pathConfigFile = Simulation.class
                .getClassLoader()
                .getResource("config.properties")
                .getPath();*/


        InputStream pathConfigFile = Simulation.class
                .getClassLoader()
                .getResourceAsStream("config.properties");




        //Custom class
        SimpleSimulationHandler ssh = new SimpleSimulationHandler();

        startProgram(pathConfigFile, ssh);
    }
}
