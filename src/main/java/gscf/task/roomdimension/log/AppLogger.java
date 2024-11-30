package gscf.task.roomdimension.log;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A utility class for configuring and providing a global logger instance for the application.
 *
 * @author krisztian.hathazi
 */
public final class AppLogger {

    private static final Logger LOGGER = Logger.getLogger(AppLogger.class.getName());

    static {
        // Set the log level to INFO
        LOGGER.setLevel(Level.FINE);

        // Create a ConsoleHandler
        ConsoleHandler consoleHandler = new ConsoleHandler();

        // Set the custom formatter for the console handler
        consoleHandler.setFormatter(new CustomConsoleLogFormatter());

        // Set the console handler log level
        consoleHandler.setLevel(Level.FINE);

        // Add the handler to the logger
        LOGGER.addHandler(consoleHandler);
    }

    private AppLogger() {
        // Private constructor to prevent instantiation
    }

    /**
     * Returns the logger instance for the application.
     *
     * @return The global {@link Logger} instance configured for the application.
     */
    public static Logger getLogger() {
        return LOGGER;
    }

    // Can be further extended to have more versatility over application logging
}
