package gscf.task.roomdimension.exception;

import java.io.IOException;

/**
 * Custom exception thrown when the size of a file exceeds a specified limit.
 * <p>
 * This exception is used to indicate that a file being processed is too large and exceeds the maximum acceptable size.
 * </p>
 *
 * @author krisztian.hathazi
 */
public class SizeLimitExceededIOException extends IOException {

    /**
     * Constructs a new {@code SizeLimitExceededIOException} with the specified detail message.
     *
     * @param message
     *            The detail message, which is saved for later retrieval by the {@link Throwable#getMessage()} method.
     */
    public SizeLimitExceededIOException(String message) {
        super(message);
    }
}
