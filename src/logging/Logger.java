package logging;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Logger class for writing log messages to files.
 */
public class Logger {
    // SimpleDateFormat with given pattern:
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");

    // Get the current date:
    private static final Date now = new Date();

    // Format the taken date:
    private static final String formattedDate = dateFormat.format(now);

    /**
     * Logs an information message.
     *
     * @param message The information message to be logged.
     */
    public static void LogInfo(String message) {
        Log("INFO", message);
    }

    /**
     * Logs an error message.
     *
     * @param message The error message to be logged.
     */
    public static void LogError(String message) {
        Log("ERROR", message);
    }

    /**
     * Writes a log message to the appropriate log file.
     *
     * @param type      The type of log message (INFO or ERROR).
     * @param logMessage The log message to be written.
     */
    public static void Log(String type, String logMessage) {
        String logFilePath;
        if (type.equals("INFO")) {
            logFilePath = "application_info.txt";
        } else {
            logFilePath = "application_error.txt";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write("[" + formattedDate + "] [" + type + "] " + logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("An error occurred while writing the log message: " + e.getMessage());
        }
    }
}
