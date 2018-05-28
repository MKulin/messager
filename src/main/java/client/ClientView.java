package client;


import javax.swing.*;
import java.awt.*;

/**
 * Класс UI приложения "подписчика"
 */
class ClientView extends JFrame{

    /**
     * Главная панель окна, содержащая остальные элементы UI
     */
    private JPanel mainPanel;
    /**
     * Реализация "прокрутки" поля новостей
     */
    private JScrollPane scrollPane;
    /**
     * Поле новостей. Не редактируемое
     */
    private JTextArea textArea;
    /**
     * Заголовок поля новостей
     */
    private JLabel news;

    /**
     * Конструктор класса UI "подписчика"
     * производит общие настройки элементов UI:
     * <ol>
     *     <li>Устанавливает заголовок</li>
     *     <li>Определяет первоначальные размеры окна приложения</li>
     *     <li>Инициализирует прочие элементы окна приложения</li>
     *     <li>Определяет действия при закрытии окна</li>
     * <ol/>
     * @param title - строковая переменная, определяющая заголовок окна приложения
     */
    ClientView(String title){
        setTitle(title);
        setSize(new Dimension(600, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initTextField();
        initMainPanel();
        getContentPane().add(mainPanel);
        setVisible(true);
    }

    /**
     * Инициализация текстовой панели вместе с "прокруткой"
     */
    private void initTextField(){
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBar(new JScrollBar());
        news = new JLabel("Новости");
    }

    /**
     * Инициализая главной панели
     */
    private void initMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(news);
        mainPanel.add(scrollPane);
    }

    /**
     * Метод добавляет текст новости, присланной "издателем"
     * на текстовую панель, вызывается экземпляром класса ClientController
     * @see ClientController
     * @param message - строки текста новости
     */
    void handleInputText(String message){
        textArea.append(message + "\n");
        textArea.update(textArea.getGraphics());
    }
}
