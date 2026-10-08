package lab3;

public class Printer extends OfficeDevice{
    private boolean isColor;
    private boolean isDuplexSupported;
    private int printSpeed;
    private int totalPagesPaper;
    
    public Printer(){
        super("Unnamed", 0);
        this.isColor = true;
        this.isDuplexSupported = true;
        this.printSpeed = 1;
        this.totalPagesPaper = 0;
    }

    public Printer(String name, int countPaper){
        super(name, countPaper);
        this.isColor = true;
        this.isDuplexSupported = true;
        this.printSpeed = 1;
        this.totalPagesPaper = 0;

    }

    public Printer(String name, int countPaper,boolean isColor, boolean isDuplexSupported, int printSpeed){
        super(name, countPaper);
        this.isColor = isColor;
        this.isDuplexSupported = isDuplexSupported;
        this.printSpeed = printSpeed;
        this.totalPagesPaper = 0;

    }

    private void print(int pages){
        if (pages <= paperCount){
            paperCount = paperCount - pages;
            System.out.printf("Имя: %s\nНапечатано %d стр.\nОстаток %d\n",model, pages, paperCount);
        }
        else{
            System.out.printf("Нехватка листов. Добавьте %d листов\n", pages - paperCount);
        }
    }

    public void startTask(int pages){
        if(!isPaperJammed){
            this.print(pages);
            totalPagesPaper++;
        }else{
            System.out.println("В принтере " + model + " зажало бумагу");
        }
        isWearPercentage();
    }

    public void setIsColor(boolean value){
        isColor = value;
        System.out.println("Параметр цветной печати успешно обновлён");
    }

    public void setIsDuplexSupported(boolean value){
        isDuplexSupported = value;
        System.out.println("Параметр двусторонней печати успешно обновлён");
    }

    public void setPrintSpeed(int value){
        printSpeed = value;
        System.out.println("Параметр скорости печати успешно обновлён");
    }

    public void setTotalPapperPages(int value){
        totalPagesPaper = value;
        System.out.println("Общее кол-во напечатанных листов изменено успешно!");
    }

}



