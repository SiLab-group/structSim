package hevs.ch.silab.hevs.ch.silab.unitTests.fileManagement;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test methods from FileManagement class
 * related to pure file system (copying, moving...)
 * @see FileManagementTests
 * @author Matthias Gaillard
 */

public class FileManagementFileOperationsTests extends FileManagementTests {


    // Claude generated
    @Test
    void moveFile_shouldMoveFile_whenOriginExists() throws IOException {
        // Arrange
        Path origin = tempDir.resolve("origin.txt");
        Path destination = tempDir.resolve("destination.txt");
        Files.writeString(origin, "fileContent");

        // Act
        fileManagement.moveFile(origin.toString(), destination.toString());

        // Assert
        assertFalse(Files.exists(origin));
        assertTrue(Files.exists(destination));
        assertEquals("fileContent", Files.readString(destination));
    }

    // Claude generated
    @Test
    void moveFile_shouldReplaceDestination_whenDestinationAlreadyExists() throws IOException {
        // Arrange
        Path origin = tempDir.resolve("origin.txt");
        Path destination = tempDir.resolve("destination.txt");
        Files.writeString(origin, "newContent");
        Files.writeString(destination, "oldContent");

        // Act
        fileManagement.moveFile(origin.toString(), destination.toString());

        // Assert
        assertFalse(Files.exists(origin));
        assertEquals("newContent", Files.readString(destination));
    }

    // Claude generated
    @Test
    void moveFile_shouldNotThrow_whenOriginDoesNotExist() {
        // Arrange
        String nonExistentOrigin = tempDir.resolve("ghost.txt").toString();
        String destination       = tempDir.resolve("destination.txt").toString();

        // Act & Assert
        assertDoesNotThrow(() -> fileManagement.moveFile(nonExistentOrigin, destination));
        assertFalse(Files.exists(Path.of(destination)));
    }


    // Claude generated
    @Test
    void copyFile_shouldCopyFile_whenSourceExists() throws IOException {
        // Arrange
        Path source      = tempDir.resolve("source.txt");
        Path destination = tempDir.resolve("destination.txt");
        Files.writeString(source, "file content");

        // Act
        fileManagement.copyFile(source.toString(), destination.toString());

        // Assert
        assertTrue(Files.exists(source),      "Source file must still exist after copy");
        assertTrue(Files.exists(destination), "Destination file must have been created");
        assertEquals("file content", Files.readString(destination));
    }

    // Claude generated
    @Test
    void copyFile_shouldReplaceDestination_whenDestinationAlreadyExists() throws IOException {
        // Arrange
        Path source      = tempDir.resolve("source.txt");
        Path destination = tempDir.resolve("destination.txt");
        Files.writeString(source,      "new content");
        Files.writeString(destination, "old content");

        // Act
        fileManagement.copyFile(source.toString(), destination.toString());

        // Assert
        assertTrue(Files.exists(source), "Source file must still exist after copy");
        assertEquals("new content", Files.readString(destination));
    }

    // Claude generated
    @Test
    void copyFile_shouldNotThrow_whenSourceDoesNotExist() {
        // Arrange
        String nonExistentSource = tempDir.resolve("ghost.txt").toString();
        String destination       = tempDir.resolve("destination.txt").toString();

        // Act & Assert
        // The method catches Exception internally: it must not propagate any exception
        assertDoesNotThrow(() -> fileManagement.copyFile(nonExistentSource, destination));
        assertFalse(Files.exists(Path.of(destination)),
                "No destination file should be created if source does not exist");
    }

    // Claude generated
    @Test
    void createFolder_shouldCreateFolder_whenPathIsValid() {
        // Arrange
        Path newFolder = tempDir.resolve("newFolder");

        // Act
        fileManagement.createFolder(newFolder.toString());

        // Assert
        assertTrue(Files.exists(newFolder),    "Folder must exist after creation");
        assertTrue(Files.isDirectory(newFolder), "Created path must be a directory");
    }

    // Claude generated
    @Test
    void createFolder_shouldNotThrow_whenFolderAlreadyExists() throws IOException {
        // Arrange
        Path existingFolder = tempDir.resolve("existingFolder");
        Files.createDirectory(existingFolder);
        Files.writeString(existingFolder.resolve("file.txt"), "existing content");

        // Act & Assert
        // mkdir() returns false silently if the folder already exists: no exception expected
        assertDoesNotThrow(() -> fileManagement.createFolder(existingFolder.toString()));
        assertTrue(Files.exists(existingFolder.resolve("file.txt")),
                "Existing folder content must remain intact");
    }

    // Claude generated
    @Test
    void createFolder_shouldNotCreateFolder_whenParentDoesNotExist() {
        // Arrange
        Path nestedFolder = tempDir.resolve("nonExistentParent/newFolder");

        // Act
        fileManagement.createFolder(nestedFolder.toString());

        // Assert
        assertFalse(Files.exists(nestedFolder),
                "Folder must not be created if parent directory does not exist");
    }

}
