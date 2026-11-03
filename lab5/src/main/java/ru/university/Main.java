package ru.university;

import ru.university.controller.AppController;
import ru.university.model.DeviceManager;
import ru.university.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        DeviceManager manager = new DeviceManager();
        ConsoleView view = new ConsoleView();
        AppController controller = new AppController(manager, view);

        controller.handle(args);
    }
}
