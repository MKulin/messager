package client;


import common.Connection;
import common.Logging;
import common.interfaces.Observable;
import common.interfaces.Observer;

import java.io.IOException;
import java.net.Socket;

/**
 * Экземпляр - "Подписчик"
 */
class Client implements Observable{

    private Observer observer;

    /**
     * Экземпляр класса подключения
     * @see Connection
     */
    private Connection connection;

    /**
     * <p>Конструктор класса "подписчик" открывает Socket на переданных параметрах и
     * инициализирует подключение</p>
     * @see Connection
     * @see ClientController
     * @param host - адресс хоста (издателя)
     * @param port - порт хоста (издателя)
     * @param clientController - экземляр интерфейса Observer, который реализуется классом ClientController
     */
    Client (String host, int port, Observer clientController){
        try {
            Socket socket = new Socket(host, port);
            this.connection = new Connection(socket);
            addObserver(clientController);
            clientMainLoop();
        } catch (IOException e) {
            Logging.log("Не удалось установить соединение по адресу: " + host + ":" + port);
            System.exit(1);
        }
    }

    /**
     * <p>Главный цикл клиента, обеспечивающий получение сообщений от "издателя"</p>
     * <p>Если текст события не null и не пустое сообщение, вызывает метод интерфейса Observable</p>
     * @see Observable
     */
    private void clientMainLoop(){
        String message;
        while (true){
            message = connection.receive();
            if(message != null && !message.equals("")) {
                notifyObserver(message);
            }
        }
    }

    /**
     * Метод интерфейса Observable для оповещения о поступлении нового сообщения
     * @param message - строка текста сообщения
     */
    @Override
    public void notifyObserver(String message) {
        observer.handleAction(message);
    }

    /**
     * Метод интерфейса Observable для добавления экземпляра интерфейса Observer
     * @param observer - экземпляр интерфеса Observer
     * @see Observer
     */
    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }
}
