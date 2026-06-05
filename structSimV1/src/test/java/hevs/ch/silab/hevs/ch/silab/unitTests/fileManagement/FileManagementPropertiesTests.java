package hevs.ch.silab.hevs.ch.silab.unitTests.fileManagement;

import ch.hevs.silab.structuredsim.experimenthandling.Options;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test methods from FileManagement class
 * related to data serialization
 * @author Matthias Gaillard
 */

public class FileManagementPropertiesTests extends FileManagementTests {

    // GPT generated
    @Test
    void loadDataFromPropertiesFile_shouldLoadPropertiesFile() {

        // Arrange
        String propertiesContent =
                "pathParameters=params.txt\n" +
                        "pathOUT=C:/output\n" +
                        "pathSimulator=simulator.exe\n" +
                        "pathToSimulatorResultFile=result.txt\n" +
                        "cuttOfPlanning=10\n" +
                        "typeCuttOfPlanning=INT\n";

        InputStream inputStream =
                new ByteArrayInputStream(
                        propertiesContent.getBytes(StandardCharsets.UTF_8));

        // Act
        Options options =
                fileManagement.loadDataFromPropertiesFile(inputStream);

        // Assert
        assertEquals("params.txt", options.getPathParameters());
        assertEquals("C:/output", options.getFolderPathOUT());
        assertEquals("simulator.exe", options.getPathSimulator());
        assertEquals("result.txt",
                options.getPathToSimulatorResultFile());

        assertEquals("INT", options.getTypeOfCuttOfPlanning());
        assertEquals(10, options.getCuttOfPlanning());
    }




    @Test
    void loadDataFromPropertiesFile_shouldLoadCriteriaPlanningType() {

        // Arrange
        String propertiesContent =
                "cuttOfPlanning=0.95\n" +
                        "typeCuttOfPlanning=CRITERIA\n";

        InputStream inputStream =
                new ByteArrayInputStream(
                        propertiesContent.getBytes(StandardCharsets.UTF_8));

        // Act
        Options options =
                fileManagement.loadDataFromPropertiesFile(inputStream);

        // Assert
        assertEquals("CRITERIA",
                options.getTypeOfCuttOfPlanning());

        assertEquals(0.95,
                options.getStopCriteria(),
                delta);
    }

    //ChatGPT generated
    @ParameterizedTest
    @CsvSource({
            "DAY,15",
            "HOURS,10",
            "MINUTES,45"
    })
    void loadDataFromPropertiesFile_shouldLoadCalendarBasedPlanningTypes(String type, int value) {

        // Arrange
        String propertiesContent =
                "cuttOfPlanning=" + value + "\n" +
                        "typeCuttOfPlanning=" + type + "\n";

        InputStream inputStream =
                new ByteArrayInputStream(
                        propertiesContent.getBytes(StandardCharsets.UTF_8));

        // Act
        Options options =
                fileManagement.loadDataFromPropertiesFile(inputStream);

        // Assert
        assertEquals(type, options.getTypeOfCuttOfPlanning());

        Calendar calendar = options.getCuttOfPlanningH();

        switch (type) {
            case "DAY":
                assertEquals(value, calendar.get(Calendar.DATE));
                break;

            case "HOURS":
                assertEquals(value, calendar.get(Calendar.HOUR_OF_DAY));
                break;

            case "MINUTES":
                assertEquals(value, calendar.get(Calendar.MINUTE));
                break;
        }
    }

    //ChatGPT generated
    @Test
    void writeDataInPropertiesFile_shouldCreateAndWritePropertiesFile() throws Exception {

        // Arrange
        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");

        Path file = tempDir.resolve("test.properties");

        // Act
        fileManagement.writeDataInPropertiesFile(
                data,
                file.toString(),
                false);

        // Assert
        assertTrue(Files.exists(file));

        Properties properties = new Properties();

        try (FileInputStream fis =
                     new FileInputStream(file.toFile())) {
            properties.load(fis);
        }

        assertEquals("value1", properties.getProperty("key1"));
        assertEquals("value2", properties.getProperty("key2"));
    }


    //ChatGPT generated
    @Test
    void writeDataInPropertiesFile_shouldOverwriteExistingFile() throws Exception {

        //Arrange

        Path file = tempDir.resolve("test.properties");

        LinkedHashMap<String, String> firstData = new LinkedHashMap<>();
        firstData.put("oldKey", "oldValue");

        fileManagement.writeDataInPropertiesFile(
                firstData,
                file.toString(),
                false);

        LinkedHashMap<String, String> secondData = new LinkedHashMap<>();
        secondData.put("newKey", "newValue");

        // Act
        fileManagement.writeDataInPropertiesFile(
                secondData,
                file.toString(),
                false);

        Properties properties = new Properties();

        try (FileInputStream fis =
                     new FileInputStream(file.toFile())) {
            properties.load(fis);
        }

        // Assert
        assertNull(properties.getProperty("oldKey"));
        assertEquals("newValue", properties.getProperty("newKey"));
    }


    //ChatGPT generated
    @Test
    void writeDataInPropertiesFile_shouldCreateFileIfItDoesNotExist() {

        //Arrange
        Path file = tempDir.resolve("newFile.properties");

        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put("test", "value");

        // Act
        fileManagement.writeDataInPropertiesFile(
                data,
                file.toString(),
                false);

        // Assert
        assertTrue(Files.exists(file));
    }

    //ChatGPT generated
    @Test
    void writeDataInPropertiesFile_shouldNotCreateFileWhenParentDirectoryDoesNotExist() {

        // Arrange
        Path file =
                tempDir.resolve("unknown")
                        .resolve("folder")
                        .resolve("test.properties");

        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put("key", "value");

        // Act
        fileManagement.writeDataInPropertiesFile(
                data,
                file.toString(),
                false);

        // Assert
        assertFalse(Files.exists(file));
    }



}
