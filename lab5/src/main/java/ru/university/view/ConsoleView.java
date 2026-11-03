package ru.university.view;

import ru.university.model.OfficeDevice;
import java.util.List;

public class ConsoleView {

    public void showDevices(List<OfficeDevice> devices) {
        if (devices == null || devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return;
        }
        System.out.println("\n========== ТЕКУЩЕЕ СОСТОЯНИЕ УСТРОЙСТВ ==========");
        for (OfficeDevice d : devices) {
            System.out.println(d);
            System.out.printf("Износ: %.1f%% | Замятие: %s\n",
                    d.getWearPercentage(), d.isPaperJammed() ? "Да" : "Нет");
            System.out.println("-------------------------------------------------");
        }
        System.out.println("=================================================\n");
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showError(String err) {
        System.err.println("ОШИБКА: " + err);
    }

    public void showHelp() {
        System.out.println("""
            Использование: java -jar target/lab5-mvc-1.0.0.jar [КЛЮЧИ]
            
            Доступные ключи:
              --help, -h
                  Справка по вызову программы.
                  
              --print, -p <файл>
                  Разовый вывод информации о состоянии объектов из файла.
                  
              --replace, -r <откуда> <куда>
                  Замена целевого файла другим файлом.
                  
              --watch, -w <файл> -i <секунды>
                  Цикличный вывод состояния объектов с периодическим считыванием файла.
                  
              --csv, -c <входной_файл> -o <выходной_csv> -i <секунды>
                  Периодическое логирование изменяющихся свойств в CSV с отметкой времени.
                  
              --legacy, -l
                  Запуск интерактивного режима работы из ЛР4.
            """);
    }
}
