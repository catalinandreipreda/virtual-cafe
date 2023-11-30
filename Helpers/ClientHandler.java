package Helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//TODO respond to order status commands
//TODO implement brewing lifecycle with waiting area, brewing area and tray area

//TODO If a client leaves the café before their order has completed, simply discard the relevant objects from the respective areas.
//TODO [BONUS] Whenever a client leaves, the server should check if any of their brewing or tray items can be repurposed for orders belonging to other clients

//TODO [BONUS] implement authorization
public class ClientHandler implements Runnable{
    private final Socket socket;

    private static Cafe cafe;

    private static final Logger logger = new Logger();
    private String customerName;
    public ClientHandler(Socket clientSocket){

        this.socket = clientSocket;
        cafe = new Cafe();
    }

    private void disconnect(){
        try {
            socket.close();
            logger.log("Client disconnected");
        } catch (IOException e) {
            e.printStackTrace();
            logger.log("Failed to close socket");
        }

    }

    @Override
    public void run() {
        try{

            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter( socket.getOutputStream(), true );

            logger.log("Client connected");
            writer.println("Hello! What is your name?");



            while (socket.isConnected()){

                if(scanner.hasNextLine()){
                    String request = scanner.nextLine();

                    logger.log("[Received from " + customerName + " ]: " + request);

                    if (request.equalsIgnoreCase("exit")){
                        cafe.removeOrder(customerName);
                        scanner.close();
                        writer.close();
                        break;
                    }

                    if(customerName == null && request.contains("username:")){
                        customerName = request.replace("username:", "");
                        writer.println("Welcome to the cafe, " + customerName + "!");
                        continue;
                    }

                    if (request.startsWith("order")){
                        if(request.equalsIgnoreCase("Order status")){
                            var orderStatus = cafe.getOrderStatus(customerName);
                            writer.println(orderStatus);
                            continue;
                        }

                        cafe.handleOrder(request, customerName, writer);

                        continue;
                    }


                    writer.println("Unrecognized command");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }


    }
}
