package ch.hevs.silab.structuredsim.simulators.mockUpSim;

import java.util.Vector;

import ch.hevs.silab.structuredsim.experimenthandling.Environment;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.interfaces.AModifier;

public class ModifierClass2 extends AModifier {

    String keyToChange = "val2";

    public ModifierClass2() {
        this.probability = 0.5;
        this.name = "Modifier2";
    }

    @Override
    public Environment applyModifier(Environment env) {
        Vector<Parameter> params = env.getSetOfParameters();
        for (Parameter p : params) {
            if (p.getKey().equals(keyToChange)) {
                p.setValue(p.getValue() + 10);
                break;
            }
        }
        return env;
    }
}
