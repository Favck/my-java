package lab3;

public class DocumentScanner extends OfficeDevice {
    private boolean isSheetfed; //Умеет ли потоково печатать
    private int totalDocumentsScan;
    private int dpi;
    private  int maxDpi;
    private  double scanSpeed; // страниц в минуту


    public  DocumentScanner(){
        super("Unnamed", 0);
        this.isSheetfed = false;
        this.totalDocumentsScan = 0;
        this.dpi = 300;
        this.maxDpi = 800;
        this.scanSpeed = 20;
    }
    
    public DocumentScanner(String name, boolean isSheetfed, int maxDpi, double scanSpeed){
        super(name,0);
        this.isSheetfed = isSheetfed;
        this.maxDpi = maxDpi;
        this.dpi = maxDpi;
        this.totalDocumentsScan = 0;
        this.scanSpeed = scanSpeed;
    }


    public void scanOnePaper(int pages){
        if(pages == 1){
            startTask(pages);
        }else{
            System.out.println("Сканер " + model + " за раз сканирует только 1 лист");
        }
        
    }


    private void updateTotalScan(int pages){
        totalDocumentsScan = totalDocumentsScan + pages;
    }

    public void scanSeveralPaper(int pages){
        startTask(pages);
    }

    public void scanSeveralPaperDpi(int pages, int dpi){
        if(dpi <= maxDpi){
            this.dpi = dpi;
            startTask(pages);
        }else{
            System.out.println("Сканер " + model + " не поддерживает такое качество, максимум: " + maxDpi);
        }
    }

    @Override 
    public void startTask(int pages){
        if(!isPaperJammed){
            if(isSheetfed){
                updateTotalScan(pages);
                isWearPercentage();
                System.out.println("Отсканировано " + pages + " л");
            }else if(pages == 1){
                updateTotalScan(pages);
                isWearPercentage();
                System.out.println("Отсканировано " + pages + " л");
            }else{
                System.out.println("Сканер " + model + " не сканирует несколько листов");
            }
        }else{
            System.out.print("В сканере " + model + " зажало бумагу");
        }
    }

    public double calculateScanTime(int pages){
        double timeInSeconds = ((double)pages/scanSpeed) * 60;
        System.out.println("Примерное время сканировния: " + timeInSeconds + "сек");
        return timeInSeconds;
    }

    @Override 
    public String toString(){
        String sheetfed = isSheetfed? "Да" : "Нет";
        return  "\nId: " + id +
                "\nName: " + model +
                "\nПараметры:\n" + 
                " 1. Максимальный dpi: " + maxDpi +
                "\n 2. Поддержка потоковой сканировния :" + sheetfed +
                "\n 3. Скорость сканирования: " + scanSpeed +
                "\nВсего отсканировано: " + totalDocumentsScan;

    }
    


    public boolean isSheetfed() {return isSheetfed;}
    public int getTotalDocumentsScan() {return totalDocumentsScan;}
    public int getDpi() { return dpi;}
    public int getMaxDpi() {return maxDpi;}
    public double getScanSpeed() {return scanSpeed; }


    public void setSheetfed(boolean sheetfed) {
        this.isSheetfed = sheetfed;
        System.out.println("Параметр потокового сканирования обновлён");
    }

    public void setTotalDocumentsScan(int totalDocumentsScan) {
        this.totalDocumentsScan = totalDocumentsScan;
        System.out.println("Общий счётчик отсканированных документов обновлён");
    }

    public void setDpi(int dpi){
        if(dpi <= maxDpi){
            this.dpi = dpi;
            System.out.println("Dpi обновлен успешно!");
        }else{
            System.out.println("Ошибка, максимум: " + maxDpi);
        }
    }
    public void setMaxDpi(int maxDpi) {
        this.maxDpi = maxDpi;
        System.out.println("Максимальный DPI обновлён");
    }

    public void setScanSpeed(double scanSpeed) {
        this.scanSpeed = scanSpeed;
        System.out.println("Скорость сканирования обновлена");
    }


}
