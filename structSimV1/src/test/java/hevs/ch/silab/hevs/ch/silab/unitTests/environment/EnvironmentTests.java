package hevs.ch.silab.hevs.ch.silab.unitTests.environment;

import ch.hevs.silab.structuredsim.experimenthandling.Environment;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Vector;

/**
 * This class is used to test methods from Environment class
 * @author Matthias Gaillard
 */
public class EnvironmentTests extends TestCase {


    /**
     * Small cutOff to compare double values
     */
    private double delta = 1e-9;

    @Test
    public void testConstructor_shouldNotChangeTheOriginalEnv_whenConstructingNewEnv() {

        // Arrange
        Parameter p1 = new Parameter("val1", 0);
        Vector<Parameter> v = new Vector<>();
        v.add(p1);
        Environment e1 = new Environment(1, v, 0.3);
        Environment e2 = new Environment(2, e1);
        e2.getSetOfParameters().get(0).setValue(1);

        // Act
        double expectedE1Value = e1.getSetOfParameters().get(0).getValue();
        double expectedE2Value = e2.getSetOfParameters().get(0).getValue();

        // Assert
        assertEquals(expectedE1Value, 0, delta);
        assertEquals(expectedE2Value, 1, delta);

    }
    @Test
    public void testCompareTo_shouldReturnCorrectValue_whenComparingEnvironments() {

        // Arrange
        Environment low = new Environment(1, new Vector<Parameter>(), 0.3);
        Environment high = new Environment(2, new Vector<Parameter>(), 0.5);
        Environment low2 = new Environment(2, new Vector<Parameter>(), 0.3);

        // Act
        int shouldBeNegative = low.compareTo(high);
        int shouldBePositive = high.compareTo(low);
        int shouldBeZero = low.compareTo(low2);

        // Assert
        assertTrue(shouldBeNegative < 0);
        assertTrue(shouldBePositive > 0);
        assertEquals(shouldBeZero, 0, delta);

    }

    @Test
    public void testToStringModifier_shouldReturnCorrectString_whenDisplayingTheEnvAsAString() {

        // Arrange
        Environment e = new Environment(1, new Vector<Parameter>(), 0.3);
        ArrayList<String> trace = new ArrayList<>();
        trace.add("m1");
        trace.add("m2");
        e.setTrace(trace);

        // Act
        String expectedString = "Simulation ID : 1\t Probability : 0.3\t Modifier implemented :    m1   m2";
        String actualString = e.toStringModifier();

        // Assert
        assertEquals(expectedString, actualString);

    }



}
