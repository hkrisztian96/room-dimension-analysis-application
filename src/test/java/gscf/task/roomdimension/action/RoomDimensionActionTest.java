package gscf.task.roomdimension.action;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gscf.task.roomdimension.action.process.InputFileProcessor;
import gscf.task.roomdimension.dto.Result;
import gscf.task.roomdimension.exception.InvalidMethodParameterException;
import gscf.task.roomdimension.exception.SizeLimitExceededIOException;
import gscf.task.roomdimension.log.AppLogger;
import gscf.task.roomdimension.util.ResultPrinterUtil;
import gscf.task.roomdimension.validation.FileSizeValidator;

/**
 * {@link RoomDimensionAction} unit test class.
 *
 * @author krisztian.hathazi
 */
class RoomDimensionActionTest {

    private static final long MAX_SIZE_BYTES = 5L * 1024 * 1024;

    private static final Logger LOGGER = AppLogger.getLogger();

    private static final Handler MOCK_HANDLER = Mockito.mock(Handler.class);

    private RoomDimensionAction roomDimensionAction;

    private FileSizeValidator mockFileSizeValidator;

    private InputFileProcessor mockInputFileProcessor;

    @BeforeAll
    static void setupLogger() {
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(MOCK_HANDLER);
    }

    @BeforeEach
    void setupObjects() throws NoSuchFieldException, IllegalAccessException {
        roomDimensionAction = new RoomDimensionAction();

        mockFileSizeValidator = Mockito.mock(FileSizeValidator.class);
        mockInputFileProcessor = Mockito.mock(InputFileProcessor.class);

        injectPrivateField(roomDimensionAction, "fileSizeValidator", mockFileSizeValidator);
        injectPrivateField(roomDimensionAction, "inputFileProcessor", mockInputFileProcessor);
    }

    @AfterEach
    void resetMockHandler() {
        Mockito.reset(MOCK_HANDLER);
    }

    @Test
    @DisplayName("Should process successfully with valid file input")
    void testProcessWithValidArguments() throws Exception {
        String validFilePath = "test-input.txt";
        String[] args = { validFilePath };
        Result mockResult = Mockito.mock(Result.class);

        Mockito.when(mockResult.totalSquareFeetOfWallpaper()).thenReturn(100);
        Mockito.when(mockResult.cubicRooms()).thenReturn(Collections.emptyList());
        Mockito.when(mockResult.duplicateRooms()).thenReturn(Collections.emptyList());

        Mockito.doNothing().when(mockFileSizeValidator).validate(validFilePath, MAX_SIZE_BYTES);
        Mockito.when(mockInputFileProcessor.process(validFilePath)).thenReturn(mockResult);

        roomDimensionAction.process(args);

        Mockito.verify(mockFileSizeValidator).validate(validFilePath, MAX_SIZE_BYTES);
        Mockito.verify(mockInputFileProcessor).process(validFilePath);

        Mockito.verify(MOCK_HANDLER)
                .publish(
                        Mockito.argThat(
                                arg -> arg.getMessage()
                                        .contains(
                                                "The number of total square feet of wallpaper the company should order for all rooms is: [100]m2.")));
        Mockito.verify(MOCK_HANDLER).publish(Mockito.argThat(arg -> arg.getMessage().contains("Cubic shaped room dimensions are: []")));
        Mockito.verify(MOCK_HANDLER).publish(Mockito.argThat(arg -> arg.getMessage().contains("Duplicated room dimensions are: []")));
    }

    @Test
    @DisplayName("Should throw InvalidMethodParameterException when called with null parameter")
    void testProcessWithNullArguments() {
        Assertions.assertThrows(InvalidMethodParameterException.class, () -> roomDimensionAction.process(null));
    }

    @Test
    @DisplayName("Should throw InvalidMethodParameterException when called with empty array")
    void testProcessWithEmptyArguments() {
        Assertions.assertThrows(InvalidMethodParameterException.class, () -> roomDimensionAction.process(new String[] {}));
    }

    @Test
    @DisplayName("Should throw InvalidMethodParameterException and no interaction after - when called with two-element array")
    void testProcessWithMultipleArguments() {
        String[] args = { "test-input1.txt", "test-input2.txt" };
        Assertions.assertThrows(InvalidMethodParameterException.class, () -> roomDimensionAction.process(args));
        Mockito.verifyNoInteractions(mockFileSizeValidator, mockInputFileProcessor);
    }

    @Test
    @DisplayName("Should be no interactions after validation - when provided with a too large input file")
    void testProcessWithFileSizeExceedingLimit() throws Exception {
        String filePath = "large-input.txt";
        String[] args = { filePath };

        Mockito.doThrow(SizeLimitExceededIOException.class).when(mockFileSizeValidator).validate(filePath, MAX_SIZE_BYTES);

        roomDimensionAction.process(args);

        Mockito.verify(mockFileSizeValidator).validate(filePath, MAX_SIZE_BYTES);
        Mockito.verifyNoInteractions(mockInputFileProcessor);
    }

    @Test
    @DisplayName("Should be no interactions after IOException occurred during input file processing")
    void testProcessWithIOExceptionDuringProcessing() throws Exception {
        String filePath = "valid-input.txt";
        String[] args = { filePath };

        Mockito.doNothing().when(mockFileSizeValidator).validate(filePath, MAX_SIZE_BYTES);
        Mockito.doThrow(IOException.class).when(mockInputFileProcessor).process(filePath);

        roomDimensionAction.process(args);

        Mockito.verify(mockFileSizeValidator).validate(filePath, MAX_SIZE_BYTES);
        Mockito.verify(mockInputFileProcessor).process(filePath);
        Mockito.verifyNoInteractions(Mockito.mock(ResultPrinterUtil.class));
    }

    private void injectPrivateField(Object target, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, fieldValue);
    }
}
