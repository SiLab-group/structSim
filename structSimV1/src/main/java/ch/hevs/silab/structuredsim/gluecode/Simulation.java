package ch.hevs.silab.structuredsim.gluecode;

import ch.hevs.silab.structuredsim.interfaces.AModifier;
import ch.hevs.silab.structuredsim.interfaces.StartProgram;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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


        List<AModifier> modifiers = new ArrayList<AModifier>();

        //modifiers.add(new ConcreteModifier("val1", '*', 0.2, 0.2));
        modifiers.add(new ConcreteModifier("val1", '*', 0.5, 0.5));

        //Custom class
        SimpleSimulationHandler ssh = new SimpleSimulationHandler(modifiers);

        startProgram(pathConfigFile, ssh);
    }
}
