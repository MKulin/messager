package common.interfaces;

/**
 * Интерфейс поддержки оповещения класса, реализующего интерфейс Observer,
 * о событии в текущем классе.
 * @see Observer
 */
public interface Observable {

    /**
     * @see server.ServerView
     */
    void notifyObserver(String message);

    /**
     * @see server.ServerView
     */
    void addObserver(Observer observer);
}
