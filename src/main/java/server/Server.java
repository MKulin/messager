package server;

import common.Connection;
import common.Logging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/***
 * Экземпляр - "издатель"
 */
class Server {

    private ServerSocket serverSocket;
    private int port;

    /**
     * Библиотека клиентов. Используем ArrayList для хранения активных соединений с экземплярами - "подписчиками"
     */
    private static List<Connection> clientsLib = new ArrayList<>();

    /**
     * Конструктор класса, где инициализируем ServerSocket
     * @param port - номер порта на котором открывается приложение
     */
    Server(int port){
        try{
            this.serverSocket = new ServerSocket(port);
            serverMainLoop();
        } catch (IOException e) {
            Logging.log("Error while opening server socket");
            System.exit(1);
        }
    }

    /**
     * <p>Главный цикл класса, в котором:</p>
     * <ol>
     *  <li>ждем подключения "подписчиков";</li>
     *  <li>открываем новый сокет в момент подключения "подписчика";</li>
     *  <li>заносим нового "подписчика" в библиотеку.</li>
     * </ol>
     */
    private void serverMainLoop(){
        while(true)
        {
            Socket socket;
            try {
                socket = serverSocket.accept();
                Logging.log("Connection accepted with " + socket.getLocalSocketAddress().toString());
                Connection connection = new Connection(socket);
                clientsLib.add(connection);
            } catch (IOException e) {
                Logging.log("Could not open new socket");
            }
        }
    }

    /**
     * Внутренний cтатический класс для обработки и отправки сообщений "подписчикам"
     */
    static class Broadcaster {

        /**
         * Cтатический метод рассылки сообщений по адресам из библиотеки "подписчиков"
         * @param message - текст сообщения
         */
        static void broadcast(String message){
            clientsLib.stream().filter(o -> !o.isClosed()).forEach(o -> o.send(message));
            Logging.log("Broadcasted message " + message);
        }
    }

}
