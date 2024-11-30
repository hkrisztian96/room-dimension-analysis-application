package gscf.task.roomdimension.util;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import gscf.task.roomdimension.exception.InvalidMethodParameterException;

/**
 * {@link ParamValidatorUtil} unit test class.
 *
 * @author krisztian.hathazi
 */
class ParamValidatorUtilTest {

    static Stream<Arguments> arrayProvider() {
        return Stream.of(Arguments.of((Object) new String[] {}), null);
    }

    static Stream<Arguments> stringProvider() {
        return Stream.of(Arguments.of((""), null));
    }

    @Test
    @DisplayName("Should validate successfully with non blank string")
    void testRequireNonBlankValid() {
        Assertions.assertDoesNotThrow(() -> ParamValidatorUtil.requireNonBlank("Valid String", "parameterName"));
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    @DisplayName("Should throw InvalidMethodParameterException for blank parameters")
    void testRequireNonBlankInvalid(String parameter) {
        InvalidMethodParameterException exception = Assertions
                .assertThrows(InvalidMethodParameterException.class, () -> ParamValidatorUtil.requireNonBlank(parameter, "parameterName"));
        Assertions.assertEquals("[parameterName] parameter is blank.", exception.getMessage());
    }

    @Test
    @DisplayName("Should validate successfully with non null object")
    void testRequireNonNullValid() {
        Assertions.assertDoesNotThrow(() -> ParamValidatorUtil.requireNonNull(new Object(), "parameterName"));
    }

    @Test
    @DisplayName("Should throw InvalidMethodParameterException for null parameter")
    void testRequireNonNullInvalid() {
        InvalidMethodParameterException exception = Assertions
                .assertThrows(InvalidMethodParameterException.class, () -> ParamValidatorUtil.requireNonNull(null, "parameterName"));
        Assertions.assertEquals("[parameterName] parameter is null.", exception.getMessage());
    }

    @Test
    @DisplayName("Should validate successfully with non empty array")
    void testRequireNonEmptyValid() {
        Assertions.assertDoesNotThrow(() -> ParamValidatorUtil.requireNonEmpty(new Object[] { "value" }, "parameterName"));
    }

    @ParameterizedTest
    @MethodSource("arrayProvider")
    @DisplayName("Should throw InvalidMethodParameterException for null and empty array")
    void testRequireNonEmptyInvalid(Object[] parameter) {
        InvalidMethodParameterException exception = Assertions
                .assertThrows(InvalidMethodParameterException.class, () -> ParamValidatorUtil.requireNonEmpty(parameter, "parameterName"));
        Assertions.assertEquals("[parameterName] parameter is null or empty.", exception.getMessage());
    }
}
