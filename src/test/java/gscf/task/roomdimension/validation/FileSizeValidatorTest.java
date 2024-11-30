package gscf.task.roomdimension.validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import gscf.task.roomdimension.exception.InvalidMethodParameterException;
import gscf.task.roomdimension.exception.SizeLimitExceededIOException;

/**
 * {@link FileSizeValidator} unit test class.
 *
 * @author krisztian.hathazi
 */
class FileSizeValidatorTest {

    private static final long MAX_SIZE_BYTES = 1024;

    private final FileSizeValidator fileSizeValidator = new FileSizeValidator();

    @TempDir
    private Path tempDir;

    @Test
    @DisplayName("Should validate successfully when file size is within the limit")
    void testValidateSuccessfulValidation() throws IOException {
        Path tempFile = tempDir.resolve("test-input.txt");
        Files.write(tempFile, """
                13x29x10
                28x18x6
                22x28x26
                """.getBytes());
        Assertions.assertDoesNotThrow(() -> fileSizeValidator.validate(tempFile.toString(), MAX_SIZE_BYTES));
    }

    @Test
    @DisplayName("Should throw SizeLimitExceededIOException when file size exceeds the limit")
    void testValidateFileExceedsSizeLimit() throws IOException {
        Path tempFile = tempDir.resolve("too-large-input.txt");
        Files.write(tempFile, "1x2x3".repeat(2048).getBytes()); // File size is for sure more thank 1KB
        Assertions.assertThrows(SizeLimitExceededIOException.class, () -> fileSizeValidator.validate(tempFile.toString(), MAX_SIZE_BYTES));
    }

    @Test
    @DisplayName("Should throw InvalidMethodParameterException for blank file path")
    void testValidateBlankFilePath() {
        String blankFilePath = "  ";
        Assertions.assertThrows(InvalidMethodParameterException.class, () -> fileSizeValidator.validate(blankFilePath, MAX_SIZE_BYTES));
    }

    @Test
    @DisplayName("Should throw NoSuchFileException when file does not exist")
    void testValidateNonExistentFile() {
        String nonExistentFilePath = tempDir.resolve("non-existent-file.txt").toString();
        Assertions.assertThrows(NoSuchFileException.class, () -> fileSizeValidator.validate(nonExistentFilePath, MAX_SIZE_BYTES));
    }

    @Test
    @DisplayName("Should throw IOException for unreadable file")
    void testValidateUnreadableFile() throws IOException {
        Path unreadableFile = tempDir.resolve("unreadable-file.txt");
        Files.write(unreadableFile, "Test content".getBytes());
        unreadableFile.toFile().setReadable(false);
        IOException exception = Assertions
                .assertThrows(IOException.class, () -> fileSizeValidator.validate(unreadableFile.toString(), MAX_SIZE_BYTES));
        Assertions.assertTrue(exception.getMessage().contains("unreadable-file.txt"));
    }
}
