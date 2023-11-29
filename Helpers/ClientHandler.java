package Helpers;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//TODO ask client for name instead of having it hardcoded in client
//TODO respond to order commands
//TODO respond to order status commands
//TODO respond with error message for unknown commands
//TODO implement brewing lifecycle with waiting area, brewing area and tray area
//TODO If a client orders new items before their previous order has completed, instead of generating a new ‘order’, the new items should simply be added to the existing order.

//TODO If a client leaves the café before their order has completed, simply discard the relevant objects from the respective areas.
//TODO [BONUS] Whenever a client leaves, the server should check if any of their brewing or tray items can be repurposed for orders belonging to other clients

//TODO [BONUS] implement authorization
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
