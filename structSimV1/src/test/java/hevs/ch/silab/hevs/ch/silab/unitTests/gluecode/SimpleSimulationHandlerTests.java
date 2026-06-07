package hevs.ch.silab.hevs.ch.silab.unitTests.gluecode;

import ch.hevs.silab.structuredsim.experimenthandling.Measure;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.gluecode.SimpleSimulationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Vector;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


/**
 * This class is used to test methods from SimpleSimulationHandler class
 * @see SimpleSimulationHandler
 * @author Matthias Gaillard
 */
public class SimpleSimulationHandlerTests {

    SimpleSimulationHandler handler;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        handler = new SimpleSimulationHandler();
    }

    //ChatGPT generated
    @Test
    void extractMeasures_shouldParseFileCorrectly() throws Exception {
        // Arrange
        Path file = tempDir.resolve("results.txt");

        Files.write(file, List.of(
                "throughput=12",
                "latency=50"
        ));

        // Act
        Vector<Measure> result = handler.extractMeasures(file.toString());

        // Assert
        assertEquals(2, result.size());

        assertEquals("throughput", result.get(0).getKey());
        assertEquals("12", result.get(0).getValue());

        assertEquals("latency", result.get(1).getKey());
        assertEquals("50", result.get(1).getValue());
    }

    //ChatGPT generated
    @Test
    void readParametersFile_shouldParseFileFromPath() throws Exception {
        // Arrange
        Path file = tempDir.resolve("params.txt");

        Files.write(file, List.of(
                "val1=10",
                "val2=20.5"
        ));


        // Act
        Vector<Parameter> result = handler.readParametersFile(file.toString());

        // Assert
        assertEquals(2, result.size());

        assertEquals("val1", result.get(0).getKey());
        assertEquals(10.0, result.get(0).getValue());

        assertEquals("val2", result.get(1).getKey());
        assertEquals(20.5, result.get(1).getValue());
    }

    //ChatGPT generated
    @Test
    void readParametersFile_shouldParseFromInputStream() {
        // Arrange
        String content =
                "val1=1\n" +
                        "val2=2.5\n";

        InputStream is = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));



        // Act
        Vector<Parameter> result = handler.readParametersFile(is);

        // Assert
        assertEquals(2, result.size());

        assertEquals("val1", result.get(0).getKey());
        assertEquals(1.0, result.get(0).getValue());

        assertEquals("val2", result.get(1).getKey());
        assertEquals(2.5, result.get(1).getValue());
    }

    //ChatGPT generated
    @Test
    void writeParametersFile_shouldCreateFileWithCorrectFormat() throws Exception {
        // Arrange
        Vector<Parameter> params = new Vector<>();
        params.add(new Parameter("val1", 10));
        params.add(new Parameter("val2", 20.5));


        String outputDir = tempDir.toString();

        // Act
        handler.writeParametersFile(params, outputDir);

        // Assert
        Path file = tempDir.resolve("myParamFile.txt");

        assertTrue(Files.exists(file));

        List<String> lines = Files.readAllLines(file);

        assertEquals(2, lines.size());
        assertEquals("val1=10.0", lines.get(0));
        assertEquals("val2=20.5", lines.get(1));
    }

}
