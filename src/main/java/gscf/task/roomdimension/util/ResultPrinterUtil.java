package gscf.task.roomdimension.util;

import java.text.MessageFormat;
import java.util.logging.Logger;

import gscf.task.roomdimension.dto.Result;
import gscf.task.roomdimension.dto.Room;
import gscf.task.roomdimension.log.AppLogger;

/**
 * Utility class for printing results related to room dimensions and wallpaper calculations.
 *
 * @author krisztian.hathazi
 */
public final class ResultPrinterUtil {

    private static final Logger LOGGER = AppLogger.getLogger();

    private static final String RESULT = "result";

    private ResultPrinterUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Prints the total square feet of wallpaper needed for all rooms.
     *
     * @param result
     *            The {@link Result} object containing the total wallpaper information.
     * @throws gscf.task.roomdimension.exception.InvalidMethodParameterException
     *             If the result object is null.
     */
    public static void printTotalSquareFeetOfWallpaperNeeded(Result result) {
        // Public method, so parameter validation is a must
        ParamValidatorUtil.requireNonNull(result, RESULT);
        String message = MessageFormat.format(
                "The number of total square feet of wallpaper the company should order for all rooms is: [{0}]m2.",
                result.totalSquareFeetOfWallpaper());
        LOGGER.fine(message);
    }

    /**
     * Prints the dimensions of all cubic shaped rooms.
     *
     * @param result
     *            The {@link Result} object containing cubic room data.
     * @throws gscf.task.roomdimension.exception.InvalidMethodParameterException
     *             If the result object is null.
     */
    public static void printCubicShapedRooms(Result result) {
        // Public method, so parameter validation is a must
        ParamValidatorUtil.requireNonNull(result, RESULT);
        String message = MessageFormat.format("Cubic shaped room dimensions are: {0}", result.cubicRooms().stream().map(Room::key).toList());
        LOGGER.fine(message);
    }

    /**
     * Prints the dimensions of all duplicated rooms.
     *
     * @param result
     *            The {@link Result} object containing duplicated room data.
     * @throws gscf.task.roomdimension.exception.InvalidMethodParameterException
     *             If the result object is null.
     */
    public static void printDuplicatedRooms(Result result) {
        // Public method, so parameter validation is a must
        ParamValidatorUtil.requireNonNull(result, RESULT);
        String message = MessageFormat.format("Duplicated room dimensions are: {0}", result.duplicateRooms().stream().map(Room::key).toList());
        LOGGER.fine(message);
    }
}
