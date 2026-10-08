package lab3;

public class Printer extends OfficeDevice{
    private boolean isColor;
    private boolean isDuplexSupported;
    private double printSpeed;
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

    public Printer(String name, int countPaper,boolean isColor, boolean isDuplexSupported, double printSpeed){
        super(name, countPaper);
        this.isColor = isColor;
        this.isDuplexSupported = isDuplexSupported;
        this.printSpeed = printSpeed;
        this.totalPagesPaper = 0;

    }

    public double calculatePrintTime(int pages){
        double timeInSeconds = ((double)pages/printSpeed) * 60;
        System.out.println("Примерное время печати: " + timeInSeconds + "сек");
        return timeInSeconds;
    }


    private void updateTotalPages(int pages){
        totalPagesPaper = pages + totalPagesPaper;
    }

    public void printColor(int pages){
        if(!isPaperJammed){
            if(paperCount >= pages){
                if(isColor){
                    paperCount = paperCount - pages;
                    System.out.println("Напечатано " + pages + " листов" + "\nОстаток: " + paperCount);
                    isWearPercentage();
                    updateTotalPages(pages);
                }else{
                    System.out.println("Принтер " + model + " не поддерживает цветную печать");
                }
            }else{
                System.out.printf("Нехватка листов. Добавьте %d листов\n", pages - paperCount);
            }
        }else{
            System.out.println("В принтере " + model + " зажало бумагу");
        }
    }

    public void printDuplex(int pages){
        if(!isPaperJammed){
            if(isDuplexSupported){
                int sheetNeed = (int)Math.ceil(pages / 2.0);
                if(sheetNeed <= paperCount){
                    paperCount = paperCount - sheetNeed;
                    System.out.println("\nНапечтано " + sheetNeed + "\nОстаток: " + paperCount);
                    isWearPercentage();
                    updateTotalPages(sheetNeed);
                }else{
                    System.out.printf("Нехватка листов. Добавьте %d листов\n", pages - paperCount);
                }
            }else{
                System.out.println("У модели " + model + "не поддерживается двойная печать");
            }
        }else{
            System.out.println("В принтере " + model + " зажало бумагу");
        }
    }

    private void print(int pages){
        if (pages <= paperCount){
            paperCount = paperCount - pages;
            System.out.printf("Имя: %s\nНапечатано %d стр.\nОстаток %d\n",model, pages, paperCount);
            isWearPercentage();
            updateTotalPages(pages);
        }
        else{
            System.out.printf("Нехватка листов. Добавьте %d листов\n", pages - paperCount);
        }
    }

    public void startTask(int pages){
        if(!isPaperJammed){
            this.print(pages);
        }else{
            System.out.println("В принтере " + model + " зажало бумагу");
        }
        
    }

    public void printDuplexColor(int pages){
        int sheetNeed = (int)Math.ceil(pages / 2.0);
        if(!isPaperJammed){
            if(isDuplexSupported){
                if(isColor){
                    if(sheetNeed <= paperCount){
                        paperCount = paperCount - sheetNeed;
                        System.out.printf("Имя: %s\nНапечатано %d стр.\nОстаток %d\n",model, sheetNeed, paperCount);
                        isWearPercentage();
                        updateTotalPages(sheetNeed);
                    }else{
                        System.out.printf("Нехватка листов. Добавьте %d листов\n", sheetNeed - paperCount);
                    }
                }else{
                    System.out.println("Принтер " + model + " не поддерживает цветную печать");
                }
            }else{
                System.out.println("У модели " + model + "не поддерживается двойная печать");
            }
        }else{
            System.out.println("В принтере " + model + " зажало бумагу");
        }
    }

    @Override 
    public String toString(){
        return "\n" + "id: "+ id+ "\n" + "Имя: " + model + "\n" + 
        "Параметры:\n  1. Цвет:" + isColor + 
        "\n  2. Двойная печать: " + isDuplexSupported +
        "\n  3. Скорость печати: " + printSpeed + 
        "\nКол-во листов: " + paperCount + 
        "\nВсего напечатано: " + totalPagesPaper;
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

    public double getPrintSpeed(){
        return  printSpeed;
    }

    public boolean getIsColor(){
        return  isColor;
    }

    public boolean getIsDuplexSupported(){
        return  isDuplexSupported;
    }

    public int getTotalPages(){
        return  totalPagesPaper;
    }

}



