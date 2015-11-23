package net.communication;

import cons.Constants;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * <p> This class is used to get reports from Input and Output handlers and send every log messages to this class </p>
 * <p> Created by Majid Vaghari on 11/18/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.0.0
 */
public class Report {
    /**
     * This is a array of strings used to store log messages
     */
    private final ArrayBlockingQueue<String> log;

    /**
     * Initializes the log.
     */ {
        log = new ArrayBlockingQueue<>(Constants.BUFFER_SIZE);
    }

    /**
     * Adds a message to the log
     *
     * @param message the message to add
     */
    public void add(final String message) {
        log.add(message);
    }

    /**
     * returns and removes the first message in the log
     *
     * @return the First message in the log
     */
    public String remove() {
        return log.remove();
    }

    /**
     * Saves the log file in the specific location
     *
     * @param path file path to save the file
     */
    public void saveLog(final String path) {
        File logFile = new File(path);

        if (logFile.canWrite())
            try (
                    PrintStream fileWriter = new PrintStream(logFile)
            ) {
                saveLog(fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * Prints log in a given Prints stream object
     *
     * @param stream PrintStream object which we'll save logs to.
     */
    public void saveLog(final PrintStream stream) {
        log.forEach(stream:: println);
    }
}
