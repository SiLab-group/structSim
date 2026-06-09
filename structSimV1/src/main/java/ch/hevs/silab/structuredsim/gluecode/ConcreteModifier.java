package ch.hevs.silab.structuredsim.gluecode;

import ch.hevs.silab.structuredsim.experimenthandling.Environment;
import ch.hevs.silab.structuredsim.experimenthandling.ExperimentPlanGenerator;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.interfaces.AModifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Vector;

public class ConcreteModifier extends AModifier {


    private static final Logger logger = LogManager.getLogger(ConcreteModifier.class.getName());

    String keyToChange;
    char operator;

    /**
     * Value to add/substract/multiply/divide to the key
     */
    double delta;

    public ConcreteModifier(String keyToChange, char operator, double delta, double probability) {
        super(probability, operator + "" + delta);
        this.keyToChange=keyToChange;
        this.probability = probability;
        this.operator = operator;
        this.delta=delta;
    }

    public ConcreteModifier(String keyToChange, char operator, double delta) {
        this(keyToChange, operator, delta, 1);
    }

    public ConcreteModifier(double delta) {
        this("val1", '*', delta, delta);
    }
    public ConcreteModifier() {
    }


    @Override
    public Environment applyModifier(Environment env) {
        Vector<Parameter> params = env.getSetOfParameters();
        for (Parameter p : params) {
            logger.debug("param=" + p.getKey() + " keyToChange=" + keyToChange);
            if (p.getKey().equals(keyToChange)) {
                switch(operator) {
                    case '+':
                        p.setValue(p.getValue() + delta);
                        break;
                    case '-':
                        p.setValue(p.getValue() - delta);
                        break;
                    case '*':
                        p.setValue(p.getValue() * delta);
                        break;
                    case '/':
                        p.setValue(p.getValue() / delta);
                        break;
                }
            }
        }
        return env;
    }

    public static double findValue(Vector<Parameter> params, String key) {
        String value = null;
        for (Parameter p : params) {
            if (p.getKey().equals(key)) {
                return p.getValue();
            }
        }
        return -1;
    }

}
