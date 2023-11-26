package Helpers;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private final Socket socket;

    private String customerName = "Unknown Customer";
    public ClientHandler(Socket clientSocket){
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        try{

            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter( socket.getOutputStream(), true );

            System.out.println("Client connected");
            String firstMessage = scanner.nextLine();
            if(firstMessage.contains(" entered the cafe")){
                this.customerName = firstMessage.replaceFirst(" entered the cafe", "");
            }
;
            writer.println("Welcome to the cafe, " + customerName + "!");
            while (socket.isConnected()){

                if(scanner.hasNextLine()){
                    String request = scanner.nextLine();
                    System.out.println("[Received from " + customerName + " ]: " + request);
                    writer.println(request);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }
}
