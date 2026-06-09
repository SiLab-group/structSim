package hevs.ch.silab.hevs.ch.silab.unitTests.fileManagement;

import ch.hevs.silab.structuredsim.util.FileManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;

/**
 * This class is used to test methods from FileManagement class
 * Subclasses will do the actual tests.
 * @see FileManagement
 * @author Matthias Gaillard
 */
public class FileManagementTests {

    protected FileManagement fileManagement;

    protected double delta = 1e-9;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileManagement = new FileManagement();
    }

}
