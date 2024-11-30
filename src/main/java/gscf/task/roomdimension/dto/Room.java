package gscf.task.roomdimension.dto;

/**
 * The {@code Room} record represents the characteristics of a room, including its unique identifier, the wallpaper needed to cover the room, and
 * whether the room is cubic.
 *
 * @param key
 *            A unique identifier for the room (e.g., a string representing room dimensions).
 * @param wallpaperNeeded
 *            The total amount of wallpaper needed to cover the room, in square feet.
 * @param isCubic
 *            A boolean value indicating whether the room is cubic (i.e., if length, width, and height are equal).
 *
 * @author krisztian.hathazi
 */
public record Room(String key, int wallpaperNeeded, boolean isCubic) {
}
