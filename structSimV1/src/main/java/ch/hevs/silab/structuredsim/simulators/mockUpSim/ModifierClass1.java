package ch.hevs.silab.structuredsim.simulators.mockUpSim;

import java.util.Vector;

import ch.hevs.silab.structuredsim.experimenthandling.Environment;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.interfaces.AModifier;

public class ModifierClass1 extends AModifier {

    String keyToChange = "val2";

    public ModifierClass1() {
        this.probability = 0.2;
        this.name = "Modifier1";
    }

    @Override
    public Environment applyModifier(Environment env) {
        Vector<Parameter> params = env.getSetOfParameters();
        for (Parameter p : params) {
            if (p.getKey().equals(keyToChange)) {
                p.setValue(p.getValue() + 1);
                break;
            }
        }
        return env;
    }
}
