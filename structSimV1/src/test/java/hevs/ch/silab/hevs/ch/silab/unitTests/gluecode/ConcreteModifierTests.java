package hevs.ch.silab.hevs.ch.silab.unitTests.gluecode;

import ch.hevs.silab.structuredsim.experimenthandling.Environment;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.gluecode.ConcreteModifier;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Vector;

/**
 * This class is used to test methods from ConcreteModifier class
 * @author Matthias Gaillard
 */
public class ConcreteModifierTests extends TestCase {


    private double delta = 1e-9;

    @ParameterizedTest
    @CsvSource({
            "+,1",
            "-,2",
            "*,3",
            "/,4"
    })
    public void applyModifier_shouldUpdateTheEnvironment(char operator, double value) {
        //Arrange
        Environment e = new Environment();
        Vector<Parameter> v = new Vector<>();
        v.add(new Parameter("val1", 1));
        e.setSetOfParameters(v);

        ConcreteModifier modifier = new ConcreteModifier("val1", operator, value);

        //Act
        e = modifier.applyModifier(e);

        //Assert
        switch(operator) {
            case '+':
                assertEquals(2, e.getSetOfParameters().get(0).getValue(), delta);
                break;
            case '-':
                assertEquals(-1, e.getSetOfParameters().get(0).getValue(), delta);
                break;
            case '*':
                assertEquals(3, e.getSetOfParameters().get(0).getValue(), delta);
                break;
            case '/':
                assertEquals(0.25, e.getSetOfParameters().get(0).getValue(), delta);
                break;

        }
    }

    @Test
    public void findvalue_shouldFindTheValueInAVector() {

        //Arrange
        Vector<Parameter> params = new Vector<>();
        params.add(new Parameter("val1", 3));
        params.add(new Parameter("val2", 10));
        params.add(new Parameter("val3", 7));

        //Act
        double result = ConcreteModifier.findValue(params, "val2");

        //Assert
        assertEquals(10, result, delta);

    }

    @Test
    public void findvalue_shouldReturnMinus1_WhenNothingIsFound() {

        //Arrange
        Vector<Parameter> params = new Vector<>();
        params.add(new Parameter("val1", 3));
        params.add(new Parameter("val2", 10));
        params.add(new Parameter("val3", 7));

        //Act
        double result = ConcreteModifier.findValue(params, "val31416");

        //Assert
        assertEquals(-1, result, delta);

    }

}
