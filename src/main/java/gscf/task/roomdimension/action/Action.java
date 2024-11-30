package gscf.task.roomdimension.action;

/**
 * The {@code Action} interface represents a generic contract for performing an action with a given input parameter. Classes that implement this
 * interface must define how to process the input parameter by providing an implementation of the {@link #process(Object)} method.
 *
 * @param <P>
 *            The type of the parameter that the action will process.
 *
 * @author krisztian.hathazi
 */
public interface Action<P> {

    /**
     * Processes the given parameter to perform an action.
     *
     * @param parameter
     *            The parameter to be processed by the action. The type of this parameter is specified by the implementing class.
     */
    void process(P parameter);
}
