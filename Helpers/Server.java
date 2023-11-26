package Helpers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements  AutoCloseable{

    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server started on port: " + port);

    }

    public void listen()
    {
        try
        {
            System.out.println( "Server waiting for incoming connections..." );
            while (!serverSocket.isClosed())
            {   Socket clientSocket = this.serverSocket.accept();
                Thread clientThread = new Thread( new ClientHandler(clientSocket) );
                clientThread.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        if(serverSocket != null){
            serverSocket.close();
        }
    }
}
