package gscf.task.roomdimension.exception;

/**
 * Custom exception thrown when an invalid method parameter is provided.
 * <p>
 * This exception is typically used to signal that a method was called with an invalid or inappropriate parameter.
 * </p>
 *
 * @author krisztian.hathazi
 */
public class InvalidMethodParameterException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidMethodParameterException} with the specified detail message.
     *
     * @param message
     *            The detail message, which is saved for later retrieval by the {@link Throwable#getMessage()} method.
     */
    public InvalidMethodParameterException(String message) {
        super(message);
    }
}
