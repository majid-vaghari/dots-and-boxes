package net.communication;

import cons.Constants;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Majid Vaghari on 11/18/2015.
 */
public class Report {
    private final ArrayBlockingQueue<String> log;

    {
        log = new ArrayBlockingQueue<String>(Constants.BUFFER_SIZE);
    }

    public void add(final String message) {
        log.add(message);
    }

    public String remove() {
        return log.remove();
    }

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

    public void saveLog(final PrintStream stream) {
        log.forEach(stream:: println);
    }
}
