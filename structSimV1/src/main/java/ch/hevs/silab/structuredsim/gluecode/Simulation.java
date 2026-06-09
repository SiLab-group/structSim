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

        InputStream pathConfigFile = Simulation.class
                .getClassLoader()
                .getResourceAsStream("config.properties");

        List<AModifier> modifiers = new ArrayList<AModifier>();

        modifiers.add(new ConcreteModifier("val2", '+', 1.0, 0.5));
        modifiers.add(new ConcreteModifier("val2", '+', 10.0, 0.5));

        //Custom class
        SimpleSimulationHandler ssh = new SimpleSimulationHandler(modifiers);

        startProgram(pathConfigFile, ssh);

    }
}
