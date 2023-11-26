import Helpers.Server;

public class Barista {
    final static int PORT = 8888;
//    private static Server cafeServer;


    public static void main(String[] args) {
        Server cafeServer = null;
        try {
            cafeServer = new Server(PORT);
            cafeServer.listen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (cafeServer != null) {
                try {
                    cafeServer.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
