package gscf.task.roomdimension.action;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import gscf.task.roomdimension.action.process.InputFileProcessor;
import gscf.task.roomdimension.converter.RoomConverter;
import gscf.task.roomdimension.dto.Result;
import gscf.task.roomdimension.dto.Room;
import gscf.task.roomdimension.exception.InvalidMethodParameterException;

/**
 * {@link InputFileProcessor} unit test class.
 *
 * @author krisztian.hathazi
 */
class InputFileProcessorTest {

    private RoomConverter roomConverter;

    private InputFileProcessor inputFileProcessor;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setupObjects() {
        roomConverter = Mockito.mock(RoomConverter.class);
        inputFileProcessor = new InputFileProcessor(roomConverter);
    }

    @Test
    @DisplayName("Should process successfully with a valid input file")
    void testProcessValidInputFile() throws IOException {
        Room room1 = new Room("4x4x4", 112, true);
        Room room2 = new Room("4x5x6", 168, false);
        Room room3 = new Room("4x4x4", 112, true);

        Mockito.when(roomConverter.convert("4x4x4")).thenReturn(room1);
        Mockito.when(roomConverter.convert("4x5x6")).thenReturn(room2);
        Mockito.when(roomConverter.convert("4x4x4")).thenReturn(room3);

        Path tempFile = tempDir.resolve("test-input.txt");
        Files.write(tempFile, """
                4x4x4
                4x5x6
                4x4x4
                """.getBytes());

        Result result = inputFileProcessor.process(tempFile.toAbsolutePath().toString());

        Assertions.assertAll(
                () -> Assertions.assertEquals(392, result.totalSquareFeetOfWallpaper()),
                () -> Assertions.assertEquals(List.of(room1, room3), result.cubicRooms()),
                () -> Assertions.assertEquals(Set.of(room1), Set.copyOf(result.duplicateRooms())));

        Mockito.verify(roomConverter, Mockito.times(2)).convert("4x4x4");
        Mockito.verify(roomConverter, Mockito.times(1)).convert("4x5x6");
    }

    @Test
    @DisplayName("Should throw InvalidMethodParameterException when providing a blank file path")
    void testProcessThrowsForBlankFilePath() {
        InvalidMethodParameterException exception = Assertions.assertThrows(InvalidMethodParameterException.class, () -> {
            inputFileProcessor.process("   ");
        });
        Assertions.assertEquals("[inputFilePath] parameter is blank.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidMethodParameterException when providing a missing file path")
    void testProcessThrowsForMissingFile() {
        IOException exception = Assertions.assertThrows(IOException.class, () -> {
            inputFileProcessor.process("non-existent-file.txt");
        });
        Assertions.assertTrue(exception.getMessage().contains("non-existent-file.txt"));
    }

    @Test
    @DisplayName("Should process successfully with an empty file a provide result(0, empty, empty)")
    void testProcessEmptyFile() throws IOException {
        Path tempFile = tempDir.resolve("empty-input.txt");
        Files.write(tempFile, "".getBytes());

        Result result = inputFileProcessor.process(tempFile.toAbsolutePath().toString());

        Assertions.assertAll(
                () -> Assertions.assertEquals(0, result.totalSquareFeetOfWallpaper()),
                () -> Assertions.assertTrue(result.cubicRooms().isEmpty()),
                () -> Assertions.assertTrue(result.duplicateRooms().isEmpty()));
    }
}
