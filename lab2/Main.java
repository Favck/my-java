package lab2;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int check = 0;
        int count, count_Paper;
        ArrayList<Printer> printers = new ArrayList<>();
        String new_name;
        Printer p1 = new Printer();
        Printer p2 = new Printer("HP", 100);
        Printer p3 = new Printer("Canon", 200);

        printers.add(p1);
        printers.add(p2);
        printers.add(p3);

        while (true) {
            System.out.println("======МЕНЮ======");
            System.out.println("1. Просмотреть принтеры");
            System.out.println("2. Изменить принтеры");
            System.out.println("3. Добавить принтер");
            System.out.println("4. Удалить принтер");
            System.out.println("5. Напечатать документы");
            System.out.println("6. Добавить бумагу");
            System.out.println("7. Посчитать общее количество листов");
            System.out.println("8. Выход");
            boolean condition = true;
            while (condition) {
                boolean check1 = scanner.hasNextInt();
                if(check1){
                    check = scanner.nextInt();
                    condition = false;
                }else{
                    System.out.println("Попробуйте снова:");
                    scanner.next(); 
                }
            }
                    

            switch (check) {
                case 1: 
                    if(printers.isEmpty()){
                        System.out.println("Принтеры ещё не добавлены. Нажмите 3");
                    }else{
                        getInfo(printers);
                    }
                    break;
                case 2:
                    if(!printers.isEmpty()){
                    getInfo(printers);
                    System.out.println("Введите номер принтера который хотите изменить");
                    int num = scanner.nextInt();
                    System.out.println("Что хотите изменить\n1. Название\n2.Количество листов\nВведите цифру:");
                    int check_edit = scanner.nextInt();
                    if (check_edit == 1){
                        System.out.println("Введите новое название:");
                        new_name = scanner.next();
                        printers.get(num - 1).setName(new_name);
                    }else if(check_edit == 2){
                        System.out.println("Введите новое кол-во листов:");
                        count = checkCountPaper(scanner);
                        printers.get(num - 1).setCountPaper(count);
                    }
                    }else{
                        System.out.println("Принтеры ещё не добавлены. Нажмите 3");
                    }
                    break;
                case 3:
                    System.out.println("Введите название принтера:");
                    new_name = scanner.next();
                    System.out.println("Введите количество листов");
                    count = checkCountPaper(scanner);
                    Printer pr = new Printer(new_name, count);
                    printers.add(pr);
                    break;
                case 4:
                    if(!printers.isEmpty()){
                    getInfo(printers);
                    System.out.println("Введите номер принтера:");
                    count = scanner.nextInt();
                    printers.remove(count - 1);
                    System.out.println("ПОСЛЕ УДАЛЕНИЯ:");
                    getInfo(printers);
                    }else{
                        System.out.println("Принтеры закончились!");
                    }
                    break;
                case 5:
                    if(!printers.isEmpty()){
                    getInfo(printers);
                    System.out.println("Введите номер принтера:");
                    count = scanner.nextInt();
                    System.out.println("Кол-во страниц:");
                    count_Paper = checkCountPaper(scanner);
                    printers.get(count - 1).print(count_Paper);
                    }else{
                        System.out.println("Принтеры ещё не добавлены. Нажмите 3");
                    }
                    break;
                case 6:
                    if(!printers.isEmpty()){
                    getInfo(printers);
                    System.out.println("Введите номер принтера:");
                    count = scanner.nextInt();
                    System.out.println("Кол-во страниц:");
                    count_Paper = checkCountPaper(scanner);
                    printers.get(count - 1).addPaper(count_Paper);   
                    System.out.println("Принтеры закончились!");
                    }else{
                        System.out.println("Принтеры ещё не добавлены. Нажмите 3");
                    }
                    break;

                case 7:
                    if(!printers.isEmpty()){
                        getAllPapers(printers);
                    }else{
                        System.out.println("Принтеры ещё не добавлены. Нажмите 3");
                    }
                    break;
                case 8:
                    System.out.println("Выход из программы!");
                    return;
                default:
                    break;
            }
            
        }

    }

    public static void getInfo(ArrayList<Printer> printers){
        for(int i = 0; i < printers.size(); i++){
            System.out.printf("Принтер %d: %s, %d листов\n", i+1, printers.get(i).getName(), printers.get(i).getCountPaper());
        }
    }

    public static void getAllPapers(ArrayList<Printer> printers){
        int summ = 0;
        for(Printer printer: printers){
            summ = summ + printer.getCountPaper();
        }
        System.out.printf("Общее кол-во листов %d:\n\n", summ);
    }

    public static int checkCountPaper(Scanner scanner) {
        int count = 0;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Введите количество бумаги (строго больше 0): ");

            if (scanner.hasNextInt()) {
                count = scanner.nextInt();
                
                
                if (count > 0) {
                    isValid = true; 
                } else {
                    System.out.println("Ошибка! Количество должно быть строго больше 0.");
                }
            } else {
                System.out.println("Ошибка! Введены буквы или не целое число.");
                scanner.next(); 
            }
        }

        return count;
    }


}
