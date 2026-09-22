package lab2;

public class Printer {
    String name;
    int countPapper;
    
    public Printer(){
        this.countPapper = 0;
        this.name = "";
    }

    public Printer(String name, int countPapper){
        if (countPapper > 0){
            this.countPapper = countPapper;
        }
        else{
            System.out.println("Кол-во листов не может быть отрицатльным! Присвоено 0.");
            this.countPapper = 0;
        }
        this.name = name;
    }

    public void print(int pages){
        if (pages < countPapper){
            countPapper = countPapper - pages;
            System.out.printf("Имя: %s\nНапечатано %d стр.\nОстаток %d\n",name, pages, countPapper);
        }
        else{
            System.out.printf("Нехватка листов. Добавьте %d листов\n", pages - countPapper);
        }
    }

    public void addPapper(int pages){
        if(pages < 0){
            System.out.println("Нельзя добавить отрицательное количество листов.");
        }else{
            countPapper = pages + countPapper;
            System.out.printf("Добавлено %d листов.\nВсего: %d", pages, countPapper);
        }
    }  

    public int getCountPapper() {
        return countPapper;
    }
    public String getName() {
        return name;
    }
    public void setCountPapper(int countPapper) {
        if(countPapper > 0){
            this.countPapper = countPapper;
        }else{
            System.out.println("Кол-во листов не может быть отрицательным");
        }
    }
    public void setName(String name) {
        this.name = name;
    }
}



