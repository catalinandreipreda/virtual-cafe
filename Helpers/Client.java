package Helpers;

import java.io.*;
import java.net.Socket;

public class Client{
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public Client(String serverUrl, int port ) throws IOException {
        this.socket = new Socket(serverUrl, port);
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.writer = new PrintWriter(this.socket.getOutputStream(), true);
    }

    public void sendMessage(String message){
        writer.println(message);
    }

    public String readResponse(){
        String response;
        try {
            response = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    public Boolean isConnected(){
        return socket.isConnected();
    }

    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
