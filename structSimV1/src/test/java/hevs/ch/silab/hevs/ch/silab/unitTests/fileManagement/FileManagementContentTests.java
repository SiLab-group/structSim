package hevs.ch.silab.hevs.ch.silab.unitTests.fileManagement;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static junit.framework.Assert.assertEquals;


/**
 * This class is used to test methods from FileManagement class
 * related to file reading
 * @author Matthias Gaillard
 */

public class FileManagementContentTests extends FileManagementTests {


    // Claude generated
    @Test
    void contentOfAFile_shouldReturnErrorMessage_whenFileDoesNotExist() throws IOException {
        // Arrange
        fileManagement.setFilename("/noFile.txt");

        // Act
        String result = fileManagement.contentOfAFile();

        // Assert
        assertEquals("this is not a file", result);
    }

    // Claude generated
    @Test
    void contentOfAFile_shouldReturnContent_whenFileExists() throws IOException {

        // Arrange
        Path tempFile = tempDir.resolve("test.txt");
        Files.writeString(tempFile, "Hello World");

        fileManagement.setFilename(tempFile.toString());

        // Act
        String result = fileManagement.contentOfAFile();

        // Assert
        assertEquals("Hello World", result);
    }

    // Claude generated
    @Test
    void contentOfAFile_shouldConcatenateLines_whenFileHasMultipleLines() throws IOException {

        // Arrange
        Path tempFile = tempDir.resolve("multiline.txt");
        Files.write(tempFile, java.util.List.of("line1", "line2", "line3"));

        fileManagement.setFilename(tempFile.toString());

        // Act
        String result = fileManagement.contentOfAFile();

        // Assert
        assertEquals("line1line2line3", result);
    }

    // Claude generated
    @Test
    void contentOfAFile_shouldReturnEmptyString_whenFileIsEmpty() throws IOException {

        // Arrange
        Path tempFile = tempDir.resolve("empty.txt");
        Files.createFile(tempFile);

        fileManagement.setFilename(tempFile.toString());

        // Act
        String result = fileManagement.contentOfAFile();

        // Assert
        assertEquals("", result);
    }



}
