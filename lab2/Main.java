package lab2;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int check = 0;
        int count, count_papper;
        ArrayList<Printer> printers = new ArrayList<>();
        String new_name;
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
            check = scanner.nextInt();
            switch (check) {
                case 1: 
                    if(printers.isEmpty()){
                        System.out.println("Принтеры ещё не добавлены. Нажмите 3");
                    }else{
                        getInfo(printers);
                    }
                    break;
                case 2:
                    getInfo(printers);
                    System.out.println("Введите номер принтера который хотите изменить");
                    int num = scanner.nextInt();
                    System.out.println("Что хотите изменить\n1. Название\n2.Количество листов\nВведите цифру:");
                    int check_edit = scanner.nextInt();
                    if (check_edit == 1){
                        System.out.println("Введите новое название:");
                        new_name = scanner.nextLine();
                        printers.get(num).setName(new_name);
                    }else if(check_edit == 2){
                        System.out.println("Введите новое кол-во листов:");
                        count = scanner.nextInt();
                        printers.get(num).setCountPapper(count);
                    }
                    break;
                case 3:
                    System.out.println("Введите название принтера:");
                    new_name = scanner.next();
                    System.out.println("Введите количество листов");
                    count = scanner.nextInt();
                    Printer pr = new Printer(new_name, count);
                    printers.add(pr);
                    break;
                case 4:
                    getInfo(printers);
                    System.out.println("Введите номер принтера:");
                    count = scanner.nextInt();
                    printers.remove(count - 1);
                    System.out.println("ПОСЛЕ УДАЛЕНИЯ:");
                    getInfo(printers);
                    break;
                case 5:
                    getInfo(printers);
                    System.out.println("Введите номер принтера:");
                    count = scanner.nextInt();
                    System.out.println("Кол-во страниц:");
                    count_papper = scanner.nextInt();
                    printers.get(count).print(count_papper);
                    break;
                case 6:
                    getInfo(printers);
                    System.out.println("Введите номер принтера:");
                    count = scanner.nextInt();
                    System.out.println("Кол-во страниц:");
                    count_papper = scanner.nextInt();
                    printers.get(count).print(count_papper);
                    break;
                case 7:
                    getAllPappers(printers);
                    break;
                case 8:
                    break;
            }

        }
    }

    public static void getInfo(ArrayList<Printer> printers){
        for(int i = 0; i < printers.size(); i++){
            System.out.printf("Принтер %d: %s, %d листов\n", i+1, printers.get(i).getName(), printers.get(i).getCountPapper());
        }
    }

    public static void getAllPappers(ArrayList<Printer> printers){
        int summ = 0;
        for(Printer printer: printers){
            summ = summ + printer.getCountPapper();
        }
        System.out.printf("Общее кол-во листов %d: ", summ);
    }

}
