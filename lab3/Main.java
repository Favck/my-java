package lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<OfficeDevice> devices = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        initDefaultDevices();

        while (true) {
            System.out.println("\n========== ГЛАВНОЕ МЕНЮ ==========");
            System.out.println("1. Показать все устройства");
            System.out.println("2. Добавить устройство");
            System.out.println("3. Удалить устройство");
            System.out.println("4. Изменить свойства устройства");
            System.out.println("5. Функциональная работа с устройством");
            System.out.println("6. Изменить свойство на случайную величину");
            System.out.println("0. Выход");

            int choice = InputHelper.readPositiveInt(scanner, "Выберите пункт: ", 0, 6);

            switch (choice) {
                case 1 : showAllDevices(); break;
                case 2 : addDeviceMenu(); break;
                case 3 : removeDeviceMenu(); break;
                case 4 : editDeviceMenu(); break;
                case 5 : executeDeviceFunctionMenu(); break;
                case 6 : triggerRandomPropertyMenu(); break;
                case 0 : {
                    System.out.println("Завершение программы.");
                    return;
                }
            }
        }
    }

    private static void initDefaultDevices() {
        devices.add(new Printer("HP LaserJet Pro", 100, true, true, 25.0));
        devices.add(new DocumentScanner("Epson Perfection", true, 1200, 30.0));
        devices.add(new ScannerPrinter("Canon MegaTank MFP", 150, true, 20.0));
    }

    private static void showAllDevices() {
        if (devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return;
        }
        System.out.println("\n--- Список устройств в офисе ---");
        for (OfficeDevice device : devices) {
            System.out.println(device);
            System.out.printf("Износ: %.1f%% | Замятие бумаги: %s\n", 
                    device.getWearPercentage(), device.isPaperJammed() ? "Да" : "Нет");
            System.out.println("---------------------------------");
        }
    }

    private static OfficeDevice selectDevice() {
        if (devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return null;
        }
        showAllDevices();
        int id = InputHelper.readPositiveInt(scanner, "Введите ID устройства: ", 1, Integer.MAX_VALUE);
        for (OfficeDevice device : devices) {
            if (device.getId() == id) {
                return device;
            }
        }
        System.out.println("Устройство с ID " + id + " не найдено.");
        return null;
    }

    private static void triggerRandomPropertyMenu() {
        OfficeDevice device = selectDevice();
        if (device == null) return;

        double before = device.getWearPercentage();
        double randomDelta = 5.0 + Math.random() * 20.0; 
        device.setWearPercentage(before + randomDelta);

        System.out.printf("Свойство wearPercentage устройства %s изменено на случайную величину +%.1f%%.\n", 
                device.getModel(), randomDelta);
        System.out.printf("Было: %.1f%% -> Стало: %.1f%%\n", before, device.getWearPercentage());
        if (device.getWearPercentage() >= 100.0) {
            device.setPaperJammed(true);
            System.out.println("Внимание! Износ достиг предела, бумагу замяло!");
        }
    }

    private static void addDeviceMenu() {
        System.out.println("\n--- Добавление устройства ---");
        System.out.println("1. Обычный принтер (Printer)");
        System.out.println("2. Документ-сканер (DocumentScanner)");
        System.out.println("3. Многофункциональное устройство (ScannerPrinter)");
        System.out.println("0. Назад");

        int type = InputHelper.readPositiveInt(scanner, "Выберите тип: ", 0, 3);
        if (type == 0) return;

        String model = InputHelper.readString(scanner, "Введите название модели: ");

        switch (type) {
            case 1 : {
                int paper = InputHelper.readPositiveInt(scanner, "Количество бумаги в лотке: ", 0, 5000);
                int color = InputHelper.readPositiveInt(scanner, "Цветной? (1 - Да, 0 - Нет): ", 0, 1);
                int duplex = InputHelper.readPositiveInt(scanner, "Двусторонняя печать? (1 - Да, 0 - Нет): ", 0, 1);
                int speed = InputHelper.readPositiveInt(scanner, "Скорость печати (стр/мин): ", 1, 200);
                devices.add(new Printer(model, paper, color == 1, duplex == 1, speed));
                System.out.println("Принтер успешно добавлен.");
                break;
            }
            case 2 : {
                int sheetfed = InputHelper.readPositiveInt(scanner, "Поддержка потокового сканирования? (1 - Да, 0 - Нет): ", 0, 1);
                int maxDpi = InputHelper.readPositiveInt(scanner, "Максимальный DPI (напр. 600, 1200): ", 75, 4800);
                int speed = InputHelper.readPositiveInt(scanner, "Скорость сканирования (стр/мин): ", 1, 200);
                devices.add(new DocumentScanner(model, sheetfed == 1, maxDpi, speed));
                System.out.println("Сканер успешно добавлен.");
                break;
            }
            case 3 : {
                int paper = InputHelper.readPositiveInt(scanner, "Количество бумаги в лотке: ", 0, 5000);
                int color = InputHelper.readPositiveInt(scanner, "Цветное МФУ? (1 - Да, 0 - Нет): ", 0, 1);
                int speed = InputHelper.readPositiveInt(scanner, "Скорость работы МФУ (стр/мин): ", 1, 200);
                devices.add(new ScannerPrinter(model, paper, color == 1, speed));
                System.out.println("МФУ успешно добавлено.");
                break;
            }
        }
    }

    private static void removeDeviceMenu() {
        OfficeDevice device = selectDevice();
        if (device != null) {
            devices.remove(device);
            System.out.println("Устройство " + device.getModel() + " (ID: " + device.getId() + ") удалено.");
        }
    }

    private static void editDeviceMenu() {
        OfficeDevice device = selectDevice();
        if (device == null) return;

        System.out.println("\n--- Редактирование: " + device.getModel() + " ---");
        System.out.println("1. Изменить модель");
        System.out.println("2. Загрузить бумагу");
        System.out.println("3. Устранить замятие бумаги");
        System.out.println("4. Изменить износ на случайную величину (п. 4.3)");

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
            case 1 : {
                String newModel = InputHelper.readString(scanner, "Новое имя модели: ");
                device.setModel(newModel);
                break;
            }
            case 2 : {
                int amount = InputHelper.readPositiveInt(scanner, "Сколько листов добавить: ", 1, 2000);
                device.loadPaper(amount);
                break;
            }
            case 3 : device.clearJam(); break;
            case 4 : {
                double before = device.getWearPercentage();
                double randomDelta = 5.0 + Math.random() * 20.0;
                device.setWearPercentage(before + randomDelta);
                System.out.printf("Износ случайно вырос на %.1f%% (было: %.1f%%, стало: %.1f%%)\n", 
                        randomDelta, before, device.getWearPercentage());
                break;
            }
            case 5 : {
                if (device instanceof Printer p) {
                    int speed = InputHelper.readPositiveInt(scanner, "Новая скорость печати: ", 1, 200);
                    p.setPrintSpeed(speed);
                } else if (device instanceof DocumentScanner s) {
                    int dpi = InputHelper.readPositiveInt(scanner, "Новый DPI: ", 75, s.getMaxDpi());
                    s.setDpi(dpi);
                } else if (device instanceof ScannerPrinter sp) {
                    sp.refillCartridge();
                }
                break;
            }
            case 6 : {
                if (device instanceof Printer p) {
                    int c = InputHelper.readPositiveInt(scanner, "Цветной? (1 - Да, 0 - Нет): ", 0, 1);
                    p.setIsColor(c == 1);
                } else if (device instanceof DocumentScanner s) {
                    int speed = InputHelper.readPositiveInt(scanner, "Новая скорость сканирования: ", 1, 200);
                    s.setScanSpeed(speed);
                } else if (device instanceof ScannerPrinter sp) {
                    int speed = InputHelper.readPositiveInt(scanner, "Новая скорость МФУ: ", 1, 200);
                    sp.setSpeedWork(speed);
                }
                break;
            }
        }
    }

    private static void executeDeviceFunctionMenu() {
        OfficeDevice device = selectDevice();
        if (device == null) return;

        System.out.println("\n--- Работа с " + device.getModel() + " ---");
        System.out.println("1. Базовая задача устройства (startTask)");

        if (device instanceof Printer) {
            System.out.println("2. Цветная печать");
            System.out.println("3. Двусторонняя печать (Duplex)");
            System.out.println("4. Двусторонняя цветная печать");
            System.out.println("5. Рассчитать время печати");
        } else if (device instanceof DocumentScanner) {
            System.out.println("2. Сканировать один лист");
            System.out.println("3. Потоковое сканирование");
            System.out.println("4. Сканирование с выбором DPI");
            System.out.println("5. Рассчитать время сканирования");
        } else if (device instanceof ScannerPrinter) {
            System.out.println("2. Только распечатать документ");
            System.out.println("3. Только отсканировать в файл");
            System.out.println("4. Заправить картридж");
            System.out.println("5. Рассчитать время копирования");
        }

        int action = InputHelper.readPositiveInt(scanner, "Выберите действие: ", 1, 5);

        if (action == 1) {
            int pages = InputHelper.readPositiveInt(scanner, "Количество страниц: ", 1, 1000);
            device.startTask(pages);
            return;
        }

        if (device instanceof Printer p) {
            int pages = InputHelper.readPositiveInt(scanner, "Количество страниц: ", 1, 1000);
            switch (action) {
                case 2 : p.printColor(pages); break;
                case 3 : p.printDuplex(pages); break;
                case 4 : p.printDuplexColor(pages); break;
                case 5 : p.calculatePrintTime(pages); break;
            }
        } else if (device instanceof DocumentScanner s) {
            switch (action) {
                case 2 : 
                    s.scanOnePaper(1);
                    break;
                case 3 : {
                    int pages = InputHelper.readPositiveInt(scanner, "Количество страниц: ", 1, 500);
                    s.scanSeveralPaper(pages);
                    break;
                }
                case 4 : {
                    int pages = InputHelper.readPositiveInt(scanner, "Количество страниц: ", 1, 500);
                    int dpi = InputHelper.readPositiveInt(scanner, "DPI: ", 75, 4800);
                    s.scanSeveralPaperDpi(pages, dpi);
                    break;
                }
                case 5 : {
                    int pages = InputHelper.readPositiveInt(scanner, "Количество страниц: ", 1, 500);
                    s.calculateScanTime(pages);
                    break;
                }
            }
        } else if (device instanceof ScannerPrinter sp) {
            switch (action) {
                case 2 : {
                    int pages = InputHelper.readPositiveInt(scanner, "Количество страниц: ", 1, 1000);
                    sp.printDoc(pages);
                    break;
                }
                case 3 : {
                    int pages = InputHelper.readPositiveInt(scanner, "Количество страниц: ", 1, 1000);
                    sp.scanDoc(pages);
                    break;
                }
                case 4 : 
                    sp.refillCartridge();
                    break;
                case 5 : {
                    int pages = InputHelper.readPositiveInt(scanner, "Количество страниц: ", 1, 1000);
                    sp.calculateCopyTime(pages);
                    break;
                }
            }
        }
    }
}