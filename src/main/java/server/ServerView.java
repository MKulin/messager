package server;


import common.interfaces.Observable;
import common.interfaces.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * Класс UI приложения "издателя"
 * @see Observable
 */
public class ServerView extends JFrame implements Observable {

    /**
     * Главная панель окна, содержащая остальные панели и элементы UI
     */
    private JPanel mainPanel;
    /**
     * Панель архива новостей, содежит JLabel c заголовком и нередактируемую JTextArea
     */
    private JPanel archivePanel;
    /**
     * Редактируемая панель создания нового сообщения, содержащая JLabel c заголовком и
     * редактируемую JTextArea
     */
    private JPanel newsPanel;
    /**
     * Текстовое поле JTextArea, входящая в состав newsPanel
     */
    private JTextArea inputArea;
    /**
     * Текстовое поле JTextArea, входящая в состав archivePanel
     */
    private JTextArea archiveArea;
    /**
     * Кнопка подтверждения окончания ввода текста в newsPanel и публикации его "подписчикам"
     */
    private JButton publish;
    /**
     * Схема расположения элементов на главной панели в столбик
     */
    private GroupLayout layout;

    /**
     * Ссылка на экземпляр класса, реализующего интерфейс Observer, в данном случае ServerController
     * @see Observer
     * @see Observable
     * @see ServerController
     */
    private Observer observer;

    /**
     * Конструктор класса UI "издателя"
     * производит общие настройки элементов UI:
     * <ol>
     *     <li>Устанавливает заголовок</li>
     *     <li>Определяет первоначальные размеры окна приложения</li>
     *     <li>Инициализирует прочие элементы окна приложения</li>
     *     <li>Определяет действия при закрытии окна</li>
     * <ol/>
     * @param title - строковая переменная, определяющая заголовок окна приложения
     */
    ServerView(String title){
        setTitle(title);
        setSize(new Dimension(600, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        layout = new GroupLayout(mainPanel);

        archivePanelInit();
        newsPanelInit();
        publishButtonInit();

        mainPanel.setLayout(new BoxLayout(mainPanel, 3));
        mainPanel.setSize(getSize());
        add(mainPanel);
        setVisible(true);
    }

    /**
     * Инициация и настройка панели архива новостей
     */
    private void archivePanelInit(){

        JLabel titleLabel = new JLabel("Архив новостей");
        archiveArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(archiveArea);
        archiveArea.setEditable(false);
        archivePanel = new JPanel();
        archivePanel.setLayout(new BoxLayout(archivePanel, 1));
        archivePanel.add(titleLabel);
        archivePanel.add(scrollPane);
        archivePanel.setVisible(true);
        mainPanel.add(archivePanel);
    }

    /**
     * Инициация и настройка панели создания новостей
     */
    private void newsPanelInit(){
        inputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(inputArea);
        scrollPane.setVerticalScrollBar(new JScrollBar());
        JLabel titleLabel = new JLabel("Новая новость");
        newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, 1));
        newsPanel.add(titleLabel);
        newsPanel.add(scrollPane);
        newsPanel.setVisible(true);
        mainPanel.add(newsPanel);
    }

    /**
     * Инициация и настройка кнопки окончания ввода и начала публикации новости
     * Лямбда-функция (о) определяет алгоритм действия программы в случае нажатия "кнопки":
     * <ol>
     *     <li>Сохраняет текст из поля inputArea в переменную message</li>
     *     <li><p>Проверяет наличие текста в переменной, если его нет,</p>
     *     <p>то присваивает переменной message пустое значение,</p>
     *     <p>во избежании NullPointerException</p></li>
     *     <li>Добавляет введенный текст в поле архива новостей с символом переноса каретки</li>
     *     <li>Вызывает метод интерфейса Observable, для опвещения об окончании ввода нового сообщения</li>
     * </ol>
     * @see Observable
     * @see Observer
     */
    private void publishButtonInit(){
        publish = new JButton("Опубликовать");
        publish.addActionListener(o -> {
            String message = inputArea.getText();
            if (message == null){
                message = "";
            }
            inputArea.setText("");
            archiveArea.append(message + "\n");
            notifyObserver(message);
        });
        newsPanel.add(publish);
    }

    /**
     * Метод интерфейса Observable для оповещения об окончании ввода текста нового сообщения
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
