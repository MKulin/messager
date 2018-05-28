package client;

public class ClientMain {
    public static void main(String[] args) {
        new ClientController(args[0], Integer.parseInt(args[1]));
    }
}
