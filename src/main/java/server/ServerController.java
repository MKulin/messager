package server;

import common.interfaces.Observer;

/**
 * Класс - контроллер. Создает экземпляры классов серверной логики и UI "издателя",
 * а так же связывает их по средствам интерфесов Observer и Observable и статического внутреннего
 * класса Broadcaster.
 * @see Observer
 * @see common.interfaces.Observable
 * @see server.Server.Broadcaster
 */
public class ServerController implements Observer {

    private ServerView serverView;

    /**
     * Старт серверной части приложения,
     * создание объектов серверной логики и UI
     * @param port - порт, на котором открывается приложение
     */
    void serverStart(int port){
        serverView = new ServerView("Издатель");
        serverView.addObserver(this);
        new Server(port);
    }

    /**
     * Метод интерфейса Observer, принимающий строку сообщения в качестве аргумента,
     * и передающий её статическому методу статического внутреннего класса Broadcaster серверной логики
     * @see Observer
     * @see server.Server.Broadcaster
     * @param message - строка сообщения от "издателя" к "подписчикам"
     */
    @Override
    public void handleAction(String message) {
        Server.Broadcaster.broadcast(message);
    }
}
