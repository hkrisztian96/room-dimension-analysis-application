package gscf.task.roomdimension.util;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gscf.task.roomdimension.dto.Result;
import gscf.task.roomdimension.dto.Room;
import gscf.task.roomdimension.log.AppLogger;

/**
 * {@link ResultPrinterUtil} unit test class.
 *
 * @author krisztian.hathazi
 */
class ResultPrinterUtilTest {

    private static final Logger LOGGER = AppLogger.getLogger();

    private static final Handler MOCK_HANDLER = Mockito.mock(Handler.class);

    @BeforeAll
    static void setupLogger() {
        LOGGER.addHandler(MOCK_HANDLER);
        LOGGER.setUseParentHandlers(false);
    }

    @AfterEach
    void resetMockHandler() {
        Mockito.reset(MOCK_HANDLER);
    }

    @Test
    @DisplayName("Should log total square feet of wallpaper needed")
    void testPrintTotalSquareFeetOfWallpaperNeeded() {
        Result result = new Result(100, List.of(), List.of());
        ResultPrinterUtil.printTotalSquareFeetOfWallpaperNeeded(result);
        Mockito.verify(MOCK_HANDLER)
                .publish(
                        Mockito.argThat(
                                arg -> arg.getMessage()
                                        .contains(
                                                "The number of total square feet of wallpaper the company should order for all rooms is: [100]m2.")));
    }

    @Test
    @DisplayName("Should log cubic shaped room dimensions")
    void testPrintCubicShapedRooms() {
        Room room1 = new Room("3x3x3", 54, true);
        Room room2 = new Room("4x4x4", 96, true);
        Result result = new Result(0, List.of(room1, room2), List.of());
        ResultPrinterUtil.printCubicShapedRooms(result);
        Mockito.verify(MOCK_HANDLER).publish(Mockito.argThat(arg -> arg.getMessage().contains("Cubic shaped room dimensions are: [3x3x3, 4x4x4]")));
    }

    @Test
    @DisplayName("Should log duplicated room dimensions")
    void testPrintDuplicatedRooms() {
        Room room1 = new Room("3x4x5", 94, false);
        Room room2 = new Room("6x7x8", 314, false);
        Result result = new Result(0, List.of(), List.of(room1, room2));
        ResultPrinterUtil.printDuplicatedRooms(result);
        Mockito.verify(MOCK_HANDLER).publish(Mockito.argThat(arg -> arg.getMessage().contains("Duplicated room dimensions are: [3x4x5, 6x7x8]")));
    }
}
