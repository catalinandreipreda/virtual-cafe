package Helpers;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<String> logs;
    private FileWriter fileWriter;
    private final Gson gson;

    public Logger() {
        this.logs = new ArrayList<>();
        this.gson = new Gson();
        try {
            this.fileWriter = new FileWriter("logs.json", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message){
        logToTerminal(message);
        logToFile(message);
    }

    public void logToTerminal(String message) {
        System.out.println(message);
    }

    public void logToFile(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy 'at' HH:mm"));
        String log = gson.toJson(new Log(message, timestamp));
        logs.add(log);

        try {
            fileWriter.write(log + ",\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Log {
        String message;
        String dateAndTime;

        Log(String message, String dateAndTime) {
            this.message = message;
            this.dateAndTime = dateAndTime;
        }
    }
}

