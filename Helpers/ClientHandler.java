package Helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//TODO respond to order commands
//TODO respond to order status commands
//TODO implement brewing lifecycle with waiting area, brewing area and tray area
//TODO If a client orders new items before their previous order has completed, instead of generating a new ‘order’, the new items should simply be added to the existing order.

//TODO If a client leaves the café before their order has completed, simply discard the relevant objects from the respective areas.
//TODO [BONUS] Whenever a client leaves, the server should check if any of their brewing or tray items can be repurposed for orders belonging to other clients

//TODO [BONUS] implement authorization
public class ClientHandler implements Runnable{
    private final Socket socket;

    private String customerName;
    public ClientHandler(Socket clientSocket){
        this.socket = clientSocket;
    }

    private void disconnect(){
        try {
            socket.close();
            System.out.println("Client disconnected");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to close socket");
        }

    }

    @Override
    public void run() {
        try{

            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter( socket.getOutputStream(), true );

            System.out.println("Client connected");
            writer.println("Hello! What is your name?");



            while (socket.isConnected()){

                if(scanner.hasNextLine()){
                    String request = scanner.nextLine();

                    System.out.println("[Received from " + customerName + " ]: " + request);

                    if (request.equalsIgnoreCase("exit")){
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
                            writer.println("Working on your order. No status available");
                            continue;
                        }

                        try {
                            var order = Cafe.parseOrder(request);
                            String orderResponse = order.isEmpty() ? "We couldn't place your order, no quantity for tea or coffee was specified" :"Order received from " + customerName + " ( " + order.coffeeCount + " X coffee, " + order.teaCount + " X tea )";
                            writer.println(orderResponse);
                            continue;
                        } catch (Exception e){
                            writer.println("We couldn't process your order, please make sure it is correctly formatted and try again" + "\n Example: " + " order 1 tea and 3 coffees");

                            continue;
                        }

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
