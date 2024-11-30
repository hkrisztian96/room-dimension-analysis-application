package gscf.task.roomdimension.dto;

import java.util.List;

/**
 * The {@code Result} record represents the result of processing room dimension data.
 *
 * @param totalSquareFeetOfWallpaper
 *            The total square feet of wallpaper needed to cover all rooms.
 * @param cubicRooms
 *            A list of {@link Room} objects that represent rooms that are cubic.
 * @param duplicateRooms
 *            A list of {@link Room} objects that represent rooms with duplicate dimensions.
 *
 * @author krisztian.hathazi
 */
public record Result(int totalSquareFeetOfWallpaper, List<Room> cubicRooms, List<Room> duplicateRooms) {
}
