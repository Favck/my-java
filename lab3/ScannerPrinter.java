package lab3;

public class ScannerPrinter extends OfficeDevice {
    private int totalPrints;
    private int totalScans;
    private int totalCopies;
    private boolean isColor;
    private int cartridgeInk; 
    private double speedWork; 

    public ScannerPrinter() {
        super("Unnamed MFP", 50);
        this.totalPrints = 0;
        this.totalScans = 0;
        this.totalCopies = 0;
        this.isColor = true;
        this.cartridgeInk = 100;
        this.speedWork = 15.0;
    }

    public ScannerPrinter(String name, int countPaper, boolean isColor) {
        super(name, countPaper);
        this.totalPrints = 0;
        this.totalScans = 0;
        this.totalCopies = 0;
        this.isColor = isColor;
        this.cartridgeInk = 100;
        this.speedWork = 15.0;
    }

    public ScannerPrinter(String name, int countPaper, boolean isColor, double speedWork) {
        super(name, countPaper);
        this.totalPrints = 0;
        this.totalScans = 0;
        this.totalCopies = 0;
        this.isColor = isColor;
        this.cartridgeInk = 100;
        this.speedWork = speedWork;
    }

    @Override
    public void startTask(int pages) {
        if (isPaperJammed) {
            System.out.println("В МФУ " + model + " зажало бумагу!");
            return;
        }
        if (paperCount < pages) {
            System.out.printf("В МФУ %s не хватает бумаги. Нужно еще %d листов.\n", model, pages - paperCount);
            return;
        }
        if (cartridgeInk < pages) {
            System.out.println("В МФУ " + model + " закончилась краска! Требуется заправка.");
            return;
        }

        paperCount -= pages;
        cartridgeInk -= pages;
        totalCopies += pages;
        totalScans += pages;
        totalPrints += pages;
        isWearPercentage();

        System.out.printf("МФУ %s: скопировано %d стр. Остаток бумаги: %d, остаток краски: %d\n",
                model, pages, paperCount, cartridgeInk);
    }

    public void printDoc(int pages) {
        if (isPaperJammed) {
            System.out.println("В МФУ " + model + " зажало бумагу!");
            return;
        }
        if (paperCount >= pages && cartridgeInk >= pages) {
            paperCount -= pages;
            cartridgeInk -= pages;
            totalPrints += pages;
            isWearPercentage();
            System.out.printf("МФУ %s: напечатано %d стр. Остаток бумаги: %d\n", model, pages, paperCount);
        } else {
            System.out.println("Ошибка печати: проверьте бумагу или краску в МФУ " + model);
        }
    }

    public void scanDoc(int pages) {
        if (isPaperJammed) {
            System.out.println("В МФУ " + model + " замятие!");
            return;
        }
        totalScans += pages;
        isWearPercentage();
        System.out.printf("МФУ %s: успешно отсканировано %d страниц в файл.\n", model, pages);
    }

    public void refillCartridge() {
        this.cartridgeInk = 100;
        System.out.println("Картридж МФУ " + model + " успешно заправлен (100).");
    }

    public double calculateCopyTime(int pages) {
        double seconds = ((double) pages / speedWork) * 60;
        System.out.printf("Примерное время работы (%d стр.): %.1f сек.\n", pages, seconds);
        return seconds;
    }

    @Override
    public String toString() {
        return "\nId: " + id +
                "\nИмя МФУ: " + model +
                "\nЦветное: " + (isColor ? "Да" : "Нет") +
                "\nСкорость работы: " + speedWork + " стр/мин" +
                "\nБумаги: " + paperCount +
                "\nОстаток краски: " + cartridgeInk +
                "\nВсего напечатано: " + totalPrints +
                "\nВсего отсканировано: " + totalScans +
                "\nВсего скопировано: " + totalCopies;
    }

    public int getTotalPrints() { return totalPrints; }
    public int getTotalScans() { return totalScans; }
    public int getTotalCopies() { return totalCopies; }
    public boolean isColor() { return isColor; }
    public int getCartridgeInk() { return cartridgeInk; }
    public double getSpeedWork() { return speedWork; }

    public void setColor(boolean color) { this.isColor = color; }
    public void setTotalPrints(int totalPrints) { this.totalPrints = totalPrints; }
    public void setTotalScans(int totalScans) { this.totalScans = totalScans; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public void setSpeedWork(double speedWork) { 
        this.speedWork = speedWork; 
        System.out.println("Скорость работы МФУ обновлена");
        }
}
