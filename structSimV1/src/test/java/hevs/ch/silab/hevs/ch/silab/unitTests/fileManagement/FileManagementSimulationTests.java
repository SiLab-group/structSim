package hevs.ch.silab.hevs.ch.silab.unitTests.fileManagement;

import ch.hevs.silab.structuredsim.experimenthandling.Environment;
import ch.hevs.silab.structuredsim.experimenthandling.Options;
import ch.hevs.silab.structuredsim.interfaces.ASimulationSystemHandler;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * This class is used to test methods from FileManagement class
 * related Environment and Options classes
 * @see FileManagementTests
 * @see Environment
 * @see Options
 * @author Matthias Gaillard
 */
public class FileManagementSimulationTests extends FileManagementTests {

    // GPT generated
    @Test
    void saveSimultationResult_shouldSaveResultAndReturnPath_whenDirectoryExists() throws Exception {

        // Arrange

        Options options = new Options();
        options.setFolderPathOUT(tempDir.toString());
        fileManagement.setOptions(options);

        Environment env = new Environment();

        Path simulationFolder = tempDir.resolve("_sim1");
        Files.createDirectories(simulationFolder);

        String expectedResult = "SUCCESS";

        // Act
        String returnedPath =
                fileManagement.saveSimultationResult(expectedResult, env);

        // Assert
        String expectedPath =
                simulationFolder.resolve("results.txt").toString();

        assertEquals(Path.of(expectedPath).normalize(),
                Path.of(returnedPath).normalize());
        assertTrue(Files.exists(Path.of(returnedPath)));

        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(returnedPath)) {
            properties.load(fis);
        }

        assertEquals(expectedResult, properties.getProperty("Result"));
    }



    // ChatGPT generated
    @Test
    void createNewFolder_shouldCreateResultAndSimulatorFolders() throws IOException {

        Options options = new Options();
        options.setFolderPathOUT(tempDir.resolve("results").toString());
        options.setPathSimulator(tempDir.resolve("simulator").toString());

        fileManagement.setOptions(options);

        Files.createDirectories(tempDir.resolve("results"));
        Files.createDirectories(tempDir.resolve("simulator"));

        Environment env = new Environment();

        String returnedPath = fileManagement.createNewFolder(env);

        Path expectedResultFolder =
                tempDir.resolve("results").resolve("_sim1");

        Path expectedSimulatorFolder =
                tempDir.resolve("simulator").resolve("_sim1");

        assertEquals(
                expectedResultFolder.normalize(),
                Path.of(returnedPath).normalize());

        assertTrue(Files.exists(expectedResultFolder));
        assertTrue(Files.isDirectory(expectedResultFolder));

        assertTrue(Files.exists(expectedSimulatorFolder));
        assertTrue(Files.isDirectory(expectedSimulatorFolder));
    }


    // ChatGPT generated
    @Test
    void createNewFolderSimulation_shouldCreateSimulationFolderAndWriteParametersFile() {

        // Arrange

        Options options = new Options();
        options.setFolderPathOUT(tempDir.resolve("results").toString());
        options.setPathSimulator(tempDir.resolve("simulator").toString());

        fileManagement.setOptions(options);

        Environment env = new Environment();

        ASimulationSystemHandler glueCode =
                mock(ASimulationSystemHandler.class);

        try {
            Files.createDirectories(tempDir.resolve("results"));
            Files.createDirectories(tempDir.resolve("simulator"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Act
        fileManagement.createNewFolderSimulation(env, glueCode);

        // Assert
        verify(glueCode, times(1))
                .writeParametersFile(
                        eq(env.getSetOfParameters()),
                        anyString());

        assertNotNull(env.getPathSaveResult());
        assertTrue(env.getPathSaveResult().contains("_sim"));
    }
}
