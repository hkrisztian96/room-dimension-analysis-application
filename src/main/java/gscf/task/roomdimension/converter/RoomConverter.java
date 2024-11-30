package gscf.task.roomdimension.converter;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gscf.task.roomdimension.dto.Room;
import gscf.task.roomdimension.util.ParamValidatorUtil;

/**
 * The {@code RoomConverter} class implements the {@link Converter} interface to convert room dimension strings into {@link Room} objects.
 *
 * @author krisztian.hathazi
 */
public class RoomConverter implements Converter<Room, String> {

    /**
     * Converts a room dimension string in the format "LxWxH" to a {@code Room} object.
     *
     * @param roomDimensions
     *            A string representing the room's dimensions in the format "LxWxH".
     * @return A {@code Room} object representing the room with the specified dimensions, wallpaper needed, and cubic status.
     * @throws IllegalStateException
     *             If the given room dimension string does not match the expected pattern "LxWxH".
     */
    @Override
    public Room convert(String roomDimensions) {
        // Public method, so parameter validation is a must
        ParamValidatorUtil.requireNonBlank(roomDimensions, "roomDimensions");

        // Define the regex pattern for matching room dimensions
        String regex = "([1-9]\\d*)x([1-9]\\d*)x([1-9]\\d*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(roomDimensions);

        // If the roomDimensions matches the pattern, extract the dimensions
        if (matcher.matches()) {
            int length = Integer.parseInt(matcher.group(1));
            int width = Integer.parseInt(matcher.group(2));
            int height = Integer.parseInt(matcher.group(3));

            // Calculate wallpaper needed to cover the room
            int surfaceArea = 2 * (length * width + width * height + height * length);
            int smallestSideArea = Math.min(length * width, Math.min(width * height, height * length));
            int wallpaperNeeded = surfaceArea + smallestSideArea;

            // Check whether the room is cubic
            boolean isCubic = length == width && width == height;

            return new Room(roomDimensions, wallpaperNeeded, isCubic);
        }

        throw new IllegalStateException(MessageFormat.format("The given room dimension [{0}] does not match the LxWxH pattern.", roomDimensions));
    }
}
