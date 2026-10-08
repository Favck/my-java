package lab2;

public class Printer {
    String name;
    int countPaper;
    static int test;


    public Printer(){
        this.countPaper = 0;
        this.name = "";
    }

    public Printer(String name, int countPaper){
        if (countPaper >= 0){
            this.countPaper = countPaper;
        }
        else{
            System.out.println("Кол-во листов не может быть отрицатльным! Присвоено 0.");
            this.countPaper = 0;
        }
        this.name = name;
    }

    public static void testt(){
        this.name = "123123";
    }

    public void print(int pages){
        if (pages <= countPaper){
            countPaper = countPaper - pages;
            System.out.printf("Имя: %s\nНапечатано %d стр.\nОстаток %d\n",name, pages, countPaper);
        }
        else{
            System.out.printf("Нехватка листов. Добавьте %d листов\n", pages - countPaper);
        }
    }

    public void addPaper(int pages){
        if(pages <= 0){
            System.out.println("Нельзя добавить отрицательное количество листов.");
        }else{
            countPaper = pages + countPaper;
            System.out.printf("Добавлено %d листов.\nВсего: %d", pages, countPaper);
        }
    }  

    public int getCountPaper() {
        return countPaper;
    }
    public String getName() {
        return name;
    }
    public void setCountPaper(int countPaper) {
        if(countPaper > 0){
            this.countPaper = countPaper;
        }else{
            System.out.println("Кол-во листов не может быть отрицательным");
        }
    }
    public void setName(String name) {
        this.name = name;
    }
}



