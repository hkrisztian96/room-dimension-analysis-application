package gscf.task.roomdimension.util;

import java.text.MessageFormat;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import gscf.task.roomdimension.exception.InvalidMethodParameterException;

/**
 * Utility class providing methods for validating method parameters.
 *
 * @author krisztian.hathazi
 */
public final class ParamValidatorUtil {

    private ParamValidatorUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Validates that the given string parameter is neither blank nor null.
     *
     * @param parameter
     *            The string parameter to check.
     * @param parameterName
     *            The name of the parameter, used in the exception message if validation fails.
     * @throws InvalidMethodParameterException
     *             If the parameter is blank or null.
     */
    public static void requireNonBlank(String parameter, String parameterName) throws InvalidMethodParameterException {
        if (StringUtils.isBlank(parameter)) {
            throw new InvalidMethodParameterException(MessageFormat.format("[{0}] parameter is blank.", parameterName));
        }
    }

    /**
     * Validates that the given object parameter is not null.
     *
     * @param parameter
     *            The object parameter to check.
     * @param parameterName
     *            The name of the parameter, used in the exception message if validation fails.
     * @throws InvalidMethodParameterException
     *             If the parameter is null.
     */
    public static void requireNonNull(Object parameter, String parameterName) throws InvalidMethodParameterException {
        if (Objects.isNull(parameter)) {
            throw new InvalidMethodParameterException(MessageFormat.format("[{0}] parameter is null.", parameterName));
        }
    }

    /**
     * Validates that the given array parameter is not empty.
     *
     * @param parameter
     *            The array parameter to check.
     * @param parameterName
     *            The name of the parameter, used in the exception message if validation fails.
     * @throws InvalidMethodParameterException
     *             If the parameter is null or empty.
     */
    public static void requireNonEmpty(Object[] parameter, String parameterName) throws InvalidMethodParameterException {
        if (ArrayUtils.isEmpty(parameter)) {
            throw new InvalidMethodParameterException(MessageFormat.format("[{0}] parameter is null or empty.", parameterName));
        }
    }
}
