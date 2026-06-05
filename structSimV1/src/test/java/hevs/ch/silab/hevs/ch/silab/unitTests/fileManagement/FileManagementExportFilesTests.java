package hevs.ch.silab.hevs.ch.silab.unitTests.fileManagement;

import ch.hevs.silab.structuredsim.experimenthandling.Measure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Vector;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is used to test methods from FileManagement class
 * related to measures and modifiers
 * @author Matthias Gaillard
 */
public class FileManagementExportFilesTests extends FileManagementTests {

    // ChatGPT generated
    @Test
    void createMeasuresFile_shouldCreateMeasuresFile(@TempDir Path tempDir) throws Exception {

        // Arrange

        Vector<Measure> measures = new Vector<>();

        Measure m1 = new Measure("throughput", "12");
        Measure m2 = new Measure("latency", "50");

        measures.add(m1);
        measures.add(m2);

        Path measuresFile = tempDir.resolve("measures.txt");

        // Act
        fileManagement.createMeasuresFile(
                measures,
                measuresFile.toString());

        // Assert
        assertTrue(Files.exists(measuresFile));

        List<String> lines = Files.readAllLines(measuresFile);

        assertEquals(2, lines.size());
        assertEquals("throughput=12", lines.get(0));
        assertEquals("latency=50", lines.get(1));
    }

    // ChatGPT generated
    @Test
    void createModifierFile_shouldCreateSummaryFile(@TempDir Path tempDir) throws Exception {

        // Arrange

        String modifier = "SIMULATION_FINISHED";

        // Act
        fileManagement.createModifierFile(
                tempDir.toString(),
                modifier);

        // Assert
        Path summaryFile =
                tempDir.resolve("SummaryFile.txt");

        assertTrue(Files.exists(summaryFile));

        List<String> lines =
                Files.readAllLines(summaryFile);

        assertEquals(1, lines.size());
        assertEquals(modifier, lines.get(0));
    }


}
