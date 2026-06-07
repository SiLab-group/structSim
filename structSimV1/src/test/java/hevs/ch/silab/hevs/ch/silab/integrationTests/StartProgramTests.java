package hevs.ch.silab.hevs.ch.silab.integrationTests;

import ch.hevs.silab.structuredsim.experimenthandling.Environment;
import ch.hevs.silab.structuredsim.experimenthandling.Parameter;
import ch.hevs.silab.structuredsim.gluecode.ConcreteModifier;
import ch.hevs.silab.structuredsim.gluecode.SimpleSimulationHandler;
import ch.hevs.silab.structuredsim.gluecode.Simulation;
import ch.hevs.silab.structuredsim.interfaces.AModifier;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class StartProgramTests extends TestCase {


    private double delta = 1e-9;

    private String pathOUT = System.getProperty("java.io.tmpdir").replace("\\", "/") + "/structsim-results";
    private String pathSIM = System.getProperty("java.io.tmpdir").replace("\\", "/") + "/structsim-simulator";


    @BeforeEach
    @Override
    protected void setUp() throws Exception {
        Path resultsPath = Paths.get(pathOUT);
        if (Files.exists(resultsPath)) {
            Files.walk(resultsPath)
                    .sorted(Comparator.reverseOrder())
                    .filter(p -> !p.equals(resultsPath))
                    .forEach(p -> {
                        try { Files.delete(p); }
                        catch (IOException e) { e.printStackTrace(); }
                    });
        }
        Files.createDirectories(resultsPath);
        Files.createDirectories(Paths.get(pathSIM));
    }

    /**
     * Delete the output files after each test
     * @throws Exception
     */
    @AfterEach
    @Override
    protected void tearDown() throws Exception {
        Path resultsPath = Paths.get(pathOUT);
        if (Files.exists(resultsPath)) {
            Files.walk(resultsPath)
                    .sorted(Comparator.reverseOrder())
                    .forEach(p -> {
                        try { Files.delete(p); }
                        catch (IOException e) { e.printStackTrace(); }
                    });
        }
    }

    @Test
    public void startProgram_shouldGenerateCorrectSummaryFile() throws IOException {

        // Arrange
        /*InputStream pathConfigFile = Simulation.class
                .getClassLoader()
                .getResourceAsStream("config.properties");*/

        File tempDir = Files.createTempDirectory("structsim-test").toFile();
        File tempConfig = new File(tempDir, "config.properties");

        String configContent =
                "pathOUT = " + pathOUT + "\n" +
                        "pathParameters = parameters.txt\n" +
                        "pathSimulator = " + pathSIM + "\n" +
                        "pathToSimulatorResultFile = " + pathSIM + "/results/results.txt\n" +
                        "cuttOfPlanning = 0.15\n" +
                        "typeCuttOfPlanning = CRITERIA\n";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempConfig))) {
            bw.write(configContent);
        }

        InputStream pathConfigFile = new FileInputStream(tempConfig);


        List<AModifier> modifiers = new ArrayList<AModifier>();
        modifiers.add(new ConcreteModifier("val1", '*', 0.5, 0.5));
        modifiers.add(new ConcreteModifier("val1", '*', 0.2, 0.2));
        SimpleSimulationHandler ssh = new SimpleSimulationHandler(modifiers);

        // Act
        Simulation s = new Simulation();
        try {
            s.startProgram(pathConfigFile, ssh);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Assert
        File summaryFile = new File(pathOUT + "/SummaryFile.txt");
        long timeout = 15_000; // wait 15 seconds
        long start = System.currentTimeMillis();
        List<String> lines = new ArrayList<>();

        int expectedLineCount = 8;
        while (System.currentTimeMillis() - start < timeout) {
            if (summaryFile.exists()) {
                lines.clear();
                try (BufferedReader br = new BufferedReader(new FileReader(summaryFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.trim().isEmpty()) {
                            lines.add(line);
                        }
                    }
                } catch (IOException e) {
                    // File still being written
                }
                if (lines.size() >= expectedLineCount) break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

        assertTrue("SummaryFile.txt should exist after startProgram()",
                summaryFile.exists());

        assertEquals("File should contains right number of simulations",
                8, lines.size());

        String[] expectedLines = {
                "Simulation ID : 1\t Probability : 0.5\t Modifier implemented :    *0.5",
                "Simulation ID : 2\t Probability : 0.2\t Modifier implemented :    *0.2",
                "Simulation ID : 3\t Probability : 0.25\t Modifier implemented :    *0.5   *0.5",
                "Simulation ID : 4\t Probability : 0.1\t Modifier implemented :    *0.5   *0.2",
                "Simulation ID : 5\t Probability : 0.125\t Modifier implemented :    *0.5   *0.5   *0.5",
                "Simulation ID : 6\t Probability : 0.05\t Modifier implemented :    *0.5   *0.5   *0.2",
                "Simulation ID : 7\t Probability : 0.1\t Modifier implemented :    *0.2   *0.5",
                "Simulation ID : 8\t Probability : 0.04000000000000001\t Modifier implemented :    *0.2   *0.2"
        };

        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals(
                    "Incorrect content at line " + (i + 1),
                    expectedLines[i],
                    lines.get(i)
            );
        }
    }

}
