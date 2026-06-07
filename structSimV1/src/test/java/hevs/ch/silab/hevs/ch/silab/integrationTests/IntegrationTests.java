package hevs.ch.silab.hevs.ch.silab.integrationTests;

import ch.hevs.silab.structuredsim.gluecode.ConcreteModifier;
import ch.hevs.silab.structuredsim.gluecode.SimpleSimulationHandler;
import ch.hevs.silab.structuredsim.gluecode.Simulation;
import ch.hevs.silab.structuredsim.interfaces.AModifier;
import junit.framework.TestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;


/**
 * This class is used to test the overall behavior of the framework,
 * It checks whether the output summary file
 * is correctly computed when given inputs.
 * Scenarios mentioned in the tests are illustrated
 * with pictures in this folder.
 * @author Matthias Gaillard
 */
public class IntegrationTests extends TestCase {
    private String pathOUT = System.getProperty("java.io.tmpdir") + "/structsim-results";
    private String pathSIM = System.getProperty("java.io.tmpdir") + "/structsim-simulator";






    /**
     * This method returns a list of predefined modifiers
     * depending on a scenario number.
     *
     * @param scenario
     * @return
     */
    private List<AModifier> returnModifiersFromScenario(int scenario) {
        ArrayList<AModifier> modifiers = new ArrayList<>();
        switch (scenario) {
            case 1:
                modifiers.add(new ConcreteModifier(0.5));
                return modifiers;
            case 2:
                modifiers.add(new ConcreteModifier(0.2));
                modifiers.add(new ConcreteModifier(0.5));
                return modifiers;
            case 4:
                modifiers.add(new ConcreteModifier(0.1));
                modifiers.add(new ConcreteModifier(0.8));
                modifiers.add(new ConcreteModifier(0.5));
                modifiers.add(new ConcreteModifier(0.2));
                return modifiers;
        }
        return modifiers;
    }


    /**
     * This method returns the expected summaryFile
     * depending on a scenario number.
     * The tree might not be written fully in the summary file
     * depending on the cuttOf value, this will be handled as well.
     *
     * @param scenario
     * @return
     */
    private String[] returnExpectedLinesFromScenario(int scenario) {
        switch (scenario) {
            case 1:
                return new String[]{
                        "Simulation ID : 1\t Probability : 0.5\t Modifier implemented :    *0.5",
                        "Simulation ID : 2\t Probability : 0.25\t Modifier implemented :    *0.5   *0.5",
                        "Simulation ID : 3\t Probability : 0.125\t Modifier implemented :    *0.5   *0.5   *0.5",
                        "Simulation ID : 4\t Probability : 0.0625\t Modifier implemented :    *0.5   *0.5   *0.5   *0.5"
                };
            case 2:
                return new String[]{
                        "Simulation ID : 1\t Probability : 0.2\t Modifier implemented :    *0.2",
                        "Simulation ID : 2\t Probability : 0.5\t Modifier implemented :    *0.5",
                        "Simulation ID : 3\t Probability : 0.1\t Modifier implemented :    *0.5   *0.2",
                        "Simulation ID : 4\t Probability : 0.25\t Modifier implemented :    *0.5   *0.5",
                        "Simulation ID : 5\t Probability : 0.05\t Modifier implemented :    *0.5   *0.5   *0.2",
                        "Simulation ID : 6\t Probability : 0.125\t Modifier implemented :    *0.5   *0.5   *0.5",
                        "Simulation ID : 7\t Probability : 0.04000000000000001\t Modifier implemented :    *0.2   *0.2",
                        "Simulation ID : 8\t Probability : 0.1\t Modifier implemented :    *0.2   *0.5",
                        "Simulation ID : 9\t Probability : 0.025\t Modifier implemented :    *0.5   *0.5   *0.5   *0.2",
                        "Simulation ID : 10\t Probability : 0.0625\t Modifier implemented :    *0.5   *0.5   *0.5   *0.5",
                        "Simulation ID : 11\t Probability : 0.020000000000000004\t Modifier implemented :    *0.5   *0.2   *0.2",
                        "Simulation ID : 12\t Probability : 0.05\t Modifier implemented :    *0.5   *0.2   *0.5",
                        "Simulation ID : 13\t Probability : 0.020000000000000004\t Modifier implemented :    *0.2   *0.5   *0.2",
                        "Simulation ID : 14\t Probability : 0.05\t Modifier implemented :    *0.2   *0.5   *0.5",
                };
            case 4:
                return new String[]{
                        "Simulation ID : 1\t Probability : 0.1\t Modifier implemented :    *0.1",
                        "Simulation ID : 2\t Probability : 0.8\t Modifier implemented :    *0.8",
                        "Simulation ID : 3\t Probability : 0.5\t Modifier implemented :    *0.5",
                        "Simulation ID : 4\t Probability : 0.2\t Modifier implemented :    *0.2",

                        "Simulation ID : 5\t Probability : 0.08000000000000002\t Modifier implemented :    *0.8   *0.1",
                        "Simulation ID : 6\t Probability : 0.6400000000000001\t Modifier implemented :    *0.8   *0.8",
                        "Simulation ID : 7\t Probability : 0.4\t Modifier implemented :    *0.8   *0.5",
                        "Simulation ID : 8\t Probability : 0.16000000000000003\t Modifier implemented :    *0.8   *0.2",

                        "Simulation ID : 9\t Probability : 0.06400000000000002\t Modifier implemented :    *0.8   *0.8   *0.1",
                        "Simulation ID : 10\t Probability : 0.5120000000000001\t Modifier implemented :    *0.8   *0.8   *0.8",
                        "Simulation ID : 11\t Probability : 0.32000000000000006\t Modifier implemented :    *0.8   *0.8   *0.5",
                        "Simulation ID : 12\t Probability : 0.12800000000000003\t Modifier implemented :    *0.8   *0.8   *0.2",

                        "Simulation ID : 13\t Probability : 0.051200000000000016\t Modifier implemented :    *0.8   *0.8   *0.8   *0.1",
                        "Simulation ID : 14\t Probability : 0.40960000000000013\t Modifier implemented :    *0.8   *0.8   *0.8   *0.8",
                        "Simulation ID : 15\t Probability : 0.25600000000000006\t Modifier implemented :    *0.8   *0.8   *0.8   *0.5",
                        "Simulation ID : 16\t Probability : 0.10240000000000003\t Modifier implemented :    *0.8   *0.8   *0.8   *0.2",

                        "Simulation ID : 17\t Probability : 0.05\t Modifier implemented :    *0.5   *0.1",
                        "Simulation ID : 18\t Probability : 0.4\t Modifier implemented :    *0.5   *0.8",
                        "Simulation ID : 19\t Probability : 0.25\t Modifier implemented :    *0.5   *0.5",
                        "Simulation ID : 20\t Probability : 0.1\t Modifier implemented :    *0.5   *0.2",
                };

        }
        return null;
    }

    /**
     * Delete everything in pathOUT
     * Claude generated
     * @throws IOException
     */
    private void cleanOutputDirectory() throws IOException {
        Path resultsPath = Paths.get(pathOUT);
        if (Files.exists(resultsPath)) {
            Files.walk(resultsPath)
                    .sorted(Comparator.reverseOrder())
                    .filter(p -> !p.equals(resultsPath)) // ← Don't delete root path
                    .forEach(p -> {
                        try { Files.delete(p); }
                        catch (IOException e) { e.printStackTrace(); }
                    });
        }
    }

    /**
     * Help method, used in all other tests
     * It tests content of SummaryFile
     * as well as the number of lines in it.
     *
     * @param cuttOfPlanning
     * @param typeCuttOfPlanning
     * @param modifiers
     * @param expectedLines
     * @throws IOException
     */
    private void runAndAssertSummaryFile(
            String cuttOfPlanning,
            String typeCuttOfPlanning,
            List<AModifier> modifiers,
            String[] expectedLines
    ) throws IOException {

        // Arrange
        cleanOutputDirectory();
        Files.createDirectories(Paths.get(pathOUT));
        Files.createDirectories(Paths.get(pathSIM));
        File tempDir = Files.createTempDirectory("structsim-test").toFile();
        File tempConfig = new File(tempDir, "config.properties");

        String configContent =
                "pathOUT = " + pathOUT + "\n" +
                        "pathParameters = parameters.txt\n" +
                        "pathSimulator = " + pathSIM + "\n" +
                        "pathToSimulatorResultFile = " + pathSIM + "/results/results.txt\n" +
                        "cuttOfPlanning = " + cuttOfPlanning + "\n" +
                        "typeCuttOfPlanning = " + typeCuttOfPlanning + "\n";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempConfig))) {
            bw.write(configContent);
        }
        InputStream pathConfigFile = new FileInputStream(tempConfig);
        SimpleSimulationHandler ssh = new SimpleSimulationHandler(modifiers);

        // Act
        Simulation s = new Simulation();
        try {
            s.startProgram(pathConfigFile, ssh);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Poll
        File summaryFile = new File(pathOUT + "/SummaryFile.txt");
        long timeout = 15_000;
        long start = System.currentTimeMillis();
        List<String> lines = new ArrayList<>();

        while (System.currentTimeMillis() - start < timeout) {
            if (summaryFile.exists()) {
                lines.clear();
                boolean foundBlankLine = false;
                try (BufferedReader br = new BufferedReader(new FileReader(summaryFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.trim().isEmpty()) {
                            foundBlankLine = true;
                            break;
                        }
                        lines.add(line);
                    }
                } catch (IOException e) {
                }
                if (foundBlankLine) break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

        // Assert
        assertTrue("SummaryFile.txt should exist", summaryFile.exists());
        assertEquals("Wrong number of simulations", expectedLines.length, lines.size());
        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals("Incorrect content at line " + (i + 1), expectedLines[i], lines.get(i));
        }

    }


    /**
     * This test tests the generated summary file
     * with all scenarios and relevant INT cuttOfValues.
     * Please don't change it,
     * it took me a whole weekend to make it work.
     *
     * @throws IOException
     */
    @ParameterizedTest
    @CsvSource({
            "1,1",
            "1,2",
            "1,3",
            "1,4",
            "2,1",
            "2,2",
            "2,3",
            "2,4",
            "2,5",
            "2,6",
            "2,7",
            "4,1",
            "4,2",
            "4,3",
            "4,4",
            "4,5"
    })
    public void test_INTcutoffType(int scenarioNumber, int cuttOfPlanning) throws IOException {
        String typeCuttOfPlanning = "INT";
        List<AModifier> modifiers = returnModifiersFromScenario(scenarioNumber);
        String[] expectedLines = returnExpectedLinesFromScenario(scenarioNumber);

        /*
        The cutOff represents the number of iterations of the algorithm
        = number of time a node is explored.
        So we need to keep a number of lines equals to
        numberOfIterations * numberOfModifiers, which means cuttOfPlanning*scenarioNumber
        */
        int numberOfLinesToKeep = cuttOfPlanning * scenarioNumber;

        String[] exactExpectedLines = Arrays.copyOfRange(expectedLines, 0, numberOfLinesToKeep);
        runAndAssertSummaryFile(String.valueOf(cuttOfPlanning), typeCuttOfPlanning, modifiers, exactExpectedLines);
    }





    /**
     * Same as previous test, but here we test the CRITERIA type.
     * @param scenarioNumber
     * @param cuttOfPlanning
     * @param expectedNumberOfLines
     * @throws IOException
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1,0.1,4",
            "1,0.2,3",
            "1,0.3,2",
            "1,0.4,2",
            "1,0.5,1",
            "1,0.6,1",
            "1,0.7,1",
            "1,0.8,1",
            "1,0.9,1",
            "2,0.1,10",
            "2,0.2,6",
            "2,0.3,4",
            "2,0.4,4",
            "2,0.5,2",
            "2,0.6,2",
            "2,0.7,2",
            "2,0.8,2",
            "2,0.9,2",
            "4,0.45,20",
            "4,0.5,16",
            "4,0.6,12",
            "4,0.7,8",
            "4,0.8,4",
            "4,0.9,4",
    })
    public void test_CRITERIAcutoffType(int scenarioNumber, double cuttOfPlanning, int expectedNumberOfLines) throws IOException {
        String typeCuttOfPlanning = "CRITERIA";
        List<AModifier> modifiers = returnModifiersFromScenario(scenarioNumber);
        String[] expectedLines = returnExpectedLinesFromScenario(scenarioNumber);
        String[] exactExpectedLines = Arrays.copyOfRange(expectedLines, 0, expectedNumberOfLines);
        runAndAssertSummaryFile(String.valueOf(cuttOfPlanning), typeCuttOfPlanning, modifiers, exactExpectedLines);
    }


    /**
     * This test tests the CRITERIA cuttOfType, with value 1.
     * With the current implementation of ExperimentPlanGenerator,
     * a first iteration is always done,
     * which is why in this test we expect to have as many lines
     * as there are modifiers.
     *
     * @param scenarioNumber
     * @param cuttOfPlanning
     * @param expectedNumberOfLines
     * @throws IOException
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1,1,1",
            "2,1,2",
            "4,1,4",
    })
    public void test_CRITERIAcutoffType_valueOne(int scenarioNumber, double cuttOfPlanning, int expectedNumberOfLines) throws IOException {
        String typeCuttOfPlanning = "CRITERIA";
        List<AModifier> modifiers = returnModifiersFromScenario(scenarioNumber);
        String[] expectedLines = returnExpectedLinesFromScenario(scenarioNumber);
        String[] exactExpectedLines = Arrays.copyOfRange(expectedLines, 0, expectedNumberOfLines);
        runAndAssertSummaryFile(String.valueOf(cuttOfPlanning), typeCuttOfPlanning, modifiers, exactExpectedLines);
    }


    /**
     * This test tests the case of a CRITERIA cutoff value of 0
     * In the current implementation, it will cause the program to crash
     * because the generated tree would be arithmetically infinite,
     * An if statement was added in the StartProgram class
     * to prevent the threads from running if that is the case.
     * @throws IOException
     */
    @Test
    public void test_CRITERIAcutoffType_valueZero() throws IOException {
        String typeCuttOfPlanning = "CRITERIA";
        int scenarioNumber = 1;
        int cuttOfPlanning = 0;
        List<AModifier> modifiers = returnModifiersFromScenario(scenarioNumber);

        new File(pathOUT + "/SummaryFile.txt").delete(); // Delete old summary file
        File tempDir = Files.createTempDirectory("structsim-test").toFile();
        File tempConfig = new File(tempDir, "config.properties");

        String configContent =
                "pathOUT = " + pathOUT + "\n" +
                        "pathParameters = parameters.txt\n" +
                        "pathSimulator = " + pathSIM + "\n" +
                        "pathToSimulatorResultFile = " + pathSIM + "/results/results.txt\n" +
                        "cuttOfPlanning = " + cuttOfPlanning + "\n" +
                        "typeCuttOfPlanning = " + typeCuttOfPlanning + "\n";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempConfig))) {
            bw.write(configContent);
        }
        InputStream pathConfigFile = new FileInputStream(tempConfig);
        SimpleSimulationHandler ssh = new SimpleSimulationHandler(modifiers);

        // Act
        Simulation s = new Simulation();
        try {
            s.startProgram(pathConfigFile, ssh);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File summaryFile = new File(pathOUT + "/SummaryFile.txt");

        // Assert
        assertFalse("SummaryFile.txt should not exist", summaryFile.exists());

    }
}