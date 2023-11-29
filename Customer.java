import Helpers.Client;


import java.io.BufferedReader;
import java.io.InputStreamReader;

//TODO The user can order one or more teas or coffees via the client at any time, by typing commands
//TODO Inquire about the order status
//TODO [BONUS] client has authentication mechanism (protocol) to prove that it is a valid client and is allowed to interact with the server
//TODO [BONUS] implement non blocking I/O so client can receive messages from the server at any time. Not just as a response to a command sent by the client
public class Customer {
    final static String SERVER_URL = "localhost";
    final static int PORT = 8888;

   static  String username;


    public static void main(String[] args) {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));



    // Intercept and gracefully handle SIGTERM signals (e.g. pressing Ctrl-C) for exiting the café
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Exiting cafe and disconnecting client")));

        try  {
            Client client = new Client(SERVER_URL, PORT);

            while (client.isConnected()) {
                String response = client.readResponse();

                if(response != null){
                    System.out.println("Barista: " + response);

                    if(username == null && response.contains("What is your name?")){
                        username =  scanner.readLine();
                        client.sendMessage("username:"+username);
                    }

                }




                if(scanner.ready()){
                    String message = scanner.readLine();
                    client.sendMessage(message);

                    // leave the café at any time, by typing exit
                    if (message.equalsIgnoreCase("exit")) {
                        System.out.println(username + " left the cafe");
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
