package ru.university.controller;

import ru.university.model.*;
import ru.university.view.ConsoleView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppController {
    private final DeviceManager manager;
    private final ConsoleView view;
    private final List<OfficeDevice> devices = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private String currentFileName = "devices.txt";

    public AppController(DeviceManager manager, ConsoleView view) {
        this.manager = manager;
        this.view = view;
    }

    public void handle(String[] args) {
        if (args.length == 0) {
            view.showHelp();
            return;
        }

        try {
            switch (args[0]) {
                case "--help", "-h" -> view.showHelp();
                case "--print", "-p" -> executePrint(args);
                case "--replace", "-r" -> executeReplace(args);
                case "--watch", "-w" -> executeWatch(args);
                case "--csv", "-c" -> executeCsv(args);
                case "--legacy", "-l" -> executeLegacyMode();
                default -> {
                    view.showError("Неизвестный параметр: " + args[0]);
                    view.showHelp();
                }
            }
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private void executePrint(String[] args) throws IOException {
        if (args.length < 2) {
            view.showError("Укажите путь к файлу: -p <путь>");
            return;
        }
        List<OfficeDevice> list = manager.readFromFile(args[1]);
        view.showDevices(list);
    }

    private void executeReplace(String[] args) throws IOException {
        if (args.length < 3) {
            view.showError("Укажите оба файла: -r <файл1> <файл2>");
            return;
        }
        manager.replaceFile(args[1], args[2]);
        view.showMessage("Файл " + args[2] + " успешно заменен файлом " + args[1]);
    }

    private void executeWatch(String[] args) throws IOException, InterruptedException {
        if (args.length < 4 || !args[2].equals("-i")) {
            view.showError("Формат команды: -w <файл> -i <секунды>");
            return;
        }
        String file = args[1];
        int interval = Integer.parseInt(args[3]);

        view.showMessage("Запущен цикличный мониторинг файла " + file + " каждые " + interval + " сек. (Ctrl+C для остановки)");
        while (true) {
            List<OfficeDevice> list = manager.readFromFile(file);
            for (OfficeDevice d : list) simulateChange(d);
            view.showDevices(list);
            Thread.sleep(interval * 1000L);
        }
    }

    private void executeCsv(String[] args) throws IOException, InterruptedException {
        String input = null;
        String output = null;
        int interval = 2;

        for (int i = 1; i < args.length; i++) {
            if ((args[i].equals("-i") || args[i].equals("--interval")) && i + 1 < args.length) {
                interval = Integer.parseInt(args[++i]);
            } else if ((args[i].equals("-o") || args[i].equals("--output")) && i + 1 < args.length) {
                output = args[++i];
            } else if (input == null) {
                input = args[i];
            }
        }

        if (input == null || output == null) {
            view.showError("Формат команды: -c <входной_файл> -o <выходной_файл.csv> -i <секунды>");
            return;
        }

        view.showMessage("Логирование в " + output + " каждые " + interval + " сек. (Ctrl+C для остановки)");
        while (true) {
            List<OfficeDevice> list = manager.readFromFile(input);
            for (OfficeDevice d : list) simulateChange(d);
            manager.logToCsv(list, output);
            view.showMessage("[" + java.time.LocalTime.now() + "] Данные записаны в CSV: " + output);
            Thread.sleep(interval * 1000L);
        }
    }

    private void simulateChange(OfficeDevice d) {
        double delta = 0.5 + Math.random() * 1.5;
        double newWear = Math.min(100.0, d.getWearPercentage() + delta);
        d.setWearPercentage(newWear);
        if (newWear >= 100.0) {
            d.setPaperJammed(true);
        }
    }

    private void executeLegacyMode() {
        initDefaultDevices();

        while (true) {
            System.out.println("\n========== ГЛАВНОЕ МЕНЮ (ЛР4) ==========");
            System.out.println("1. Показать все устройства");
            System.out.println("2. Добавить устройство");
            System.out.println("3. Удалить устройство");
            System.out.println("4. Изменить свойства устройства");
            System.out.println("5. Функциональная работа с устройством");
            System.out.println("6. Изменить свойство на случайную величину");
            System.out.println("7. Прочитать устройства из файла (" + currentFileName + ")");
            System.out.println("8. Изменить имя файла для чтения");
            System.out.println("0. Выход");

            int choice = InputHelper.readPositiveInt(scanner, "Выберите пункт: ", 0, 8);

            switch (choice) {
                case 1 -> view.showDevices(devices);
                case 2 -> addDeviceMenu();
                case 3 -> removeDeviceMenu();
                case 4 -> editDeviceMenu();
                case 5 -> executeDeviceFunctionMenu();
                case 6 -> triggerRandomPropertyMenu();
                case 7 -> loadFromFileMenu();
                case 8 -> changeFileNameMenu();
                case 0 -> {
                    view.showMessage("Завершение интерактивного режима.");
                    return;
                }
            }
        }
    }

    private void initDefaultDevices() {
        if (devices.isEmpty()) {
            devices.add(new Printer("HP LaserJet Pro", 100, true, true, 25.0));
            devices.add(new DocumentScanner("Epson Perfection", true, 1200, 30.0));
            devices.add(new ScannerPrinter("Canon MegaTank MFP", 150, true, 20.0));
        }
    }

    private OfficeDevice selectDevice() {
        if (devices.isEmpty()) {
            view.showMessage("Список устройств пуст.");
            return null;
        }
        view.showDevices(devices);
        int id = InputHelper.readPositiveInt(scanner, "Введите ID устройства: ", 1, Integer.MAX_VALUE);
        for (OfficeDevice device : devices) {
            if (device.getId() == id) return device;
        }
        view.showMessage("Устройство с ID " + id + " не найдено.");
        return null;
    }

    private void triggerRandomPropertyMenu() {
        OfficeDevice device = selectDevice();
        if (device == null) return;
        double before = device.getWearPercentage();
        double randomDelta = 5.0 + Math.random() * 20.0;
        device.setWearPercentage(before + randomDelta);
        System.out.printf("Свойство wearPercentage устройства %s изменено: %.1f%% -> %.1f%%\n",
                device.getModel(), before, device.getWearPercentage());
        if (device.getWearPercentage() >= 100.0) {
            device.setPaperJammed(true);
            System.out.println("Внимание! Бумагу замяло!");
        }
    }

    private void addDeviceMenu() {
        System.out.println("\n--- Добавление устройства ---");
        System.out.println("1. Обычный принтер (Printer)");
        System.out.println("2. Документ-сканер (DocumentScanner)");
        System.out.println("3. Многофункциональное устройство (ScannerPrinter)");
        System.out.println("0. Назад");

        int type = InputHelper.readPositiveInt(scanner, "Выберите тип: ", 0, 3);
        if (type == 0) return;

        String model = InputHelper.readString(scanner, "Введите название модели: ");

        switch (type) {
            case 1 -> {
                int paper = InputHelper.readPositiveInt(scanner, "Количество бумаги: ", 0, 5000);
                int color = InputHelper.readPositiveInt(scanner, "Цветной? (1 - Да, 0 - Нет): ", 0, 1);
                int duplex = InputHelper.readPositiveInt(scanner, "Двусторонний? (1 - Да, 0 - Нет): ", 0, 1);
                int speed = InputHelper.readPositiveInt(scanner, "Скорость печати (стр/мин): ", 1, 200);
                devices.add(new Printer(model, paper, color == 1, duplex == 1, speed));
                view.showMessage("Принтер успешно добавлен.");
            }
            case 2 -> {
                int sheetfed = InputHelper.readPositiveInt(scanner, "Потоковое сканирование? (1 - Да, 0 - Нет): ", 0, 1);
                int maxDpi = InputHelper.readPositiveInt(scanner, "Максимальный DPI: ", 75, 4800);
                int speed = InputHelper.readPositiveInt(scanner, "Скорость сканирования (стр/мин): ", 1, 200);
                devices.add(new DocumentScanner(model, sheetfed == 1, maxDpi, speed));
                view.showMessage("Сканер успешно добавлен.");
            }
            case 3 -> {
                int paper = InputHelper.readPositiveInt(scanner, "Количество бумаги: ", 0, 5000);
                int color = InputHelper.readPositiveInt(scanner, "Цветное МФУ? (1 - Да, 0 - Нет): ", 0, 1);
                int speed = InputHelper.readPositiveInt(scanner, "Скорость работы МФУ: ", 1, 200);
                devices.add(new ScannerPrinter(model, paper, color == 1, speed));
                view.showMessage("МФУ успешно добавлено.");
            }
        }
    }

    private void removeDeviceMenu() {
        OfficeDevice device = selectDevice();
        if (device != null) {
            devices.remove(device);
            view.showMessage("Устройство " + device.getModel() + " удалено.");
        }
    }

    private void editDeviceMenu() {
        OfficeDevice device = selectDevice();
        if (device == null) return;

        System.out.println("\n--- Редактирование: " + device.getModel() + " ---");
        System.out.println("1. Изменить модель");
        System.out.println("2. Загрузить бумагу");
        System.out.println("3. Устранить замятие бумаги");
        System.out.println("4. Изменить износ на случайную величину");

        if (device instanceof Printer) {
            System.out.println("5. Изменить скорость печати");
            System.out.println("6. Изменить параметр цветной печати");
        } else if (device instanceof DocumentScanner) {
            System.out.println("5. Изменить текущий DPI");
            System.out.println("6. Изменить скорость сканирования");
        } else if (device instanceof ScannerPrinter) {
            System.out.println("5. Заправить картридж");
            System.out.println("6. Изменить скорость работы");
        }

        int choice = InputHelper.readPositiveInt(scanner, "Выберите действие: ", 1, 6);
        switch (choice) {
            case 1 -> device.setModel(InputHelper.readString(scanner, "Новое имя: "));
            case 2 -> device.loadPaper(InputHelper.readPositiveInt(scanner, "Сколько листов добавить: ", 1, 2000));
            case 3 -> device.clearJam();
            case 4 -> {
                double delta = 5.0 + Math.random() * 20.0;
                device.setWearPercentage(device.getWearPercentage() + delta);
                view.showMessage("Износ увеличен на " + delta + "%.");
            }
            case 5 -> {
                if (device instanceof Printer p) p.setPrintSpeed(InputHelper.readPositiveInt(scanner, "Скорость: ", 1, 200));
                else if (device instanceof DocumentScanner s) s.setDpi(InputHelper.readPositiveInt(scanner, "DPI: ", 75, s.getMaxDpi()));
                else if (device instanceof ScannerPrinter sp) sp.refillCartridge();
            }
            case 6 -> {
                if (device instanceof Printer p) p.setIsColor(InputHelper.readPositiveInt(scanner, "Цветной? (1 - Да, 0 - Нет): ", 0, 1) == 1);
                else if (device instanceof DocumentScanner s) s.setScanSpeed(InputHelper.readPositiveInt(scanner, "Скорость: ", 1, 200));
                else if (device instanceof ScannerPrinter sp) sp.setSpeedWork(InputHelper.readPositiveInt(scanner, "Скорость: ", 1, 200));
            }
        }
    }

    private void changeFileNameMenu() {
        currentFileName = InputHelper.readString(scanner, "Введите новое имя файла: ");
        view.showMessage("Имя файла изменено на: " + currentFileName);
    }

    private void loadFromFileMenu() {
        File file = new File(currentFileName);
        if (!file.exists()) {
            view.showError("Файл " + currentFileName + " не найден!");
            return;
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            DeviceFileReader reader = new DeviceFileReader(fis);
            List<OfficeDevice> loaded = reader.parse();
            devices.addAll(loaded);
            view.showMessage("Загружено объектов: " + loaded.size());
        } catch (Exception e) {
            view.showError("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private void executeDeviceFunctionMenu() {
        OfficeDevice device = selectDevice();
        if (device == null) return;

        System.out.println("\n--- Работа с " + device.getModel() + " ---");
        System.out.println("1. Базовая задача устройства (startTask)");
        if (device instanceof Printer) {
            System.out.println("2. Цветная печать\n3. Двусторонняя печать\n4. Двусторонняя цветная печать\n5. Рассчитать время");
        } else if (device instanceof DocumentScanner) {
            System.out.println("2. Сканировать 1 лист\n3. Потоковое сканирование\n4. Выбор DPI\n5. Рассчитать время");
        } else if (device instanceof ScannerPrinter) {
            System.out.println("2. Печать документа\n3. Скан в файл\n4. Заправить картридж\n5. Рассчитать время");
        }

        int action = InputHelper.readPositiveInt(scanner, "Выберите действие: ", 1, 5);
        if (action == 1) {
            device.startTask(InputHelper.readPositiveInt(scanner, "Количество страниц: ", 1, 1000));
            return;
        }

        if (device instanceof Printer p) {
            int pages = InputHelper.readPositiveInt(scanner, "Страниц: ", 1, 1000);
            switch (action) {
                case 2 -> p.printColor(pages);
                case 3 -> p.printDuplex(pages);
                case 4 -> p.printDuplexColor(pages);
                case 5 -> p.calculatePrintTime(pages);
            }
        } else if (device instanceof DocumentScanner s) {
            switch (action) {
                case 2 -> s.scanOnePaper(1);
                case 3 -> s.scanSeveralPaper(InputHelper.readPositiveInt(scanner, "Страниц: ", 1, 500));
                case 4 -> s.scanSeveralPaperDpi(InputHelper.readPositiveInt(scanner, "Страниц: ", 1, 500), InputHelper.readPositiveInt(scanner, "DPI: ", 75, 4800));
                case 5 -> s.calculateScanTime(InputHelper.readPositiveInt(scanner, "Страниц: ", 1, 500));
            }
        } else if (device instanceof ScannerPrinter sp) {
            switch (action) {
                case 2 -> sp.printDoc(InputHelper.readPositiveInt(scanner, "Страниц: ", 1, 1000));
                case 3 -> sp.scanDoc(InputHelper.readPositiveInt(scanner, "Страниц: ", 1, 1000));
                case 4 -> sp.refillCartridge();
                case 5 -> sp.calculateCopyTime(InputHelper.readPositiveInt(scanner, "Страниц: ", 1, 1000));
            }
        }
    }
}
