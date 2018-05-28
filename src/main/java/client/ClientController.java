package client;

import common.interfaces.Observer;

/**
 * <p>Класс - контроллер. Создает экземпляры классов серверной логики и UI "подписчика",</p>
 * <p>а так же связывает их по средствам интерфесов Observer и Observable.</p>
 * @see Observer
 * @see common.interfaces.Observable
 */
public class ClientController implements Observer{

    private ClientView clientView;

    /**
     * Старт клиентской части приложения,
     * создание объектов клиентской логики и UI
     * @param host - cтрока адреса сервера ("издателя")
     * @param port - целое число, означающее порт сервера ("издателя")
     */
    ClientController(String host, int port){
        this.clientView = new ClientView("Подписчик");
        new Client(host, port, this);
    }

    /**
     * Метод интерфейса Observer, принимающий строку сообщения в качестве аргумента,
     * и передающий её методу экземпляра класса UI "подписчика"
     * @see Observer
     * @see ClientView
     * @param message - строка сообщения от "издателя" к "подписчику"
     */
    @Override
    public void handleAction(String message) {
        clientView.handleInputText(message);
    }
}
