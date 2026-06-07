package example;

import ch.hevs.silab.structuredsim.gluecode.ConcreteModifier;
import ch.hevs.silab.structuredsim.gluecode.SimpleSimulationHandler;
import ch.hevs.silab.structuredsim.interfaces.AModifier;
import ch.hevs.silab.structuredsim.interfaces.StartProgram;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Simulation extends StartProgram {

    public static void main(String[] args) throws IOException {

        InputStream config = Simulation.class
                .getClassLoader()
                .getResourceAsStream("config.properties");

        List<AModifier> modifiers = new ArrayList<>();
        modifiers.add(new ConcreteModifier("val2", '+', 1.0, 0.5));
        modifiers.add(new ConcreteModifier("val2", '+', 10.0, 0.5));

        SimpleSimulationHandler ssh = new SimpleSimulationHandler(modifiers);

        startProgram(config, ssh);
    }
}
