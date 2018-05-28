package server;

public class ServerMain {

    public static void main(String[] args) {
        ServerController controller = new ServerController();
        controller.serverStart(Integer.parseInt(args[0]));
    }
}
