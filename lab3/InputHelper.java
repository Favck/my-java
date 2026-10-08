package lab3;

import java.util.Scanner;

public class InputHelper {

    public static int readPositiveInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value < min) {
                    System.out.println("Ошибка: число не может быть меньше " + min + ".");
                    continue;
                }

                if (value > max) {
                    System.out.println("Ошибка: значение превышает лимит (" + max + ").");
                    continue;
                }

                return value; 

            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введено не число или значение слишком велико (переполнение int)!");
            }
        }
    }

    public static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Ошибка: строка не может быть пустой. Попробуйте снова.");
                continue;
            }

            return input;
        }
    }
}