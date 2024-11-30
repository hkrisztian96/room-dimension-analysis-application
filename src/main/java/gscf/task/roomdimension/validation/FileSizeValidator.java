package gscf.task.roomdimension.validation;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import gscf.task.roomdimension.util.ParamValidatorUtil;

/**
 * A utility class that validates the size of a file.
 * 
 * @author krisztian.hathazi
 */
public class FileSizeValidator {

    /**
     * Validates that the file located at the given path does not exceed the specified size.
     * <p>
     * If the file exceeds the maximum size, a {@link gscf.task.roomdimension.exception.SizeLimitExceededIOException} is thrown.
     * </p>
     *
     * @param inputFilePath
     *            The path to the file to be validated.
     * @param maxSize
     *            The maximum allowed size for the file in bytes.
     * @throws IOException
     *             If there is an issue reading the file or if the file size exceeds the specified limit.
     * @throws gscf.task.roomdimension.exception.InvalidMethodParameterException
     *             If the inputFilePath is blank.
     */
    public void validate(String inputFilePath, long maxSize) throws IOException {
        // Public method, so parameter validation is a must
        ParamValidatorUtil.requireNonBlank(inputFilePath, "inputFilePath");
        Path filePath = Paths.get(inputFilePath);
        try (LimitedSizeInputStream limitedSizeInputStream = new LimitedSizeInputStream(Files.newInputStream(filePath), maxSize)) {
            // Consume the input stream to validate file size
            limitedSizeInputStream.transferTo(OutputStream.nullOutputStream());
        }
    }
}
