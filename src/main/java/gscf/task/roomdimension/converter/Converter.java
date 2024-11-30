package gscf.task.roomdimension.converter;

/**
 * The {@code Converter} interface defines a contract for converting an input parameter of type {@code P} into an output of type {@code R}.
 * Implementations of this interface should provide the logic for the conversion process.
 *
 * @param <R>
 *            The type of the result after conversion.
 * @param <P>
 *            The type of the input parameter to be converted.
 *
 * @author krisztian.hathazi
 */
public interface Converter<R, P> {

    /**
     * Converts the given input parameter into a result of type {@code R}.
     *
     * @param param
     *            The input parameter of type {@code P} to be converted.
     * @return The result of the conversion, of type {@code R}.
     */
    R convert(P param);
}
