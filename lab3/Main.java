package lab3;

public class Main {
    public static void main(String[] args){
        Printer pr1 = new Printer();
        Printer pr2 = new Printer("HP", 20);
        pr2.startTask(5);
        pr2.startTask(5);
        pr2.startTask(5);
        pr2.startTask(5);
        System.out.println(pr2.getWearPercentage());
    }
}
