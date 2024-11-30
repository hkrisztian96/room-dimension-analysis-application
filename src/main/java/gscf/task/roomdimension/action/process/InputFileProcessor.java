package gscf.task.roomdimension.action.process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gscf.task.roomdimension.converter.RoomConverter;
import gscf.task.roomdimension.dto.Result;
import gscf.task.roomdimension.dto.Room;
import gscf.task.roomdimension.util.ParamValidatorUtil;

/**
 * Processes input data from a file and extracts business-related information. This class uses a {@link RoomConverter} to convert room dimension
 * strings into {@link Room} objects. It reads the input file line-by-line, processes each room's data, and then returns a {@link Result} containing
 * the following:
 * <ul>
 * <li>Total wallpaper needed for all rooms</li>
 * <li>A list of cubic rooms, sorted by wallpaper needed</li>
 * <li>A list of duplicate room dimensions</li>
 * </ul>
 *
 * @author krisztian.hathazi
 */
public class InputFileProcessor {

    private final RoomConverter roomConverter;

    /**
     * Constructs an {@link InputFileProcessor} with the specified {@link RoomConverter}.
     *
     * @param roomConverter
     *            The {@link RoomConverter} instance used to convert room dimensions from the input file into {@link Room} objects.
     */
    public InputFileProcessor(RoomConverter roomConverter) {
        this.roomConverter = roomConverter;
    }

    /**
     * Processes the input file at the specified path, extracting relevant data about rooms, including total wallpaper needed, cubic rooms, and
     * duplicate rooms. The input file should contain room dimension data in a format that the {@link RoomConverter} can understand. Each line in the
     * file represents one room.
     *
     * @param inputFilePath
     *            The path to the input file containing room dimension data.
     * @return A {@link Result} object.
     * @throws IOException
     *             If an error occurs while reading the input file.
     * @throws IllegalArgumentException
     *             If the {@code inputFilePath} is null or blank.
     */
    public Result process(String inputFilePath) throws IOException {
        // Public method, so parameter validation is a must
        ParamValidatorUtil.requireNonBlank(inputFilePath, "inputFilePath");

        Set<Room> seenRooms = new HashSet<>();
        Set<Room> duplicateRooms = new HashSet<>();
        List<Room> cubicRooms = new ArrayList<>();

        int totalWallpaperNeeded = 0;

        // Stream the input file and extract business required data
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {

            String roomDimensons;

            while ((roomDimensons = br.readLine()) != null) {
                Room room = roomConverter.convert(roomDimensons);
                totalWallpaperNeeded += room.wallpaperNeeded();
                if (room.isCubic()) {
                    cubicRooms.add(room);
                }
                if (!seenRooms.add(room)) {
                    duplicateRooms.add(room);
                }
            }

        }

        // Sort the cubic rooms accordingly
        cubicRooms.sort(Comparator.comparing(Room::wallpaperNeeded));

        return new Result(totalWallpaperNeeded, cubicRooms, duplicateRooms.stream().toList());
    }

}
