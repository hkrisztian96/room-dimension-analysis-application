package gscf.task.roomdimension.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A custom log formatter that formats log messages with a timestamp, log level, and message.
 *
 * @author krisztian.hathazi
 */
public class CustomConsoleLogFormatter extends Formatter {

    /**
     * Formats a log record into a string with a custom format.
     * <p>
     * The format includes:
     * <ul>
     * <li>Timestamp (formatted as {@code yyyy-MM-dd HH:mm:ss})</li>
     * <li>Log level (e.g., INFO, SEVERE, WARNING)</li>
     * <li>Log message</li>
     * </ul>
     * </p>
     *
     * @param logRecord
     *            The log record to format.
     * @return A formatted string representing the log record.
     */
    @Override
    public String format(LogRecord logRecord) {
        // Custom log format
        StringBuilder sb = new StringBuilder();

        // Add a timestamp to the log entry
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(logRecord.getMillis())));

        // Add the log level (e.g., INFO, WARNING, SEVERE)
        sb.append(" [").append(logRecord.getLevel()).append("]");

        // Add the message
        sb.append(" - ").append(formatMessage(logRecord));

        // Add a newline at the end
        sb.append("\n");

        return sb.toString();
    }
}
