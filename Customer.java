import Helpers.Client;

import java.util.Scanner;

public class Customer {
    final static String SERVER_URL = "localhost";
    final static int PORT = 8888;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! What is your name?");
        String name = scanner.nextLine();

        try  {
            Client client = new Client(SERVER_URL, PORT);
            client.sendMessage(name + " entered the cafe");
            while (client.isConnected()) {
                String response = client.readResponse();
                if(response != null){
                System.out.println("Barista: " + response);
                }

                if(scanner.hasNextLine()){
                    String message = scanner.nextLine();
                    client.sendMessage(message);

                    if ("exit".equalsIgnoreCase(message)) {
                        client.sendMessage("Client disconnected");
                        System.out.println(name + " left the cafe");
                        client.close();
                        break;
                    }
                }

            }
        }  catch (Exception e) {
            e.printStackTrace();
        }

    }
}
