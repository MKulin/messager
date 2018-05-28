package common.interfaces;

/**
 * Интерфейс для поддержки обовещения о событии
 * в классе, реализующем интерфейс Observable
 * @see Observable
 */
public interface Observer {

    /**
     * Поддержка оповещения о событии
     * @param message - строка текста события
     */
    void handleAction(String message);
}
