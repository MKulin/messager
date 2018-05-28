package common;

import java.io.*;
import java.net.Socket;

/**
 * <p>Класс, обеспечивающий отправку сообщений "подписчикам",обработку разрыва соединения</p>
 */
public class Connection {
    /**
     * Поток вывода
     */
    private final DataOutputStream out;
    /**
     * Поток ввода
     */
    private final DataInputStream in;
    private final Socket socket;
    /**
     * Переменная - флаг, означающая активность соединения
     */
    private boolean isClosed;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        isClosed = false;
    }

    /**
     * <p>Отправка сообщения "подписчику" через поток вывода</p>
     * <p>Метод определяет достижимость адреса "подписчика",
     * если в течении 1000мс адресс не отвечает, то закрывает подключение.</p>
     *
     * @param message - текст сообщения от "издателя"
     */
    public void send(String message) {
        try {
            if (socket.getInetAddress().isReachable(1000)) {
                out.writeUTF(message);
                out.flush();
            } else {
                close();
            }
        } catch (IOException e) {
            Logging.log("Closing connection");
            close();
        }
    }

    /**
     * Метод чтения сообщения от "издателя" из потока ввода
     *
     * @return - текст сообщения, либо пустое сообщение
     */
    public String receive() {
        String message = null;
        try {
            byte[] buffer = new byte[in.available()];
            in.readFully(buffer);
            message = new String(buffer, 0, buffer.length);
        } catch (IOException e) {
            Logging.log(e.getMessage());
        }
        return message;
    }

    /**
     * Закрытие соединения
     */
    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
            isClosed = true;
        } catch (IOException e) {
            Logging.log(e.getMessage());
        }
    }

    public boolean isClosed() {
        return isClosed;
    }
}
