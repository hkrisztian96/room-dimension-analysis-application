package gscf.task.roomdimension.converter;

import java.text.MessageFormat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import gscf.task.roomdimension.dto.Room;
import gscf.task.roomdimension.exception.InvalidMethodParameterException;

/**
 * {@link RoomConverter} unit test class.
 *
 * @author krisztian.hathazi
 */
class RoomConverterTest {

    private final RoomConverter roomConverter = new RoomConverter();

    @Test
    @DisplayName("Should convert successfully with valid room dimensions")
    void testConvertValidRoomDimensions() {
        String roomDimensions = "3x4x5";

        Room room = roomConverter.convert(roomDimensions);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(room, "Room should not be null"),
                () -> Assertions.assertEquals(roomDimensions, room.key(), "Room key should match the input dimensions"),
                () -> Assertions.assertEquals(106, room.wallpaperNeeded(), "Wallpaper needed calculation is incorrect"),
                () -> Assertions.assertFalse(room.isCubic(), "Room should not be cubic"));
    }

    @Test
    @DisplayName("Should convert successfully with cubic room dimensions")
    void testConvertValidCubicRoomDimensions() {
        String roomDimensions = "4x4x4";

        Room room = roomConverter.convert(roomDimensions);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(room, "Room should not be null"),
                () -> Assertions.assertEquals(roomDimensions, room.key(), "Room key should match the input dimensions"),
                () -> Assertions.assertEquals(112, room.wallpaperNeeded(), "Wallpaper needed calculation is incorrect"),
                () -> Assertions.assertTrue(room.isCubic(), "Room should be cubic"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "0x5x6", "-3x4x5", "1-2-3" })
    @DisplayName("Should throw IllegalStateException for invalid room dimensions")
    void testConvertInvalidRoomDimensions(String roomDimensions) {
        IllegalStateException exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> roomConverter.convert(roomDimensions),
                "Expected exception for invalid room dimensions");
        String expectedMessage = MessageFormat.format("The given room dimension [{0}] does not match the LxWxH pattern.", roomDimensions);
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "    " })
    @DisplayName("Should throw IllegalStateException for blank room dimensions")
    void testConvertBlankInput(String roomDimensions) {
        InvalidMethodParameterException exception = Assertions.assertThrows(
                InvalidMethodParameterException.class,
                () -> roomConverter.convert(roomDimensions),
                "Expected exception for blank input");
        Assertions.assertEquals("[roomDimensions] parameter is blank.", exception.getMessage());
    }
}
